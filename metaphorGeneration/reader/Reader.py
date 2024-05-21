import json


class Reader:
    def __init__(self, file_name: str):
        self.__file_name = file_name

    def read(self):
        file = json.load(open(self.__file_name, 'r'))
        return file
