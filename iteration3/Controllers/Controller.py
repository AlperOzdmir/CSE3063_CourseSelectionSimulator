import json
import os


class Controller:
    def __init__(self):
        self.error = None

    def read_json_files(self, path):
        json_objects = []
        requested_path = os.getcwd() + '/Data/Input/' + path
        print("--C--->Reading json files on path: " + requested_path)
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

    def export_json_file(self, object):
        path = ""

        if object.__class__.__name__ == "Student":
            file_name = object.id
            json_object = object.to_json()
            path = "Output/Students"
            content = json.dumps(json_object)
        elif object.__class__.__name__ == "Advisor":
            file_name = object.get_name() + object.get_surname()
            file_name = file_name.replace(" ", "")
            json_object = object.to_json()
            path = "Output/Advisors"
            content = json.dumps(json_object)
        elif object.__class__.__name__ == "RegistrationError":
            file_name = "RegistrationErrors"
            json_object = object.to_json()
            content = json.dumps(json_object)
        else:
            return False

        full_file_name = os.getcwd() + '/iteration2/Data/' + path + '/' + file_name + '.json'

        try:
            directory = os.path.dirname(full_file_name)

            if not os.path.exists(directory):
                os.makedirs(directory)

            with open(full_file_name, 'w') as f:
                f.write(content)
        except OSError as e:
            print("An error occurred while exporting .json file.")
            print(e)
        return False
