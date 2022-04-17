package com.sos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.inject.ResolutionException;
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
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;



@Path("/tesoros")
public class TesoroResource {
    
    @Context
    private UriInfo uriInfo;
    
    private String url = "jdbc:mysql://localhost:3306/geoetsiinf";
    static final String DRIVER = "com.mysql.cj.jdbc.Driver";

    @GET
    @Path("/near")
    @Produces(MediaType.APPLICATION_JSON)
    public Response nearTesoro(@QueryParam("latitud") Long latitud,@QueryParam("longitud") Long longitud, @QueryParam("fecha") Date date, @QueryParam("dificultad") String dificultad, 
    @QueryParam("terreno") String tipo_terreno, @QueryParam("tamaño") String tamaño,@QueryParam("desplazamiento") int desplazamiento, @QueryParam("limite") int limite) throws ClassNotFoundException{
        List<Tesoro> tesorosA = new ArrayList<Tesoro>();
        if(latitud==null||longitud==null){
           return Response.status(Response.Status.BAD_REQUEST).entity("latitud y/o longitud son nulos, por favor introduce una latitud y una longitud").build();
        }
        Class.forName(DRIVER);
            try (Connection conn = DriverManager.getConnection(url, "access", "1Usuario")) {
                Statement stmt = conn.createStatement();
                String sql;
                //no se especifica distancia así que asumo que es en un radio de 10 kilómetros a la redonda
                sql= "SELECT z.id, z.latitud, z.longitud, p.ud_distancia* DEGREES(ACOS(LEAST(1.0, COS(RADIANS(p.latpoint))* COS(RADIANS(z.latitud))* COS(RADIANS(p.longpoint) - RADIANS(z.longitud)) + SIN(RADIANS(p.latpoint))*SIN(RADIANS(z.latitud))))) AS distance_in_km FROM tesoro AS z" 
                +"/*parámetros de query*/"
                + "JOIN (SELECT "+ latitud +" AS latpoint, "+ longitud +" AS longpoint, 10 AS radio, 111.045 AS ud_distancia) AS p ON 1=1 "
                + "WHERE z.latitud" 
                + "BETWEEN p.latpoint  - (p.radio / p.ud_distancia) AND p.latpoint + (p.radio / p.ud_distancia) AND z.longitud "
                + "BETWEEN p.longpoint - (p.radio / (p.ud_distancia * COS(RADIANS(p.latpoint)))) AND p.longpoint + (p.radio / (p.ud_distancia * COS(RADIANS(p.latpoint))))"
                + "ORDER BY distance_in_km";
                sql = buildQuery(sql,date,dificultad,tipo_terreno,tamaño,desplazamiento,limite);
                ResultSet rs = stmt.executeQuery(sql);
                while(rs.next()){
                    Tesoro tesoro = new Tesoro(rs.getInt("id"), rs.getDate("fecha"), rs.getFloat("latitud"), rs.getFloat("longitud"), rs.getString("tamaño"), rs.getString("dificultad"), rs.getString("tipo_terreno"),rs.getString("ID_usuario"));
                    tesorosA.add(tesoro);
                }
            } catch (SQLException ex) {
                throw new IllegalStateException("Cannot connect to the database", ex);
                //return Response.status(Response.Status.CONFLICT).entity("tesoro ya existe!").build();
            }
            return Response.status(Response.Status.OK).entity(tesorosA).header("Location", uriInfo.getAbsolutePath()).build();
        }
    

    private String buildQuery(String sql, Date date, String dificultad, String tipo_terreno, String tamaño,
    int desplazamiento, int limite) {
    //añade a la query el filtro deseado
    String query="";
        if(date!=null){
            query += "AND (z.fecha < '"+ date +"')";
        }else if(dificultad!=null){
            query+= "AND (z.dificultad= '"+ dificultad +"')";  
        }else if(tipo_terreno!=null){
            query += "AND (z.tipo_terreno= '"+tipo_terreno+"')";
        }else if(tamaño!=null){
            query += "AND (z.tamaño = '"+tamaño+"')";
        }
        if (limite>=1){
            query += "LIMIT " + limite;
        }
        if(desplazamiento>=1){
            query +=  " OFFSET " + desplazamiento;
        }
        query+=";";
    return sql+=query;
}

}
