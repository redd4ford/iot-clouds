import logging
import azure.functions as func
import pymysql
from configparser import ConfigParser
import datetime
import json


def default(obj):
    if isinstance(obj, (datetime.datetime, datetime.date)):
        return obj.isoformat()


def parse(config_parser: ConfigParser, section: str, field: str) -> str:
    return config_parser.get(section, field).replace("'", "")


def config() -> dict:
    config_parser = ConfigParser()
    config_parser.read('post-data/config.ini')
    return {
        'host': parse(config_parser, 'sec-database', 'database_server'),
        'user': parse(config_parser, 'sec-database', 'database_username'),
        'password': parse(config_parser, 'sec-database', 'database_password'),
        'database': parse(config_parser, 'sec-database', 'database_name'),
        'ssl_ca': 'post-data/BaltimoreCyberTrustRoot.crt.pem'
    }


def get_table() -> str:
    config_parser = ConfigParser()
    config_parser.read('post-data/config.ini')
    return parse(config_parser, 'sec-table', 'table_name')


def main(req: func.HttpRequest) -> func.HttpResponse:
    try:
        request_json = req.get_json()
        logging.info(request_json)

        is_list = isinstance(request_json, list)

        # if it's a list, then it should have at least one json element and that json
        # should have at least one non-empty value
        if any([not is_list and request_json.values() and list(request_json.values())[0],
                is_list and request_json]):
            try:
                connection = pymysql.connect(**config())

                logging.info('Connection established.')

                response = request_json[0] if is_list else request_json
                try:
                    with connection.cursor() as cursor:
                        # if it's a json, then just grab the json; if it's a dict inside
                        # a list, then unpack it
                        cursor.execute(
                            f'INSERT INTO {config().get("database")}.{get_table()} '
                            f'(device_id, value, measure_time, protocol) '
                            f'VALUES (%s, %s, %s, %s)',
                            tuple(response.values())[:4]
                        )

                        connection.commit()

                        logging.info('POST OK')

                        response['id'] = cursor.lastrowid

                    return func.HttpResponse(
                        body=json.dumps(obj=response, indent=4, default=default),
                        status_code=201,
                        headers={'Access-Control-Allow-Origin': '*'}
                    )
                except pymysql.IntegrityError as err:
                    logging.error(err)
                    return func.HttpResponse(
                        f'Cannot insert those values.\n{err}',
                        status_code=409
                    )
                except pymysql.OperationalError as err:
                    logging.error(err)
                    return func.HttpResponse(
                        f'Database error.\n{err}',
                        status_code=400
                    )


            except pymysql.DatabaseError or pymysql.MySQLError or \
                   pymysql.InternalError as err:
                    logging.error(err)
                    return func.HttpResponse(
                        f'Connection was not established.\n{err}',
                        status_code=504
                    )
            finally:
                connection.close()
        else:
            logging.error('Empty JSON')
            return func.HttpResponse(
                f'JSON is empty.',
                status_code=404
            )
    except Exception as err:
        logging.error(err)
        return func.HttpResponse(
            f'JSON must be valid.\n{err}',
            status_code=404
        )
    finally:
        request_json = None
