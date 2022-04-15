package com.sos.resources;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
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
    @Consumes()
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


    @POST
    @Path("/{usuario_id}/tesoros_añadidos")
    @Produces(MediaType.TEXT_PLAIN)
    public String publicarTesoro(@PathParam("usuario_id") String id){

        //TODO MÉTODO

        return "Se ha añadido el texto correctamente";
    }

    @GET
    @Path("/{usuario_id}/tesoros_añadidos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Tesoro> getTesorosCreadosUsuario(@PathParam("usuario_id") String id, @QueryParam("string") String string, @QueryParam("fecha") Date date, 
    @QueryParam("desplazamiento") int desplazamiento, @QueryParam("terreno") String terreno, @QueryParam("dificultad") int dificultad){
        
        //TODO QUERYS 
        //TODO METODO

        //simplemente para que no haya 500 errores
        List<Tesoro> tesoros = new ArrayList<Tesoro>();
            return tesoros;
    }

    @POST
    @Path("/{usuario_id}/tesoros_descubiertos")
    @Produces(MediaType.TEXT_PLAIN)
    public String encontrarTesoro(@PathParam("usuario_id") String id){

        //TODO MÉTODO

        return "Se ha añadido el texto correctamente";
    }

    @GET
    @Path("/{usuario_id}/tesoros_descubiertos")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Tesoro> getTesorosEncontradoUsuario(@PathParam("usuario_id") String id, @QueryParam("string") String string, @QueryParam("fecha") Date date, 
    @QueryParam("desplazamiento") int desplazamiento, @QueryParam("terreno") String terreno, @QueryParam("dificultad") int dificultad){
        
        //TODO QUERYS 
        //TODO METODO

        //simplemente para que no haya 500 errores
        List<Tesoro> tesoros = new ArrayList<Tesoro>();
            return tesoros;
    }


}
