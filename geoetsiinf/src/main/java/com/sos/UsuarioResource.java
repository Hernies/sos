package com.sos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import com.sos.Usuario;

import org.omg.CosNaming._BindingIteratorImplBase;


@Path("/usuarios")
public class UsuarioResource {
    private String url = "jdbc:mysql://localhost:3306/geoetsiinfdb";
    static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    // @POST
    // @Consumes(MediaType.APPLICATION_JSON)
    // @Produces(MediaType.TEXT_PLAIN)
    // public Response registerUsuario(@PathParam("usuario_id") String id){
    //     //TODO MÉTODO        

    //     return "ok";
    // }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsuarios(@QueryParam ("string") String string) throws ClassNotFoundException{
        List<Usuario> usuarios = new ArrayList<Usuario>();
        Class.forName(DRIVER);
        try (Connection conn = DriverManager.getConnection(url, "mysql.geoetsiinf", "mysql.geoetsiinf");) {
            Statement stmt = conn.createStatement();
            String sql;
            if(string.isEmpty()){
                sql = "SELECT * FROM usuario";
            } else {//TODO FIX PARA TRATAR COMO QUERY
                sql = "SELECT * FROM usuario";
            }

            ResultSet rs = stmt.executeQuery(sql);
                while(rs.next()){
                    String ID = rs.getString("ID");
                    String nombre = rs.getString("nombre");
                    String apellidos = rs.getString("apellidos");
                    String localidad = rs.getString("localidad");
                    String correo = rs.getString("correo");
                    int edad = rs.getInt("edad");
                    Usuario usuario = new Usuario(ID,nombre,apellidos,localidad,correo,edad);
                    usuarios.add(usuario);
                }
        } catch (SQLException e){
            //potencial e.getCause para comprobar errores y dar returns adecuados? DEFINITIVAMENTE
            throw new IllegalStateException("Cannot connect to the database", e);
        } 
        return Response.status(Response.Status.OK).entity(usuarios).build(); //no se que hacer todavía con la parte del header. Ni donde meter los hrefs
    }
    
    // @GET
    // @Path("/{usuario_id}")
    // @Produces(MediaType.APPLICATION_JSON)
    // public Response getUsuario(@PathParam("usuario_id") String id){
        
    //     //TODO MÉTODO        

    //     Usuario user=new Usuario();//simplemente para que no haya 500 errores
    //     return user;
    // }

    // @PUT
    // @Path("/{usuario_id}")
    // @Produces(MediaType.TEXT_PLAIN)
    // public Response setUsuario(@PathParam("usuario_id") String id){
        
    //     //TODO MÉTODO        

    //     return "actualizacion de datos hecha correctamente";
    // }

    // @DELETE
    // @Path("/{usuario_id}")
    // @Produces(MediaType.TEXT_PLAIN)
    // public Response deleteUsuario (@PathParam("usuario_id") String id){
        
    //     //TODO MÉTODO

    //     return "usuario borrado correctamente";
    // }


    // @POST
    // @Path("/{usuario_id}/tesoros_añadidos")
    // @Produces(MediaType.TEXT_PLAIN)
    // public Response publicarTesoro(@PathParam("usuario_id") String id){

    //     //TODO MÉTODO

    //     return "Se ha añadido el texto correctamente";
    // }

    // @GET
    // @Path("/{usuario_id}/tesoros_añadidos")
    // @Produces(MediaType.APPLICATION_JSON)
    // public Response getTesorosCreadosUsuario(@PathParam("usuario_id") String id, @QueryParam("string") String string, @QueryParam("fecha") Date date, 
    // @QueryParam("desplazamiento") int desplazamiento, @QueryParam("terreno") String terreno, @QueryParam("dificultad") int dificultad){
        
    //     //TODO QUERYS 
    //     //TODO METODO

    //     //simplemente para que no haya 500 errores
    //     List<Tesoro> tesoros = new ArrayList<Tesoro>();
    //         return tesoros;
    // }

    // @POST
    // @Path("/{usuario_id}/tesoros_descubiertos")
    // @Produces(MediaType.TEXT_PLAIN)
    // public String encontrarTesoro(@PathParam("usuario_id") String id){

    //     //TODO MÉTODO

    //     return "Se ha añadido el texto correctamente";
    // }

    // @GET
    // @Path("/{usuario_id}/tesoros_descubiertos")
    // @Produces(MediaType.APPLICATION_JSON)
    // public List<Tesoro> getTesorosEncontradoUsuario(@PathParam("usuario_id") String id, @QueryParam("string") String string, @QueryParam("fecha") Date date, 
    // @QueryParam("desplazamiento") int desplazamiento, @QueryParam("terreno") String terreno, @QueryParam("dificultad") int dificultad){
        
    //     //TODO QUERYS 
    //     //TODO METODO

    //     //simplemente para que no haya 500 errores
    //     List<Tesoro> tesoros = new ArrayList<Tesoro>();
    //         return tesoros;
    // }


}
