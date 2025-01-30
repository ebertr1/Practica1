package com.example.rest;

import controller.Dao.servicies.SinteticaServicies;
import java.util.HashMap;
import controller.tda.list.LinkedList;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;
import models.Sintetica;
import com.google.gson.Gson;

@Path("sintetica")
public class SinteticaApi {
    @Path("/list")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSinteticas() {
        HashMap map = new HashMap<>();
        SinteticaServicies ps = new SinteticaServicies();
        map.put("msg", "Ok");
        map.put("data", ps.listAll().toArray());
        if (ps.listAll().isEmpty()) {
            map.put("data", new Object[] {});
        }
        return Response.ok(map).build();
    }

    @Path("/get/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSintetica(@PathParam("id") Integer id) {
        HashMap map = new HashMap<>();
        SinteticaServicies ps = new SinteticaServicies();
        try {
            ps.setSintetica(ps.get(id));
        } catch (Exception e) {

        }

        map.put("msg", "Ok");
        map.put("data", ps.getSintetica());

        if (ps.getSintetica() == null || ps.getSintetica().getIdSintetica() == 0) {
            map.put("msg", "No se encontró sintetica con ese identificador");
            return Response.status(Status.NOT_FOUND).entity(map).build();
        }

        if (ps.listAll().isEmpty()) {
            map.put("data", new Object[] {});
            return Response.status(Status.BAD_REQUEST).entity(map).build();
        }
        return Response.ok(map).build();
    }

    @Path("/save")
@POST
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public Response save(HashMap map) {
    HashMap res = new HashMap<>();
    Gson g = new Gson();
    String a = g.toJson(map);
    System.out.println("***** JSON recibido: " + a);  // Muestra el JSON recibido

    try {
        SinteticaServicies ps = new SinteticaServicies();
        
        // Verifica que todos los parámetros estén presentes en el map
        if (map.get("Nombre") == null || map.get("Direccion") == null || map.get("Telefono") == null || 
            map.get("latitud") == null || map.get("longitud") == null || map.get("horario") == null) {
            res.put("msg", "Error");
            res.put("data", "Faltan parámetros en la solicitud.");
            return Response.status(Status.BAD_REQUEST).entity(res).build();
        }

        // Asigna los valores si todos los parámetros están presentes
        ps.getSintetica().setNombre(map.get("Nombre").toString());
        ps.getSintetica().setDireccion(map.get("Direccion").toString());
        ps.getSintetica().setTelefono(map.get("Telefono").toString());
        ps.getSintetica().setLatitud(Double.parseDouble(map.get("latitud").toString()));
        ps.getSintetica().setLongitud(Double.parseDouble(map.get("longitud").toString()));
        ps.getSintetica().setHorario(map.get("horario").toString());

        ps.save();
        res.put("msg", "Ok");
        res.put("data", "Guardado correctamente");
        return Response.ok(res).build();

    } catch (NumberFormatException e) {
        // Captura errores al convertir latitud y longitud
        System.out.println("Error al convertir latitud o longitud: " + e.toString());
        res.put("msg", "Error");
        res.put("data", "Error al convertir latitud o longitud. Asegúrate de que son números válidos.");
        return Response.status(Status.BAD_REQUEST).entity(res).build();

    } catch (Exception e) {
        // Captura otros errores
        System.out.println("Error en save data: " + e.toString());
        res.put("msg", "Error");
        res.put("data", "Error desconocido: " + e.toString());
        return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
    }
}


    @Path("/listType")

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getType() {
        HashMap map = new HashMap<>();
        SinteticaServicies ps = new SinteticaServicies();
        map.put("msg", "Ok");
        map.put("data", ps.getSintetica());
        return Response.ok(map).build();
    }

    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(HashMap<String, Object> map) {
        HashMap<String, Object> res = new HashMap<>();

        try {
            SinteticaServicies ps = new SinteticaServicies();
            
            // Verificar que el idSintetica esté presente en el map
            if (!map.containsKey("idSintetica")) {
                res.put("msg", "Error");
                res.put("data", "El campo idSintetica es requerido.");
                return Response.status(Status.BAD_REQUEST).entity(res).build();
            }

            // Obtener el idSintetica del map
            Integer idSintetica = Integer.parseInt(map.get("idSintetica").toString());
            
            // Buscar la Sintetica existente por id
            Sintetica sintetica = ps.get(idSintetica);
            
            // Verificar si la Sintetica fue encontrada
            if (sintetica == null) {
                res.put("msg", "Error");
                res.put("data", "Sintetica no encontrada.");
                return Response.status(Status.NOT_FOUND).entity(res).build();
            }
            
            // Actualizar solo los campos presentes en el map
            if (map.containsKey("Nombre")) {
                sintetica.setNombre(map.get("Nombre").toString());
            }
            if (map.containsKey("Direccion")) {
                sintetica.setDireccion(map.get("Direccion").toString());
            }
            if (map.containsKey("Telefono")) {
                sintetica.setTelefono(map.get("Telefono").toString());
            }
            if (map.containsKey("latitud")) {
                sintetica.setLatitud(Double.parseDouble(map.get("latitud").toString()));
            }
            if (map.containsKey("longitud")) {
                sintetica.setLongitud(Double.parseDouble(map.get("longitud").toString()));
            }
            if (map.containsKey("horario")) {
                sintetica.setHorario(map.get("horario").toString());
            }

            // Llamar al método de actualización
            ps.save(sintetica);
            
            res.put("msg", "Ok");
            res.put("data", "Sintetica actualizada correctamente.");
            return Response.ok(res).build();

        } catch (NumberFormatException e) {
            res.put("msg", "Error");
            res.put("data", "Error al convertir latitud o longitud.");
            return Response.status(Status.BAD_REQUEST).entity(res).build();

        } catch (Exception e) {
            res.put("msg", "Error");
            res.put("data", "Error desconocido: " + e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }


    @Path("/delete")
@POST
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
    public Response delete(HashMap<String, Object> map) {
        HashMap<String, Object> res = new HashMap<>();

        try {
            SinteticaServicies ps = new SinteticaServicies();
            Integer id = Integer.parseInt(map.get("idSintetica").toString());

            Boolean success = ps.delete(id);
            if (success) {
                res.put("msg", "Ok");
                res.put("data", "Eliminado correctamente");
                return Response.ok(res).build();
            } else {
                res.put("msg", "Error");
                res.put("data", "Persona no encontrada");
                return Response.status(Status.NOT_FOUND).entity(res).build();
            }
        } catch (Exception e) {
            System.out.println("Error en delete data" + e.toString());
            res.put("msg", "Error");
            res.put("data", e.toString());
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(res).build();
        }
    }

    
    
}
