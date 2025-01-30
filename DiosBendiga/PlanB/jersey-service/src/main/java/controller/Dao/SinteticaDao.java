package controller.Dao;

import models.Sintetica;
import controller.Dao.implement.AdapterDao;
import controller.tda.list.LinkedList;

public class SinteticaDao extends AdapterDao<Sintetica> {
    private Sintetica sintetica;
    private LinkedList<Sintetica> listAll;

    public SinteticaDao() {
        super(Sintetica.class);
        this.listAll = new LinkedList<>();
    }

    public Sintetica getSintetica() {
        if (sintetica == null) {
            sintetica = new Sintetica();
        }
        return this.sintetica;
    }

    public void setSintetica(Sintetica sintetica) {
        this.sintetica = sintetica;
    }

    public LinkedList<Sintetica> getlistAll() {
        if (listAll.isEmpty()) {
            this.listAll = listAll();
        }
        return listAll;
    }

    public Boolean update() throws Exception {
        try {
            this.merge(getSintetica(), getSintetica().getIdSintetica() - 1);
            this.listAll = getlistAll();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
        public Boolean save() throws Exception {
            Integer id = getlistAll().getSize() + 1;
            sintetica.setIdSintetica(id);
            this.persist(this.sintetica);
            this.listAll = getlistAll();
            return true;
        }



    public Boolean delete(Integer id) throws Exception {
        LinkedList<Sintetica> list = getlistAll();
        Sintetica sintetica = get(id);
        if (sintetica != null) {
            list.remove(sintetica);
            String info = g.toJson(list.toArray());
            saveFile(info);
            this.listAll = list;
            return true;
        } else {
            System.out.println("Sintetica con id " + id + " no encontrada.");
            return false;
        }
    }

    
    
}
