package com.sos;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;


@Path("/usuarios")
public class UsuarioResource {

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String registerUsuario(@PathParam("usuario_id") String id){
        //TODO MÉTODO        

        return "ok";
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Usuario> getUsuarios(@QueryParam ("string") String string){
        
        //TODO QUERY

        List<Usuario> usuarios = new ArrayList<Usuario>();//simplemente para que no haya 500 errores
            return usuarios;
    }
    
    @GET
    @Path("/{usuario_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Usuario getUsuario(@PathParam("usuario_id") String id){
        
        //TODO MÉTODO        

        Usuario user=new Usuario();//simplemente para que no haya 500 errores
        return user;
    }

    @PUT
    @Path("/{usuario_id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String setUsuario(@PathParam("usuario_id") String id){
        
        //TODO MÉTODO        

        return "actualizacion de datos hecha correctamente";
    }

    @DELETE
    @Path("/{usuario_id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteUsuario (@PathParam("usuario_id") String id){
        
        //TODO MÉTODO

        return "usuario borrado correctamente";
    }



}
