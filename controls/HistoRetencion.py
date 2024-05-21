from controls.tda.stack.stack import Stack
from models.retencion import Retencion

import json
import os

class Historial():
    def __init__(self):
        self.__historial = Stack(10)

    def push(self, retencion: Retencion):
        self.__historial.push(retencion)

    
    def print(self):
        self.__historial.print

    def size(self):
        return self.__historial.length

    def save_to_json(self, filename):
        historial_list = []
        while not self.__historial.is_empty():
            retencion = self.__historial.pop
            historial_list.append(retencion)

        # Obtenemos el directorio actual
        current_directory = os.getcwd()

        # Unimos la dirección de guardado con el nombre del archivo
        file_path = os.path.join(current_directory, filename)

        # Guardamos los datos en el archivo JSON
        with open(file_path, 'w') as file:
            json.dump(historial_list, file, indent=4)
        
        return historial_list