/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.apirest;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import conexionABC.apiABC;
import javax.ws.rs.core.Response;

/**
 * REST Web Service
 *
 * @author Cesar Casaretto
 */
@Path("consulta")
public class Consulta  {

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of Consulta
     */
    public Consulta() {
    }

    /**
     * Retrieves representation of an instance of api.Consulta
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJson(@QueryParam("q") String q) {
        try {
        if (q.isEmpty()) {
            Object apiABC = "{\n"
                    + "    \"codigo\": \"" + "g268" + "\",\n"
                    + "    \"error\": \"" + "Parámetros inválidos" + "\"\n"
                    + "}";

            return Response.status(Response.Status.BAD_REQUEST)
                    .type(MediaType.APPLICATION_JSON)
                    .entity(apiABC)
                    .encoding("UTF-8")
                    .build();

        }
        apiABC conexion = new apiABC();
        String listacompleta = conexion.Noticias(q);
        
            if (listacompleta.equals("[]")) {

                Object noticiaVacia = "{\n"
                        + "    \"codigo\": \"g267\",\n"
                        + "    \"error\": \"No se encuentran noticias para el texto: "+ q +"\"\n"
                        + "}";
                return Response.status(Response.Status.NOT_FOUND)
                .type(MediaType.APPLICATION_JSON)
                .entity(noticiaVacia)
                .encoding("UTF-8")
                .build();
            }
        Object apiABC2 = listacompleta;
        return Response.status(Response.Status.OK)
                .type(MediaType.APPLICATION_JSON)
                .entity(apiABC2)
                .encoding("UTF-8")
                .build();
        } catch (Exception e) {
            String errorInterno = "{ \"codigo\": \"g100\", \"error\": \"Error interno del servidor\" }";
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .type(MediaType.APPLICATION_JSON)
                .entity(errorInterno)
                .encoding("UTF-8")
                .build();
        }
    }

    /**
     * PUT method for updating or creating an instance of Consulta
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public void putJson(String content) {
    }
}
