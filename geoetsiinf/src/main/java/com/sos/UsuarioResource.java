package com.sos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.ResponseBuilder;

import javassist.bytecode.stackmap.BasicBlock.Catch;


@Path("/usuarios")
public class UsuarioResource {

    @Context
    private UriInfo uriInfo;
    
    private String url = "jdbc:mysql://localhost:3306/geoetsiinf";
    static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response registerUsuario(Usuario usuario) throws ClassNotFoundException, SQLException{
        Class.forName(DRIVER);
        if(usuario.usuarioConNull()){
            return Response.status(Response.Status.BAD_REQUEST).entity("Usuario a añadir con uno o varios campos nulos").build();
        } else {
            try (Connection conn = DriverManager.getConnection(url, "access", "1Usuario")) {
                //añado a la BD mi usuario
                Statement stmt = conn.createStatement();
                String sql;
                sql="INSERT INTO usuario VALUES('" +usuario.getId() +  "', '"+usuario.getNombre() + "', '"+ usuario.getApellidos()+ "', '"+ usuario.getLocalidad()+ "','"+usuario.getCorreo()+ "', '"+usuario.getEdad()+ "')";
                stmt.executeUpdate(sql);
            } 
            catch (SQLIntegrityConstraintViolationException ex) {
                return Response.status(Response.Status.CONFLICT).entity("Usuario ya existe!").build();
            } 
        }
        return Response.status(Response.Status.OK).entity("Usuario añadido correctamente!").header("Location", uriInfo.getAbsolutePath()+"/"+usuario.getId()).build();
    }
    

    


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsuarios(@QueryParam ("string") String string) throws ClassNotFoundException{
        List<Usuario> usuarios = new ArrayList<Usuario>();
        Class.forName(DRIVER);
        try (Connection conn = DriverManager.getConnection(url, "access", "1Usuario")) {
            Statement stmt = conn.createStatement();
            String sql;
            if(string != null){ //TODO FIX PARA TRATAR COMO QUERY
                sql = "SELECT * FROM geoetsiinf.usuario WHERE usuario.nombre LIKE '%" +string+"%'";
            } else {
                sql = "SELECT * FROM geoetsiinf.usuario";
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
    
    @GET
    @Path("/{usuario_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsuario(@PathParam("usuario_id") String id) throws ClassNotFoundException{
        
        Usuario usuario;
        Class.forName(DRIVER);
        try (Connection conn = DriverManager.getConnection(url, "access", "1Usuario")) {
            if(id==null){//si no hemos metido un string debe de fallar
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            //selecciono de la BD mi usuario
            String sql;
            sql="SELECT * FROM geoetsiinf.usuario WHERE ID='"+id+"'";
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
            usuario = new Usuario(rs.getString("ID"),
            rs.getString("nombre"),
            rs.getString("apellidos"),
            rs.getString("localidad"),
            rs.getString("correo"),
            rs.getInt("edad"));
            } else{
                //reventar
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
        }catch (SQLException e){
            throw new IllegalStateException("Cannot connect to the database", e);
            //return Response.status(Response.Status.BAD_REQUEST).entity("o el usuario que buscas no existe o algo has hecho muy mal (quédate con la primera)").build();
        }
        return Response.status(Response.Status.OK).entity(usuario).header("Content-Location", uriInfo.getAbsolutePath()).build();
    }

    @PUT
    @Path("/{usuario_id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response setUsuario(@PathParam("usuario_id") String id, Usuario usuario) throws ClassNotFoundException {
        Class.forName(DRIVER);
        try (Connection conn = DriverManager.getConnection(url, "access", "1Usuario")) {
            if(id==null){//si no hemos metido un string debe de fallar
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            //selecciono de la BD mi usuario
            Statement stmt = conn.createStatement();
            String sql;
            sql="UPDATE usuario SET nombre = '"+ usuario.getNombre() +
                "',apellidos='"+ usuario.getApellidos() +
                "',localidad='"+ usuario.getLocalidad() +
                "',correo='"+ usuario.getCorreo() +
                "'edad="+ usuario.getEdad()+
                " WHERE ID=" + id;

            stmt.executeQuery(sql);
        } catch (SQLException e){
            throw new IllegalStateException("Cannot connect to the database", e);
        }
         return Response.status(Response.Status.OK).entity("actualizacion de datos hecha correctamente").build();
    }


    //TODO URI
    //TODO PRUEBA
    @DELETE
    @Path("/{usuario_id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteUsuario (@PathParam("usuario_id") String id) throws ClassNotFoundException{
        if(id==null){//si no hemos metido un string debe de fallar
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        Class.forName(DRIVER);
        try (Connection conn = DriverManager.getConnection(url, "access", "1Usuario")) {
            //borro de la BD mi usuario
            Statement stmt = conn.createStatement();
            String sql;
            sql="DELETE FROM geoetsiinf.usuario WHERE ID='"+id+"'";
            stmt.executeUpdate(sql);
        }catch (SQLException e){
            return Response.status(Response.Status.BAD_REQUEST).entity("aprende a hacer peticiones chaval").build();
        }
        return Response.status(Response.Status.OK).entity("usuario borrado correctamente").build();
    }


    @POST
    @Path("/{usuario_id}/tesoros_añadidos")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response publicarTesoro(@PathParam("usuario_id") String id, Tesoro tesoro) throws ClassNotFoundException, SQLException{
       Class.forName(DRIVER);
        if(tesoro.usuarioConNull()){
            return Response.status(Response.Status.BAD_REQUEST).entity("Tesoro a añadir con uno o varios campos nulos").build();
        } else {
            try (Connection conn = DriverManager.getConnection(url, "access", "1Usuario")) {
                //añado a la BD mi usuario
                Statement stmt = conn.createStatement();
                String sql;
                sql="INSERT INTO usuario VALUES("+ tesoro.getFecha() + "', '"+ tesoro.getLatitud()+ "', '"+ 
                tesoro.getLongitud()+ "','"+tesoro.getTamaño()+ "', '"+tesoro.getDificultad()+"','" +tesoro.getTipo_terreno()+ "','"+tesoro.getID_usuario() +"')";
                stmt.executeUpdate(sql);
            } catch (SQLIntegrityConstraintViolationException ex) {
                return Response.status(Response.Status.CONFLICT).entity("tesoro ya existe!").build();
            } 
            return Response.status(Response.Status.OK).entity(tesoro).header("Location", uriInfo.getAbsolutePath()+"/"+id).build();
        }
    }

    @GET
    @Path("/{usuario_id}/tesoros_añadidos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTesorosCreadosUsuario(@PathParam("usuario_id") String id, @QueryParam("string") String string, @QueryParam("fecha") Date date, 
    @QueryParam("desplazamiento") int desplazamiento, @QueryParam("terreno") String terreno, @QueryParam("dificultad") int dificultad){
        List<Tesoro> tesorosA = new ArrayList<Tesoro>();
         
        //TODO QUERYS 
        //TODO METODO

        //simplemente para que no haya 500 errores
        
            return Response.status(Response.Status.OK).entity(tesorosA).build();
    }
    
    //TODO HEADER
    // @POST
    // @Path("/{usuario_id}/tesoros_descubiertos")
    // @Produces(MediaType.TEXT_PLAIN)
    // public Response encontrarTesoro(@PathParam("usuario_id") String id){

        
    //     return Response.status(Response.Status.OK).entity("Se ha añadido el texto correctamente")build();
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


    // @GET
    // @Path("/{usuario_id}/resumen")
    // @Produces(MediaType.APPLICATION_JSON)
    // public Response resumen(@PathParam("usuario_id") String id){
    //     return null;

    // }

    
    private void showDatabases(){
        Connection conn = null;
    try {
        String myConnectionString =
                "jdbc:mysql://127.0.0.1:3306?" +
                "useUnicode=yes&characterEncoding=UTF-8";
        conn = DriverManager.getConnection(myConnectionString, "root", "");
        Statement stmt = conn.createStatement();
        stmt.execute("SHOW DATABASES");
        ResultSet rs = stmt.getResultSet();
        while (rs.next()) {
            System.out.println(rs.getString(1));
        }
        rs.close();
        stmt.close();
        conn.close();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    }


    //TODO AMIGOS

}
