package com.example.rest;

import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;  // Import correcto para Response

import controller.Dao.servicies.SinteticaServicies;
import controller.tda.list.LinkedList;


@Path("myresource")
public class MyResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getIt() {
        // Usamos Map<String, Object> para mayor claridad
        Map<String, Object> mapa = new HashMap<>();
        SinteticaServicies pd = new SinteticaServicies(); 
        String aux = "";
        
        try {
            // Verificamos si la lista de personas está vacía
            aux = "La lista de personas está vacía: " + pd.listAll().isEmpty();
            
            // Generamos una lista de números aleatorios
            LinkedList<Double> listaA = new LinkedList<>();
            for (int i = 0; i < 15; i++) {
                double roundedNumber = Math.round((Math.random() * 100) * 100.0) / 100.0;
                listaA.add(roundedNumber);
            }

            pd.getSintetica().setNombre("Calva");
            pd.getSintetica().setDireccion("Calle 1");
            pd.getSintetica().setTelefono("0989700718");
            pd.getSintetica().setLatitud(-78.467838);
            pd.getSintetica().setLongitud(-0.180653);
            pd.getSintetica().setHorario("00:00");
       
            pd.save();
            
        } catch (Exception e) {
            System.out.println("Error al procesar: " + e.getMessage());
            mapa.put("msg", "Error");
            mapa.put("error", e.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(mapa).build();
        }

        // Agregamos la información al mapa de respuesta
        mapa.put("msg", "Ok");
        mapa.put("data", "Test: " + aux);
        
        // Construimos y devolvemos la respuesta correctamente
        return Response.ok(mapa).build();
    }
}
