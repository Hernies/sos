package com.sos;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/tesoro")
public class TesoroResource {
    
    // @DELETE
    // @Path("{id_tesoro}")
    // @Produces(MediaType.TEXT_PLAIN)
    // @Consumes(MediaType.APPLICATION_JSON) //dudas del consumes
    // public Response deleteTesoro(@PathParam("id_tesoro") int  id_tesoro){

    //     //TODO MÉTODO

    //     return "ä";
    // }

    // @PUT
    // @Path("{id_tesoro}")
    // @Produces(MediaType.TEXT_PLAIN)
    // @Consumes(MediaType.APPLICATION_JSON) //dudas del consumes
    // public Response updateTesoro(Tesoro tesoro, @PathParam("id_tesoro") int  id_tesoro){

    //     //TODO MÉTODO

    //     return "tesoro borrado correctamente";
    // }

}
