import logging
import azure.functions as func
import pymysql
from configparser import ConfigParser
import datetime
import json


def default(obj):
    if isinstance(obj, (datetime.datetime, datetime.date)):
        return obj.isoformat()


def parse(config_parser: ConfigParser, field: str) -> str:
    return config_parser.get('sec-database', field).replace("'", "")


def config() -> dict:
    config_parser = ConfigParser()
    config_parser.read('create-plane/config.ini')
    return {
        'host': parse(config_parser, 'database_server'),
        'user': parse(config_parser, 'database_username'),
        'password': parse(config_parser, 'database_password'),
        'database': parse(config_parser, 'database_name'),
        'ssl_ca': 'create-plane/BaltimoreCyberTrustRoot.crt.pem'
    }


def main(req: func.HttpRequest) -> func.HttpResponse:
    try:
        request_json = req.get_json()
        logging.info(request_json)
    except Exception as err:
        logging.error(err)
        return func.HttpResponse(
            f'JSON must be valid.\n{err}',
            status_code=400
        )

    try:
        connection = pymysql.connect(**config())
    except pymysql.DatabaseError or pymysql.MySQLError or \
           pymysql.InternalError as err:
            logging.error(err)
            return func.HttpResponse(
                f'Connection was not established.\n{err}',
                status_code=504
            )

    logging.info('Connection established.')

    with connection.cursor() as cursor:
        try:
            cursor.execute(
                f'INSERT INTO {config().get("database")}.plane '
                f'(speed_in_mph, company, model, reg_number, departure_airport, '
                f'arrival_airport, scheduled_departure, scheduled_arrival) '
                f'VALUES (%s, %s, %s, %s, %s, %s, %s, %s)',
                tuple(request_json.values())
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

        connection.commit()
        connection.close()

        logging.info('POST OK')

        request_json['id'] = cursor.lastrowid

        return func.HttpResponse(
            body=json.dumps(obj=request_json, indent=4, default=default),
            status_code=201,
            headers={'Access-Control-Allow-Origin': '*'}
        )
