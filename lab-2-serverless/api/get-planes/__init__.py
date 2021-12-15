import logging
import json
import azure.functions as func
import pymysql
from configparser import ConfigParser
import datetime


def default(obj):
    if isinstance(obj, (datetime.datetime, datetime.date)):
        return obj.isoformat()


def parse(config_parser: ConfigParser, field: str) -> str:
    return config_parser.get('sec-database', field).replace("'", "")


def config() -> dict:
    config_parser = ConfigParser()
    config_parser.read('get-planes/config.ini')
    return {
        'host': parse(config_parser, 'database_server'),
        'user': parse(config_parser, 'database_username'),
        'password': parse(config_parser, 'database_password'),
        'database': parse(config_parser, 'database_name'),
        'ssl_ca': 'get-planes/BaltimoreCyberTrustRoot.crt.pem'
    }


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
        cursor.execute(f'SELECT * FROM {config().get("database")}.plane')

        keys = [
            'id', 'speed_in_mph', 'company', 'model', 'reg_number',
            'departure_airport', 'arrival_airport', 'scheduled_departure',
            'scheduled_arrival'
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
