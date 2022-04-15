package com.sos;

import java.sql.Connection;
import java.sql.DriverManager;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.ResponseBuilder;

import javassist.bytecode.stackmap.BasicBlock.Catch;


@Path("/usuarios")
public class UsuarioResource {
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
                sql="INSERT INTO usuario VALUES('" +usuario.getId() +  "', '"+usuario.getNombre() + "', '"+ usuario.getApellidos()+ "', '"+ 
                usuario.getLocalidad()+ "','"+usuario.getCorreo()+ "', '"+usuario.getEdad()+ "')";
                stmt.executeUpdate(sql);
            } catch (SQLIntegrityConstraintViolationException ex) {
                return Response.status(Response.Status.CONFLICT).entity("Usuario ya existe!").build();
            } 
            return Response.status(Response.Status.OK).entity(usuario).header("Location", uriInfo.getAbsolutePath()+"/"+usuario.getId()).build();
        }
        
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
        Usuario usuario = new Usuario();
        Class.forName(DRIVER);
        try (Connection conn = DriverManager.getConnection(url, "access", "1Usuario")) {
            if(id==null){//si no hemos metido un string debe de fallar
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            //selecciono de la BD mi usuario
            Statement stmt = conn.createStatement();
            String sql;
            sql="SELECT usuario.ID = '"+id+"' FROM geoetsiinf.usuario";
            ResultSet rs = stmt.executeQuery(sql);
            usuario.setId(id);
            usuario.setNombre(rs.getString("nombre"));
            usuario.setApellidos(rs.getString("apellidos"));
            usuario.setLocalidad( rs.getString("localidad"));
            usuario.setCorreo(rs.getString("correo"));
            usuario.setEdad(rs.getInt("edad"));

        }catch (SQLException e){
            //potencial e.getCause para comprobar errores y dar returns adecuados? DEFINITIVAMENTE
            throw new IllegalStateException("Cannot connect to the database", e);
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

    @DELETE
    @Path("/{usuario_id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteUsuario (@PathParam("usuario_id") String id) throws ClassNotFoundException{
        Class.forName(DRIVER);
        try (Connection conn = DriverManager.getConnection(url, "access", "1Usuario")) {
            if(id==null){//si no hemos metido un string debe de fallar
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            //borro de la BD mi usuario
            Statement stmt = conn.createStatement();
            String sql;
            sql="DELETE FROM geoetsiinf.usuario WHERE ID="+id;
            stmt.executeQuery(sql);

        }catch (SQLException e){
            throw new IllegalStateException("Cannot connect to the database", e);
        }
        return Response.status(Response.Status.OK).entity("usuario borrado correctamente").build();
    }


    @POST
    @Path("/{usuario_id}/tesoros_añadidos")
    @Produces(MediaType.TEXT_PLAIN)
    public Response publicarTesoro(@PathParam("usuario_id") String id){
        
        
        //TODO MÉTODO Añadir link al header del usuario creado

        return "Se ha añadido el texto correctamente";
    }

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
}
