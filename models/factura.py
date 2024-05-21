from models.enumTipoRuc import enumTipoRuc
class Factura():
    def __init__(self):
        self.__id = 0
        self.__nombre = ''
        self.__dni = ''
        self.__apellido = ''
        self.__direccion = ''
        self.__telefono = ''
        self.__monto = 0.0
        self.__monto = 0.0
        self.__tipo_ruc = enumTipoRuc


    @property
    def _dni(self):
        return self.__dni

    @_dni.setter
    def _dni(self, value):
        self.__dni = value

    

    @property
    def _id(self):
        return self.__id

    @_id.setter
    def _id(self, value):
        self.__id = value

    @property
    def _nombre(self):
        return self.__nombre

    @_nombre.setter
    def _nombre(self, value):
        self.__nombre = value

    @property
    def _apellido(self):
        return self.__apellido

    @_apellido.setter
    def _apellido(self, value):
        self.__apellido = value

    @property
    def _direccion(self):
        return self.__direccion

    @_direccion.setter
    def _direccion(self, value):
        self.__direccion = value

    @property
    def _telefono(self):
        return self.__telefono

    @_telefono.setter
    def _telefono(self, value):
        self.__telefono = value

    @property
    def _ruc(self):
        return self.__ruc

    @_ruc.setter
    def _ruc(self, value):
        self.__ruc = value

    @property
    def _monto(self):
        return self.__monto

    @_monto.setter
    def _monto(self, value):
        self.__monto = value


    @property
    def _monto(self):
        return self.__monto

    @_monto.setter
    def _monto(self, value):
        self.__monto = value

    @property
    def _tipo_ruc(self):
        return self.__tipo_ruc

    @_tipo_ruc.setter
    def _tipo_ruc(self, value):
        self.__tipo_ruc = value

    
    @property
    def serializable(self):
     return{
        'id': self._id,
        'nombre': self._nombre,
        'dni': self._dni,
        'apellido': self._apellido,
        'direccion': self._direccion,
        'telefono': self._telefono,
        'monto': self._monto,
        'tipo_ruc': self._tipo_ruc
     }
    

    def deserializar(self, data):
        factura = Factura()
        factura._id = data['id']
        factura._nombre = data['nombre']
        factura._apellido = data['apellido']
        factura._direccion = data['direccion']
        factura._dni = data['dni']
        factura._telefono = data['telefono']
        factura._monto = data['monto']
        factura._tipo_ruc = data['tipo_ruc']

        return factura



