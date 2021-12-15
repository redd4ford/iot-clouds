import logging
import json
import azure.functions as func
import pyodbc
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
        'table_name': parse(config_parser, 'sec-table', 'table_name')
    }


def main(req: func.HttpRequest) -> func.HttpResponse:
    connection_string = f'Driver={{ODBC Driver 17 for SQL Server}};' + \
                        f'Server=tcp:{config()["host"]},1433;' + \
                        f'Database={config()["database"]};' + \
                        f'Uid={config()["user"]};' + \
                        f'Pwd={config()["password"]};' + \
                        f'Encrypt=yes;' + \
                        f'TrustServerCertificate=no;' + \
                        f'Connection Timeout=30;'

    try:
        connection = pyodbc.connect(connection_string)
    except pyodbc.Error as err:
            logging.info(err)
            return func.HttpResponse(
                body='Connection was not established.',
                status_code=504
            )

    logging.info('Connection established.')

    with connection.cursor() as cursor:
        cursor.execute(f'SELECT TOP (100) * FROM {config()["table_name"]} ORDER BY id DESC')

        fetched = list(cursor.fetchall())
        data = [
            {
                'id': fetched[i][0],
                'value': float(fetched[i][1]),
                'measure_time': fetched[i][2],
                'device_id': fetched[i][3].rstrip(),
                'protocol': fetched[i][4]
            }
            for i in range(len(fetched))
        ]

        logging.info(data)

        logging.info('GET OK')

    connection.commit()
    connection.close()

    return func.HttpResponse(
            body=json.dumps(obj=data, indent=4, default=default),
            status_code=200,
            headers={'Access-Control-Allow-Origin': '*'}
    )
