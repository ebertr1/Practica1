from flask import Blueprint, jsonify, abort, request, render_template, redirect
from controls.facturaDaoControl import FacturaDaoControl
from flask_cors import CORS
router = Blueprint('router', __name__)


#GET es para presentar datos
#POST guardar datos, modificar datos y el inicio de sesion
@router.route('/')
def home():
    return render_template("template.html")
#Lista Personas
#Este es un ejemplo del video del
@router.route('/facturas')
def lista_factura():
    pd = FacturaDaoControl()
    return render_template("nene/lista.html", lista=pd.to_dic())

@router.route('/facturas/ver')
def ver_guardar():
    return render_template("nene/guardar.html")

@router.route('/facturas/editar/<pos>')
def ver_editar(pos):
    pd = FacturaDaoControl()
    nene = pd._list().get(int(pos)-1)
    return render_template("nene/editar.html", data=nene)


#Guardar personas   
@router.route('/facturas/guardar', methods=['POST'])
def guardar_personas():
    pd = FacturaDaoControl()
    data = request.form

    if not "apellido" in data.keys():
        abort(400)            
    #Todo..validar
    pd._factura._id = data['id']
    pd._factura._nombre = data['nombre']
    pd._factura._apellido = data['apellido']
    pd._factura._direccion = data['direccion']
    pd._factura._telefono = data['telefono']
    pd._factura._dni = data['dni']
    pd._factura._monto = data['monto']
    pd._factura._tipo_ruc = data['tipo_ruc']
    pd.save()
   
    return redirect("/facturas", code=302)


@router.route('/facturas/modificar', methods=['POST'], )
def modificar_personas():
    pd = FacturaDaoControl()
    data = request.form
    pos = data["id"]
    print("-----------------"+data["id"])
    nene = pd._list().get(int(pos)-1)
    if not "ruc" in data.keys():
        abort(400)            
    #Todo..validar
    pd._factura._id = data['id']
    pd._factura._nombre = data['nombre']
    pd._factura._apellido = data['apellido']
    pd._factura._direccion = data['direccion']
    pd._factura._telefono = data['telefono']
    pd._factura._dni = data['dni']
    pd._factura._monto = data['monto']
    pd._factura._tipo_ruc = data['tipo_ruc']

    pd.merge(int (pos)-1)
    return redirect("/facturas", code=302)