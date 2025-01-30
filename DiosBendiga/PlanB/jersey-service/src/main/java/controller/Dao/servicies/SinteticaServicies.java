package controller.Dao.servicies;

import controller.tda.list.LinkedList;
import models.Sintetica;
import controller.Dao.SinteticaDao;

public class SinteticaServicies {
    private SinteticaDao objSinteticaDao;

    public SinteticaServicies(){
        objSinteticaDao = new SinteticaDao();
    }

    // Método para guardar un objeto Sintetica
    public Boolean save() throws Exception{
        return objSinteticaDao.save();
    }

    // Método para actualizar un objeto Sintetica
    public Boolean update() throws Exception {
        return objSinteticaDao.update(); // Llamar al método update de SinteticaDao
    }

    // Método para eliminar un objeto Sintetica por su ID
    public Boolean delete(Integer id) throws Exception {
        return objSinteticaDao.delete(id); // Llamar al método delete de SinteticaDao
    }

    // Método para listar todos los objetos Sintetica
    public LinkedList listAll() {
        return objSinteticaDao.getlistAll(); // Obtener todos los objetos Sintetica
    }

    // Método para obtener el objeto Sintetica actualmente gestionado
    public Sintetica getSintetica() {
        return objSinteticaDao.getSintetica();
    }

    // Método para establecer el objeto Sintetica que se gestionará
    public void setSintetica(Sintetica sintetica) {
        objSinteticaDao.setSintetica(sintetica);
    }

    // Método para obtener un objeto Sintetica por su ID
    public Sintetica get(Integer id) throws Exception {
        return objSinteticaDao.get(id); // Obtener el objeto Sintetica por su ID
    }
}
