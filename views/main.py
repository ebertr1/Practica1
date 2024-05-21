import sys
sys.path.append('../')

from controls.facturaDaoControl import FacturaDaoControl


factura1 = FacturaDaoControl()

try:
    factura1._factura._id
    factura1._factura._nombre = "Eberson Daniel"
    factura1._factura._dni = "1105765745"
    factura1._factura._apellido = "Guayllas Poma"
    factura1._factura._direccion = "Loja"
    factura1._factura._telefono = "123456"
    factura1._factura._tipo_ruc = 'PROFESIONAL'
    factura1._factura._monto = 100.0
    factura1.save
    factura1._factura = None

    factura1._factura._id
    factura1._factura._nombre = "Anthony Gutierrez"
    factura1._factura._dni = "12020280"
    factura1._factura._apellido = "Gutierrez"
    factura1._factura._direccion = "Calle 1"
    factura1._factura._telefono = "123456"
    factura1._factura._tipo_ruc = 'PROFESIONAL'
    factura1._factura._monto = 100.0
    factura1.save
    factura1._factura = None
    factura1._factura._id
    factura1._factura._nombre = "Benito Manuel"
    factura1._factura._dni = "1105765746"
    factura1._factura._apellido = "Gutierrez Poma"
    factura1._factura._direccion = "Peru"
    factura1._factura._telefono = "123456"
    factura1._factura._tipo_ruc = 'PROFESIONAL'
    factura1._factura._monto = 100.0
    factura1.save
    factura1._factura = None
    factura1._factura._id
    factura1._factura._nombre = "Carlos manule"
    factura1._factura._dni = "1105765746"
    factura1._factura._apellido = "Pinza Poma"
    factura1._factura._direccion = "Peru"
    factura1._factura._telefono = "123456"
    factura1._factura._tipo_ruc = 'PROFESIONAL'
    factura1._factura._monto = 100.0
    factura1.save
    factura1._factura = None
    factura1._factura._id
    factura1._factura._nombre = "David Manuel"
    factura1._factura._dni = "1105765746"
    factura1._factura._apellido = "Gutierrez Poma"
    factura1._factura._direccion = "Peru"
    factura1._factura._telefono = "123456"
    factura1._factura._tipo_ruc = 'PROFESIONAL'
    factura1._factura._monto = 100.0
    factura1.save
    factura1._factura = None




except Exception as e:
    print(e)