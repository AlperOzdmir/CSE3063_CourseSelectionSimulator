import json
import os


class Controller:
    def __init__(self):
        self.error = None

    def execute(self):
        print("Executing Controller")

    def read_json_files(self, path):
        print("Reading json files")
        json_objects = []

        requested_path = os.getcwd() + '/Data/Input/' + path
        file_names = os.listdir(requested_path)

        for file in file_names:
            try:
                with open(requested_path + '/' + file) as f:
                    obj = json.load(f)
                    jo = json.loads(json.dumps(obj))
                    json_objects.append(jo)
            except Exception as e:
                print(e)

        return json_objects
