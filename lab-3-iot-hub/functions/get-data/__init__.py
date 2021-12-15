import logging
import json
import azure.functions as func
import pymysql
from configparser import ConfigParser
import datetime


def default(obj):
    if isinstance(obj, (datetime.datetime, datetime.date)):
        return obj.isoformat()


def parse(config_parser: ConfigParser, section: str, field: str) -> str:
    return config_parser.get(section, field).replace("'", "")


def config() -> dict:
    config_parser = ConfigParser()
    config_parser.read('get-data/config.ini')
    return {
        'host': parse(config_parser, 'sec-database', 'database_server'),
        'user': parse(config_parser, 'sec-database', 'database_username'),
        'password': parse(config_parser, 'sec-database', 'database_password'),
        'database': parse(config_parser, 'sec-database', 'database_name'),
        'ssl_ca': 'get-data/BaltimoreCyberTrustRoot.crt.pem'
    }


def get_table() -> str:
    config_parser = ConfigParser()
    config_parser.read('get-data/config.ini')
    return parse(config_parser, 'sec-table', 'table_name')


def main(req: func.HttpRequest) -> func.HttpResponse:
    try:
        connection = pymysql.connect(**config())
    except Exception as err:
            logging.error(err)
            return func.HttpResponse(
                'Connection was not established.',
                status_code=504
            )

    logging.info('Connection established.')

    with connection.cursor() as cursor:
        cursor.execute(f'SELECT * FROM {config().get("database")}.{get_table()}')

        keys = [
            'id', 'value', 'measure_time', 'device_id', 'protocol'
        ]
        
        fetched = list(cursor.fetchall())
        data = [
            {keys[j]: fetched[i][j] for j in range(len(keys))} 
            for i in range(len(fetched))
        ]

        logging.info('GET OK')
        
        connection.commit()
        connection.close()

        return func.HttpResponse(
            body=json.dumps(obj=data, indent=4, default=default),
            status_code=200,
            headers={'Access-Control-Allow-Origin': '*'}
        )
