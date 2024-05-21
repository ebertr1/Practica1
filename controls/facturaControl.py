from models.factura import Factura
from controls.tda.linked.linkedList import LinkedList

class facturaControl():
    def __init__(self):
        self.__factura = Factura()
        self.__lista = LinkedList()

    @property
    def _factura(self):
        return self.__factura

    @_factura.setter
    def _factura(self, value):
        self.__factura = value

    @property
    def _lista(self):
        return self.__lista

    @_lista.setter
    def _lista(self, value):
        self.__lista = value

    @property
    def save(self):
        self._lista.add(self._factura, self._lista._length)    
