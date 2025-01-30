package controller.Dao.servicies;
import controller.tda.list.LinkedList;
import models.Sintetica;
import controller.Dao.SinteticaDao;

public class SinteticaServicies {
    private SinteticaDao obj;
    public SinteticaServicies(){
        obj = new SinteticaDao();
    }
    public Boolean save() throws Exception{
        return obj.save();
    }
    public Boolean update() throws Exception{
        return obj.update();
    }
    public Boolean delete(Integer id) throws Exception {
        return obj.delete(id); // Llamar al método delete de PersonaDao
    }
    

    public LinkedList listAll(){
        return obj.getlistAll();

    }

    public Sintetica getSintetica(){
        return obj.getSintetica();
    }

    public void setSintetica( Sintetica sintetica){
        obj.setSintetica(sintetica);
    }

    public Sintetica get(Integer id) throws Exception {
        return obj.get(id);
    }

}
