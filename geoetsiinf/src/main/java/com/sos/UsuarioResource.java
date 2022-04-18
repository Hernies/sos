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

import javax.decorator.Delegate;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.ResponseBuilder;



@Path("/usuarios")
public class UsuarioResource {

    @Context
    UriInfo uriInfo;
    @Context
    Request request;

    private String url = "jdbc:mysql://localhost:3306/geoetsiinf";
    static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerUsuario(Usuario usuario) throws ClassNotFoundException, SQLException{
        if(usuario==null||usuario.conNull()){
            return Response.status(Response.Status.BAD_REQUEST).entity("Usuario a añadir con uno o varios campos nulos").build();
        } else {
            Class.forName(DRIVER);
            try (Connection conn = DriverManager.getConnection(url, "access", "1Usuario")) {
                //añado a la BD mi usuario
                Statement stmt = conn.createStatement();
                String sql;
                sql="INSERT INTO usuario VALUES('" +usuario.getId() +  "', '"+usuario.getNombre() + "', '"+ usuario.getApellidos()+ "', '"+ usuario.getLocalidad()+ "','"+usuario.getCorreo()+ "', '"+usuario.getEdad()+ "')";
                stmt.executeUpdate(sql);
            } 
            catch (SQLIntegrityConstraintViolationException e) {
                //throw new IllegalStateException("Cannot connect to the database", e);
                return Response.status(Response.Status.CONFLICT).entity("Usuario ya existe!").build();
            }
            catch (SQLException e){
                throw new IllegalStateException("Cannot connect to the database", e);
            }
        }
        return Response.status(Response.Status.OK).entity("Usuario añadido correctamente!").header("Location", uriInfo.getAbsolutePath()+usuario.getId()).build();
    }
    

    


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsuarios(@QueryParam ("string") String string) throws ClassNotFoundException{
        List<Usuario> usuarios = new ArrayList<Usuario>();
        Class.forName(DRIVER);
        try (Connection conn = DriverManager.getConnection(url, "access", "1Usuario")) {
            Statement stmt = conn.createStatement();
            String sql;
            if(string != null){
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
            rs.next();
            usuario = new Usuario();
            usuario.setId(rs.getString("ID"));
            usuario.setNombre(rs.getString("nombre"));
            usuario.setApellidos(rs.getString("apellidos"));
            usuario.setLocalidad(rs.getString("localidad"));
            usuario.setCorreo(rs.getString("correo"));
            usuario.setEdad(rs.getInt("edad"));
            
        }catch (SQLException e){
            return Response.status(Response.Status.BAD_REQUEST).entity("el usuario que buscas no existe ").build();
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
            if(usuario==null){//si no hemos metido un string debe de fallar
                return Response.status(Response.Status.BAD_REQUEST).entity("datos a actualizar no adjuntados").build();
            }
            if(usuario.hasEmpty()){
                return Response.status(Response.Status.BAD_REQUEST).entity("datos string a actualizar vacios o con espacio en blanco").build();
            }
            //selecciono de la BD mi usuario
            Statement stmt = conn.createStatement();
            String sql;
            sql="UPDATE usuario SET nombre = '"+ usuario.getNombre() +
                "',apellidos='"+ usuario.getApellidos() +
                "',localidad='"+ usuario.getLocalidad() +
                "',correo='"+ usuario.getCorreo() +
                "',edad="+ usuario.getEdad()+
                " WHERE ID='" + id + "'";

            stmt.executeUpdate(sql);
        } catch (SQLException e){
            throw new IllegalStateException("Cannot connect to the database", e);
        }
         return Response.status(Response.Status.OK).entity("actualizacion de datos hecha correctamente").header("Content-Location", uriInfo.getAbsolutePath()).build();
    }


    //TODO URI
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
            return Response.status(Response.Status.BAD_REQUEST).entity("no se pudo borrar el usuario\n").build();
        }
        return Response.status(Response.Status.OK).entity("usuario borrado correctamente").header("Content-Location", uriInfo.getAbsolutePath()).build();
    }


    @POST
    @Path("/{usuario_id}/tesoros_añadidos")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public Response publicarTesoro(@PathParam("usuario_id") String id, Tesoro tesoro) throws ClassNotFoundException, SQLException{ 
        if(tesoro.conNull()){
            return Response.status(Response.Status.BAD_REQUEST).entity("Tesoro a añadir con uno o varios campos nulos").build();
        }
        // if(!CorrectQueryParams(tesoro.getTamaño(), tesoro.getDificultad())){
        //     return Response.status(Response.Status.BAD_REQUEST).entity("Asegúrate de que el tamaño(pequeño,mediano,grande) y la dificultad(facil,intermedia,dificil) del tesoro son correctas.").build();
        // }
        else {
            Class.forName(DRIVER);
            try (Connection conn = DriverManager.getConnection(url, "access", "1Usuario")) {
                //añado a la BD mi usuario
                Statement stmt = conn.createStatement();
                String sql;
                sql="INSERT INTO tesoro (fecha, latitud, longitud, tamaño, dificultad, tipo_terreno,pista ,ID_usuario) VALUES ("+ tesoro.getFecha() + ", "+ tesoro.getLatitud()+ ", "+ 
                tesoro.getLongitud()+ ", '"+tesoro.getTamaño()+ "', '"+tesoro.getDificultad()+"', '" +tesoro.getTipo_terreno()+ "', '"+tesoro.getPista()+ "', '"+id+"')";
                stmt.executeUpdate(sql);
            } catch (SQLIntegrityConstraintViolationException ex) {
                return Response.status(Response.Status.CONFLICT).entity("tesoro ya existe!").build();
            } 
            return Response.status(Response.Status.OK).entity("tesoro añadido correctamente").header("Location", uriInfo.getAbsolutePath()+"/"+id).build();
        }
    }

    @GET
    @Path("/{usuario_id}/tesoros_añadidos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTesorosCreadosUsuario(@PathParam("usuario_id") String id, @QueryParam("fecha") String date, @QueryParam("dificultad") String dificultad, 
    @QueryParam("terreno") String tipo_terreno, @QueryParam("tamaño") String tamaño,@QueryParam("desplazamiento") int desplazamiento, @QueryParam("limite") int limite) throws ClassNotFoundException{
        List<Tesoro> tesorosA = new ArrayList<Tesoro>();
        //if(!CorrectQueryParams(tamaño, dificultad)){
         //   return Response.status(Response.Status.BAD_REQUEST).entity("Asegúrate de que el tamaño(pequeño,mediano,grande) y/o la dificultad(facil,intermedia,dificil) del tesoro son correctas.").build();
        //}
        Class.forName(DRIVER);
            try (Connection conn = DriverManager.getConnection(url, "access", "1Usuario")) {
                //añado a la BD mi usuario
                String sql="SELECT * FROM tesoro WHERE tesoro.ID_usuario='"+id+"'";
               sql += buildQuery(sql,id,date,dificultad,tipo_terreno,tamaño,desplazamiento,limite);
                PreparedStatement stmt = conn.prepareStatement(sql);
                ResultSet rs = stmt.executeQuery();
                while(rs.next()){
                    Tesoro tesoro = new Tesoro(rs.getInt("id"), rs.getString("fecha"), rs.getFloat("latitud"), rs.getFloat("longitud"), rs.getString("tamaño"), rs.getString("dificultad"), rs.getString("tipo_terreno"),rs.getString("pista"),rs.getString("ID_usuario"));
                    tesorosA.add(tesoro);
                }
            } catch (SQLException ex) {
                throw new IllegalStateException("Cannot connect to the database", ex);
                //return Response.status(Response.Status.CONFLICT).entity("tesoro ya existe!").build();
            }
            return Response.status(Response.Status.OK).entity(tesorosA).header("Location", uriInfo.getAbsolutePath()+"/"+id).build();
        }

        
    
    //TODO checar
    @POST
    @Path("/{usuario_id}/tesoros_descubiertos")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response encontrarTesoro(@PathParam("usuario_id") String id, Tesoro tesoro, @QueryParam("fecha") String date) throws ClassNotFoundException, SQLException{
       
        if(tesoro.conNull()){
            return Response.status(Response.Status.BAD_REQUEST).entity("Tesoro a añadir con uno o varios campos nulos").build();
        }
        Class.forName(DRIVER);
            try (Connection conn = DriverManager.getConnection(url, "access", "1Usuario")) {
                //añado a la BD un tesoro descubierto por el usuario
                Statement stmt = conn.createStatement();
                String sql;
                sql="INSERT INTO encuentra VALUES("+ date + "', '"+ id + "', '" + tesoro.getId()+"')";
                stmt.executeUpdate(sql);
            } catch (SQLIntegrityConstraintViolationException ex) {
                return Response.status(Response.Status.CONFLICT).entity("encuentra ya existe!").build();
            } 
            return Response.status(Response.Status.OK).entity(tesoro).header("Location", uriInfo.getAbsolutePath()+"/"+id).build();
        
     }
     @PUT
     @Path("/{usuario_id}/tesoros_añadidos/{tesoro_id}")
     @Consumes(MediaType.APPLICATION_JSON)
     @Produces(MediaType.TEXT_PLAIN)
     public Response actualizarTesoro(@PathParam("usuario_id") String id, @PathParam("tesoro_id") String idTesoro,Tesoro tesoro) throws ClassNotFoundException, SQLException{
         if(tesoro.conNull()){
             return Response.status(Response.Status.BAD_REQUEST).entity("Tesoro a actualizar con uno o varios campos nulos").build();
         }
         Class.forName(DRIVER);
             try (Connection conn = DriverManager.getConnection(url, "access", "1Usuario")) {
                 //añado a la BD un tesoro descubierto por el usuario
                 Statement stmt = conn.createStatement();
                 String sql;
                 sql="UPDATE tesoro "
                 + "SET fecha='"+tesoro.getFecha()+"',"
                 + "latitud='"+tesoro.getLatitud()+"',"
                 + "longitud='"+tesoro.getLongitud()+"',"
                 + "tamaño='"+tesoro.getTamaño()+"',"
                 + "dificultad='"+tesoro.getDificultad()+"',"
                 + "tipo_terreno='"+tesoro.getTipo_terreno()+"',"
                 + "pista='"+tesoro.getPista()+"'"
                 +"WHERE ID='"+tesoro.getID()+"'"
                 + ";";
                 stmt.executeUpdate(sql);
             } catch (SQLIntegrityConstraintViolationException ex) {
                 return Response.status(Response.Status.CONFLICT).entity("tesoro no pudo ser modificado").build();
             } 
             return Response.status(Response.Status.OK).entity("tesoro fue modificado").header("Location", uriInfo.getAbsolutePath()+"/"+id).build();
         
      }

    @DELETE
    @Path("/{usuario_id}/tesoros_añadidos/{tesoro_id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response removeTesoroTesorosAñadidos(@PathParam("usuario_id") String id, @PathParam("tesoro_id") int tesoro_id) throws ClassNotFoundException{
        Class.forName(DRIVER);
            try (Connection conn = DriverManager.getConnection(url, "access", "1Usuario")) {
                //añado a la BD un tesoro descubierto por el usuario
                Statement stmt = conn.createStatement();
                String sql;
                sql="DELETE  FROM geoetsiinf.tesoro WHERE ID='"+tesoro_id +"' AND ID_usuario='"+id+"' ;";
                stmt.executeUpdate(sql);
            } catch (SQLException ex) {
                return Response.status(Response.Status.CONFLICT).entity("tesoro no pudo ser borrado").build();
            } 
        return Response.status(Response.Status.OK).entity("tesoro borrado correctamente").header("Location", uriInfo.getAbsolutePath()).build();
    }


    @GET
    @Path("/{usuario_id}/tesoros_descubiertos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTesorosEncontradoUsuario(@PathParam("usuario_id") String id, @QueryParam("fecha") String date, @QueryParam("dificultad") String dificultad, 
    @QueryParam("terreno") String tipo_terreno, @QueryParam("tamaño") String tamaño,@QueryParam("desplazamiento") int desplazamiento, @QueryParam("limite") int limite) throws ClassNotFoundException{
        List<Tesoro> tesorosA = new ArrayList<Tesoro>();
        // if(!CorrectQueryParams(tamaño, dificultad)){
        //     return Response.status(Response.Status.BAD_REQUEST).entity("Asegúrate de que el tamaño(pequeño,mediano,grande) y/o la dificultad(facil,mediano,pequeño) del tesoro son correctas.").build();
        // }
        Class.forName(DRIVER);
            try (Connection conn = DriverManager.getConnection(url, "access", "1Usuario")) {
                Statement stmt = conn.createStatement();
                String sql="SELECT * FROM encuentra WHERE (tesoro.ID_usuario='"+ id +"')";
                sql=buildQuery(sql,id,date,dificultad,tipo_terreno,tamaño,desplazamiento,limite);
                ResultSet rs = stmt.executeQuery(sql);
                while(rs.next()){
                    Tesoro tesoro = new Tesoro(rs.getInt("id"), rs.getString("fecha"), rs.getLong("latitud"), rs.getLong("longitud"), rs.getString("tamaño"), rs.getString("dificultad"), rs.getString("tipo_terreno"),rs.getString("pista"),rs.getString("ID_usuario"));
                    tesorosA.add(tesoro);
                }
            } catch (SQLException ex) {
                throw new IllegalStateException("Cannot connect to the database", ex);
                //return Response.status(Response.Status.CONFLICT).entity("tesoro ya existe!").build();
            }
            return Response.status(Response.Status.OK).entity(tesorosA).header("Location", uriInfo.getAbsolutePath()+"/"+id).build();          
     }


    @DELETE
    @Path("/{ID_Usuario}/amigos/{ID_Amigo}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteAmigo (@PathParam("ID_Usuario") String idUser, @PathParam("ID_Usuario") String idAmigo) throws ClassNotFoundException{
        
        Class.forName(DRIVER);
        try (Connection conn = DriverManager.getConnection(url, "access", "1Usuario")) {
            Statement stmt = conn.createStatement();
            String sql;
            sql="DELETE FROM es_amigo WHERE ID_usuario_1='"+idUser+"' AND ID_usuario_2 ='"+idAmigo+"'";
            stmt.executeUpdate(sql);
        }catch (SQLException e){
            return Response.status(Response.Status.BAD_REQUEST).entity("no se pudo borrar el Amigo\n").build();
        }
        return Response.status(Response.Status.OK).entity("Amigo borrado correctamente").build();
    }

    @POST
    @Path("/{usuario_id}/amigos")
    @Produces(MediaType.TEXT_PLAIN)
    public Response añadirAmigo(@PathParam("usuario_id") String id, @QueryParam("id_Amigo") String idAmigo)throws ClassNotFoundException, SQLException{
        if(idAmigo==null){
            return Response.status(Response.Status.BAD_REQUEST).entity("Debe introducir el identificador del usuario que quiere añadir a su lista de amigos").build();
        }
        Class.forName(DRIVER);
        try (Connection conn = DriverManager.getConnection(url, "access", "1Usuario")) {
               //añado a la BD un amigo del usuario
            Statement stmt = conn.createStatement();
            String sql;
            sql="INSERT INTO es_amigo(`ID_usuario_1`,`ID_usuario_2`) VALUES('" + id + "', '" + idAmigo + "')";
            stmt.executeUpdate(sql);
        } catch (SQLIntegrityConstraintViolationException ex) {
            return Response.status(Response.Status.CONFLICT).entity("ese amigo ya se encuentra en tu lista de amigos o no existe!").build();
          } 
          return Response.status(Response.Status.OK).entity("Amigo añadido correctamente!").header("Location", uriInfo.getAbsolutePath()+"/"+idAmigo).build();
    }


    @GET
    @Path("/{ID_Usuario}/amigos")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAmigos(@PathParam("ID_Usuario") String id, @QueryParam("id_Amigo") String idAmigo, @QueryParam("desplazamiento") int desplazamiento,
     @QueryParam("limite") int limite) throws ClassNotFoundException{
        List<String> amigos = new ArrayList<String>();
        Class.forName(DRIVER);
        try (Connection conn = DriverManager.getConnection(url, "access", "1Usuario")) {
            Statement stmt = conn.createStatement();
            String sql;
            if(idAmigo==null){
                sql ="SELECT * FROM geoetsiinf.es_amigo WHERE ID_usuario_1='"+id+"';";
            }
            else{
                sql = "SELECT * FROM geoetsiinf.es_amigo WHERE ID_usuario_1='"+id+"' AND ID_usuario_2 LIKE '%" + idAmigo +"%'";
                if (limite>=1){
                    sql += "LIMIT " + limite;
                }
                if(desplazamiento>=1){
                    sql +=  " OFFSET " + desplazamiento;
                }
            }
                ResultSet rs = stmt.executeQuery(sql);
            while(rs.next()){
                String ID = rs.getString("ID_usuario_2");
                amigos.add(ID);
            }
        } catch (SQLException e){
            //potencial e.getCause para comprobar errores y dar returns adecuados? DEFINITIVAMENTE
            throw new IllegalStateException("Cannot connect to the database", e);
        } 
        return Response.status(Response.Status.OK).entity(amigos).build();
    }

    
    private String buildQuery(String sql,String id, String date, String dificultad, String tipo_terreno, String tamaño,
    int desplazamiento, int limite) {
    String query="";
    if(date!=null){
        query += "AND (tesoro.fecha < '"+ date +"')";
    }else if(dificultad!=null){
        query+= "AND (tesoro.dificultad= '"+ dificultad +"')";  
    }else if(tipo_terreno!=null){
        query += "AND (tesoro.tipo_terreno= '"+tipo_terreno+"')";
    }else if(tamaño!=null){
        query += "AND (tesoro.tamaño = '"+tamaño+"')";
    }
    if (limite>=1){
        query += "LIMIT " + limite;
    }
    if(desplazamiento>=1){
        query +=  " OFFSET " + desplazamiento;
    }
    return query+=";";
}
 
    
@GET
@Path("/{ID_Usuario}/resumen")
@Produces(MediaType.APPLICATION_JSON)
public Response resumenUser(@PathParam("ID_Usuario") String idUsuario) throws ClassNotFoundException{
    
    Resumen resumen;//el resumen va a estar compuesto porlos siguientes elementos:
        Usuario datos=new Usuario();//datos del perfil del usuario
        ArrayList<Tesoro> tesorosEncontrados = new ArrayList<Tesoro>();//ultimos 5 tesoros encontrados
        int nTesorosEncontrados=0;//numero de tesoros encontrados por el usuario
        int numeroAmigos=0;//numero de amigos que tiene el usuario
        int numTesorosañadidos=0; //numero de tesoros que ha añadido el usuario
    
    Class.forName(DRIVER);
    
    try (Connection conn = DriverManager.getConnection(url, "access", "1Usuario")) {

        //Cogemos los datos del usuario
        Statement informacion = conn.createStatement();
        String sqlInforUser;
        sqlInforUser="SELECT * FROM usuario WHERE ID_usuario= '" + idUsuario +"';";
        ResultSet rsUser = informacion.executeQuery(sqlInforUser);
        if(rsUser.next())
            datos = new Usuario(rsUser.getString("ID"),
                            rsUser.getString("nombre"),
                            rsUser.getString("apellidos"),
                            rsUser.getString("localidad"),
                            rsUser.getString("correo"),
                            rsUser.getInt("edad"));

        //cogemos los 5 primeros tesoros de la tabla de tesoros encontrados por este user
        Statement listaTesoros = conn.createStatement();
        String sqlLista;
        sqlLista="SELECT TOP 5 FROM encuentra INNER JOIN tesoro ON encuentra.ID_tesoro= tesoro.ID_tesoro WHERE ID_usuario= '" + idUsuario +"' ORDER BY fecha;";
        ResultSet rsLista = listaTesoros.executeQuery(sqlLista);
        while(rsLista.next()){
                Tesoro tesoro = new Tesoro(rsLista.getInt("ID"),
                            rsLista.getString("fecha"), 
                            rsLista.getLong("latitud"), 
                            rsLista.getLong("longitud"), 
                            rsLista.getString("tamaño"), 
                            rsLista.getString("dificultad"), 
                            rsLista.getString("tipo_terreno"),
                            rsLista.getString("pista"),
                            rsLista.getString("ID_usuario"));
                tesorosEncontrados.add(tesoro);
        }
        
        //contamos la cantidad de filas que tiene la tabla encuentra para el usuario dado
        Statement encontradosPorUser = conn.createStatement();
        String sqlNEncontrados;
        sqlNEncontrados="SELECT COUNT(ID_tesoro) FROM encuentra WHERE ID_usuario= '" + idUsuario +"';";
        ResultSet rsFound = encontradosPorUser.executeQuery(sqlNEncontrados);
        if(rsFound.next())
            nTesorosEncontrados=rsFound.getInt(1); 
        
        //contamos la cantidad de amigos que tiene el usuario añadidos en el sistema
        Statement amiguetes = conn.createStatement();
        String sqlNAmigos;
        sqlNAmigos="SELECT COUNT(ID_tesoro) FROM es_amigo WHERE ID_usuario_1= '" + idUsuario +"';";
        ResultSet rsAmigos = amiguetes.executeQuery(sqlNAmigos);
        if(rsAmigos.next())
            numeroAmigos=rsAmigos.getInt(1);

        //contamos la cantidad de tesoro que el usuario ha añadido en el sistema
        Statement añadidos = conn.createStatement();
        String sqlNAñadidos;
        sqlNAñadidos="SELECT COUNT(ID_tesoro) FROM tesoro WHERE ID_usuario= '" + idUsuario +"';";
        ResultSet rsAdded = añadidos.executeQuery(sqlNAñadidos);
        while(rsAdded.next()){
            numTesorosañadidos=rsAdded.getInt(1);
        }
        resumen=new Resumen(datos,tesorosEncontrados,nTesorosEncontrados,numeroAmigos,numTesorosañadidos);
    }
    catch (SQLException e){
        throw new IllegalStateException("uh uh... stinky stinky....", e);
    } 
    return Response.status(Response.Status.OK).entity(resumen).build();

    
}

    // private void showDatabases(){
    //     Connection conn = null;
    // try {
    //     String myConnectionString =
    //             "jdbc:mysql://127.0.0.1:3306?" +
    //             "useUnicode=yes&characterEncoding=UTF-8";
    //     conn = DriverManager.getConnection(myConnectionString, "root", "");
    //     Statement stmt = conn.createStatement();
    //     stmt.execute("SHOW DATABASES");
    //     ResultSet rs = stmt.getResultSet();
    //     while (rs.next()) {
    //         System.out.println(rs.getString(1));
    //     }
    //     rs.close();
    //     stmt.close();
    //     conn.close();
    // } catch (SQLException ex) {
    //     ex.printStackTrace();
    // }
    // }

}
