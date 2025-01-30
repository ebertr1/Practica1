from flask import Blueprint, jsonify, make_response, request, render_template, redirect, abort, url_for, flash
from controls.sinteticaDaoControl import SinteticaDaoControl
from models.sinteticaGrafo import SinteticaGrafo
from controls.historialDaoControl import HistorialDaoControl
#import json

router = Blueprint('api', __name__)

@router.route('/')
def home():
    return render_template('template.html')

@router.route('/grafo')
def grafo():
    return render_template("d3/grafo.html")

@router.route('/grafo/practica')
def grafo_practica():
    return render_template("d3/grafoDato.html")


@router.route('/lista/sinteticas')
def ver_sinteticas():
    sintetica = SinteticaDaoControl()
    return render_template('sintetica/lista.html', lista = sintetica.to_dic())

@router.route('/sinteticas/editar/<pos>')
def ver_editar(pos):
    sintetica = SinteticaDaoControl()
    nene = sintetica._list().get(int(pos)-1)
    return render_template("sintetica/editar.html", data=nene)

@router.route('/historial/ver')
def ver_guardad():
    return render_template('sintetica/guardar.html')


@router.route('/historial/guardar', methods=['POST'])
def guardar_factura():
    sintetica = SinteticaDaoControl()
    sintetica._sintetica._nombre = request.form['nombre'] 
    sintetica._sintetica._latitud = (request.form['latitud'])
    sintetica._sintetica._longitud = (request.form['longitud'])
    sintetica._sintetica._direccion = request.form['direccion']
    sintetica._sintetica._telefono = request.form['telefono']
    sintetica._sintetica._horario = request.form['horario']
    sintetica.save
    return redirect('/lista/sinteticas', code = 302)
    
@router.route('/sintetica/eliminar/', methods =['POST'])
def eliminar_personas():
    sintetica = SinteticaDaoControl()
    pos = request.form["id"]
    sintetica._delete(int(pos)-1)
    return redirect("/lista/sinteticas",code=302)

@router.route('/sintetica/modificar', methods=['POST'], )
def modificar_personas():
    sintetica = SinteticaDaoControl()
    data = request.form
    pos = data["id"]
    print("-----------------"+data["id"])
    nene = sintetica._list().get(int(pos)-1)
  
    sintetica._sintetica = nene
    sintetica._sintetica._nombre = request.form['nombre']
    sintetica._sintetica._latitud = (request.form['latitud'])
    sintetica._sintetica._longitud = (request.form['longitud'])
    sintetica._sintetica._direccion = request.form['direccion']
    sintetica._sintetica._telefono = request.form['telefono']
    sintetica._sintetica._horario = request.form['sintetica']
    sintetica.merge(int(pos))
    return redirect("/lista/sinteticas", code=302)

@router.route('/sintetica/grafo_sintetica')
def grafo_sintetica():
    sintetica = SinteticaGrafo()
    sintetica.creat_graph()
    return render_template("d3/grafo.html")

@router.route('/sintetica/matriz')
def grafo_admin():
    sintetica = SinteticaDaoControl()
    listas = sintetica.to_dic()
    ht = SinteticaGrafo()
    matriz2, error = ht.convert_to_weight_matrix()
    return render_template("sintetica/grafo.html", lista=listas, matriz=matriz2)

@router.route('/sintetica/peso', methods=['POST'])
def create_weights():
    sintetica = SinteticaDaoControl()
    data = request.form
    origen_id = int(data["origen"])
    destino_id = int(data["destino"])
    print('\n\n\nArchivos')
    objeto_org = sintetica._list().binary_search_models(origen_id, '_id')
    objeto_dest = sintetica._list().binary_search_models(destino_id, '_id')
    hist = HistorialDaoControl()
    if objeto_org and objeto_dest:
        sintetica = SinteticaGrafo()
        print(origen_id, destino_id)
        hist._historial._origin = origen_id
        hist._historial._destination = destino_id
        if hist.save == False:
            flash('Ya existe un historial con los mismos valores', 'error')
        else:
            hist.save
            sintetica.creat_graph(objeto_org, objeto_dest)
            distancia = sintetica.calculate_distance(objeto_org, objeto_dest)
            message = 'Distancia entre {} y {} es: {} km'.format(objeto_org._nombre, objeto_dest._nombre, distancia)
            flash(message, 'info') 
    else:
        flash('No se encontraron los hoteles especificados', 'error')

    return redirect(url_for('api.grafo_admin'))

@router.route('/sintetica/camino')
def buscar_caminos():
    sintetica = SinteticaDaoControl()
    return render_template("sintetica/distancia.html", lista = sintetica.to_dic())


@router.route('/sintetica/calcular_recorrido', methods=['POST'])
def calcular_recorrido():
    data = request.form
    origen_id = int(data["origen"])
    destino_id = int(data["destino"])
    algoritmo = int(data["algoritmo"])

    sintetica = SinteticaDaoControl()
    objeto_org = sintetica._list().binary_search_models(origen_id, '_id')
    objeto_dest = sintetica._list().binary_search_models(destino_id, '_id')

    if objeto_org and objeto_dest:
        sintetica = SinteticaGrafo()
        matriz, error = sintetica.convert_to_weight_matrix()
        if error == 1:
            message = 'No se encuentran conectados todos los vertices'
        else:
            camino, distancia = sintetica.find_shortest_path(origen_id, destino_id, algoritmo)
            message = f'El camino es {camino} y su distancia es {round(distancia,2)} km.'
        flash(message, 'info')  
    else:
        flash('No se encontraron los hoteles especificados', 'error')

    return redirect(url_for('api.buscar_caminos'))