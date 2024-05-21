from flask import Blueprint, jsonify, make_response, request
from controls.facturaDaoControl import FacturaDaoControl
from flask_cors import CORS
api = Blueprint('api', __name__)


#GET es para presentar datos
#POST guardar datos, modificar datos y el inicio de sesion
@api.route('/')
def home():
    return make_response(
        jsonify({"msg":"OK", "code": 200}),
        200
    )

#Lista Personas
#Este es un ejemplo del video del
@api.route('/api/factura')
def lista_factura():
    pd = FacturaDaoControl()
    return make_response(
        jsonify({"msg":"OK", "code": 200, "data": pd.to_dic()}),
        200
    )
 
#Guardar personas   
@api.route('/api/factura/guardar', methods=['POST'])
def guardar_factura():
    pd = FacturaDaoControl()
    data = request.json
    print(type(data))
    if not "apellido" in data.keys():
        return make_response(
            jsonify({"msg":"Falta Datos", "code": 400, "data": {}}),
            400
        )            
    #Todo..validar
    pd._factura._id = data['id']
    pd._factura._nombre = data['nombre']
    pd._factura._apellido = data['apellido']
    pd._factura._direccion = data['direccion']
    pd._factura._telefono = data['telefono']
    pd._factura._dni = data['dni']
    pd._factura._ruc = data['ruc']
    pd._factura._monto = data['monto']
    pd._factura._tipo_ruc = data['tipo_ruc']
    pd.save()
    

    return make_response(
        jsonify({"msg":"OK se ha registrado correctamente", "code": 200, "data":{}}),
        200
    )    