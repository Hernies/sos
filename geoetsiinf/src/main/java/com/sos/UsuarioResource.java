package com.sos;

import java.util.ArrayList;
import java.util.List;


import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.xml.crypto.dsig.XMLObject;

@Path("/usuarios")
public class UsuarioResource {
    @GET
    @Produces(MediaType.APPLICATION_XML)
    public List<Usuario> getUsuarios(){
        return 
    }




}
