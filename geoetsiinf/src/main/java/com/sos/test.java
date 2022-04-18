package Cliente;
import java.net.URI;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Response;
//import javax.ws.rs.client.WebTarget;
//import org.glassfish.jersey.client.ClientConfig;
//import org.json.*;

import Modelo.Usuario;
import Modelo.Tesoro;

public class test {
    public static void main(String[] args) {
    Client client = ClientBuilder.newClient();


    // POST usuarios
    String Json = " \"ID\": \"User1\",\"nombre\": \"Luca\",\"apellidos\": \"Cotan Drulea\",\"localidad\": \"Madrid\",\"correo\": \"cotiCotan@gmail.com\",\"edad\": 21";

    Response res1 = client.target(getBaseURI()).
                    path("/usuarios").
                    request().accept(MediaType.APPLICATION_JSON).
                    post(Entity.json(Json));

    System.out.println("respuesta POST usuarios: " + res1.getStatus() + "\n" +res1.getEntity());
    System.out.println("\n\n");


    // Get usuarios 
    // TODO restriccion con usuario? merece la pena? es lo mismo q lo de abajo
    String res2 = client.target(getBaseURI()).
                    path("/usuarios").
                    request().accept(MediaType.APPLICATION_JSON).
                    get(String.class);

    System.out.println("respuesta GET usuarios: "+res2);
    System.out.println("\n\n");


    // GET usuarios/id
    String res3 = client.target(getBaseURI()).
                    path("/usuarios/User1").
                    request().accept(MediaType.APPLICATION_JSON).
                    get(String.class);

    System.out.println("respuesta GET usuarios/User1: "+res3);
    System.out.println("\n\n");


    // PUT usuarios/id
    Json = " \"ID\": \"User1\",\"nombre\": \"mod1\",\"apellidos\": \"mod2\",\"localidad\": \"mod3\",\"correo\": \"mod4\",\"edad\": 1";

    Response res4 = client.target(getBaseURI()).
                path("usuarios/User1").
                request().accept(MediaType.TEXT_PLAIN).
                put(Entity.json(Json));
    
    System.out.println("respuesta PUT usuarios/User1: " + res4.getStatus() + "\n" +res4.getEntity());
    System.out.println("\n\n");


    // DELETE usuarios/id
    Response res5 = client.target(getBaseURI()).
                    path("usuarios/User1").
                    request().accept(MediaType.TEXT_PLAIN).
                    delete();

    System.out.println("respuesta DELETE usuarios/User1: " + res5.getStatus() + "\n" +res5.getEntity());
    System.out.println("\n\n");


    // POST usuarios varios 
    Json = " \"ID\": \"User1\",\"nombre\": \"Luca\",\"apellidos\": \"Cotan Drulea\",\"localidad\": \"Madrid\",\"correo\": \"cotiCotan@gmail.com\",\"edad\": 21";

    Response res6 = client.target(getBaseURI()).
                    path("/usuarios").
                    request().accept(MediaType.APPLICATION_JSON).
                    post(Entity.json(Json));

    System.out.println("respuesta POST1 (despues de DELETE) usuarios: " + res6.getStatus() + "\n" +res6.getEntity());
    System.out.println("\n\n");

    Json = " \"ID\": \"User2\",\"nombre\": \"nom2\",\"apellidos\": \"ap2\",\"localidad\": \"loc2\",\"correo\": \"co2\",\"edad\": 2";

    Response res7 = client.target(getBaseURI()).
                    path("/usuarios").
                    request().accept(MediaType.APPLICATION_JSON).
                    post(Entity.json(Json));

    System.out.println("respuesta POST2 (despues de DELETE) usuarios: " + res7.getStatus() + "\n" +res7.getEntity());
    System.out.println("\n\n");

    Json = " \"ID\": \"User3\",\"nombre\": \"nom3\",\"apellidos\": \"ap3\",\"localidad\": \"loc3\",\"correo\": \"co3\",\"edad\": 3";

    Response res71 = client.target(getBaseURI()).
                    path("/usuarios").
                    request().accept(MediaType.APPLICATION_JSON).
                    post(Entity.json(Json));

    System.out.println("respuesta POST3 (despues de DELETE) usuarios: " + res71.getStatus() + "\n" +res71.getEntity());
    System.out.println("\n\n");    


    // POST usuario/id/añadir_tesoro
    Json = "\"fecha\": 2022-04-02,\"latitud\": 41.40338,\"longitud\": 2.17403,\"tamaño\": \"grande\",\"dificultad\": \"difícil\",\"terreno\": \"calizo\",\"pista\": \"a\",\"ID\": 1";
    
    Response res81 = client.target(getBaseURI()).
                    path("/usuarios/User1/tesoros_añadidos").
                    request().accept(MediaType.APPLICATION_JSON).
                    post(Entity.json(Json));

    System.out.println("respuesta POST1 tesoro anadido: " + res81.getStatus() + "\n" +res81.getEntity());
    System.out.println("\n\n");

    Json = "\"fecha\": 2022-04-02,\"latitud\": 40.40338,\"longitud\": 1.17403,\"tamaño\": \"grande\",\"dificultad\": \"facil\",\"terreno\": \"calizo\",\"pista\": \"aa\",\"ID\": 2";
    
    Response res82 = client.target(getBaseURI()).
                    path("/usuarios/User1/tesoros_añadidos").
                    request().accept(MediaType.APPLICATION_JSON).
                    post(Entity.json(Json));

    System.out.println("respuesta POST2 tesoro anadido: " + res82.getStatus() + "\n" +res82.getEntity());
    System.out.println("\n\n");
    
    Json = "\"fecha\": 2022-04-02,\"latitud\": 39.40338,\"longitud\": 0.17403,\"tamaño\": \"mediano\",\"dificultad\": \"difícil\",\"terreno\": \"calizo\",\"pista\": \"aaa\",\"ID\": 3";
    
    Response res83 = client.target(getBaseURI()).
                    path("/usuarios/User1/tesoros_añadidos").
                    request().accept(MediaType.APPLICATION_JSON).
                    post(Entity.json(Json));

    System.out.println("respuesta POST3 tesoro anadido: " + res83.getStatus() + "\n" +res83.getEntity());
    System.out.println("\n\n");    

    // GET /tesoros/nearCoord
    String res18 = client.target(getBaseURI()).
                    path("/tesoros/nearCoord?fecha=2022-04-02&latitud=40&longitud=1&terreno=calizo&limite=3").
                    request().accept(MediaType.APPLICATION_JSON).
                    get(String.class);    
    System.out.println("respuesta GET lista tesoros añadidos: "+res18);
    System.out.println("\n\n");

    // GET /usuarios/id_usuario/tesoros_añadidos
    // el date parece que no hace nada
    // TODO se pueden poner mas filtros con desplazamiento y limite pero no veo como afecta en la bbdd
    String res91 = client.target(getBaseURI()).
                    path("/usuarios/User1/tesoros_añadidos/1?&fecha=2022-04-02").
                    request().accept(MediaType.APPLICATION_JSON).
                    get(String.class);
    
    System.out.println("respuesta GET1 tesoros añadidos: "+res91);
    System.out.println("\n\n");

    String res92 = client.target(getBaseURI()).
                    path("/usuarios/User1/tesoros_añadidos/1?&fecha=2022-04-02&dificultad=difícil&terreno=calizo&tamaño=grande").
                    request().accept(MediaType.APPLICATION_JSON).
                    get(String.class);
    
    System.out.println("respuesta GET2 tesoros añadidos: "+res92);
    System.out.println("\n\n");    

    // PUT /{usuario_id}/tesoros_añadidos
    // el date no se usa para nada
    Json = "\"fecha\": 2022-04-01,\"latitud\": 20.40338,\"longitud\": 2.27403,\"tamaño\": \"mediano\",\"dificultad\": \"facil\",\"terreno\": \"calizo\",\"pista\": \"aa\",\"ID\": 2";
    
    Response res16 = client.target(getBaseURI()).
                    path("/usuarios/User1/tesoros_añadidos").
                    request().accept(MediaType.APPLICATION_JSON).
                    post(Entity.json(Json));

    System.out.println("respuesta PUT tesoro modificado: " + res16.getStatus() + "\n" +res16.getEntity());
    System.out.println("\n\n");                    

    // POST usuario/id/tesoros_descubiertos
    //  id???????? -> solo se usa id del tesoro
    Json = "\"fecha\": 2022-04-02,\"latitud\": 41.40338,\"longitud\": 2.17403,\"tamaño\": \"grande\",\"dificultad\": \"difícil\",\"terreno\": \"calizo\",\"pista\": \"a\",\"ID\": 1";
    
    Response res10 = client.target(getBaseURI()).
                    path("/usuarios/User1/tesoros_descubiertos?fecha=2022-04-03").
                    request().accept(MediaType.APPLICATION_JSON).
                    post(Entity.json(Json));

    System.out.println("respuesta POST tesoro descubierto: " + res10.getStatus() + "\n" +res10.getEntity());
    System.out.println("\n\n");


    // GET /usuarios/id_usuario/tesoros_descubiertos
    // TODO se pueden poner mas filtros con desplazamiento y limite pero no veo como afecta en la bbdd
    String res111 = client.target(getBaseURI()).
                    path("/usuarios/User1/tesoros_descubiertos?&fecha=2022-04-02").
                    request().accept(MediaType.APPLICATION_JSON).
                    get(String.class);
    
    System.out.println("respuesta GET1 tesoros descubiertos: "+res111);
    System.out.println("\n\n");

    String res112 = client.target(getBaseURI()).
    path("/usuarios/User1/tesoros_descubiertos?&fecha=2022-04-02&dificultad=difícil&terreno=calizo&tamaño=grande").
    request().accept(MediaType.APPLICATION_JSON).
    get(String.class);

    System.out.println("respuesta GET2 tesoros descubiertos: "+res112);
    System.out.println("\n\n");   

    // DELETE /{usuario_id}/tesoros_añadidos
    Response res17 = client.target(getBaseURI()).
                    path("usuarios/User1/tesoros_añadidos?tesoro_id=1").
                    request().accept(MediaType.TEXT_PLAIN).
                    delete();

    System.out.println("respuesta DELETE User1 tesoros añadidos: " + res17.getStatus() + "\n" +res17.getEntity());
    System.out.println("\n\n");    

    // POST /usuarios/id/amigos
    Response res121 = client.target(getBaseURI()).
                        path("/usuarios/User1/amigos?id_Amigo=User2").
                        request().accept(MediaType.TEXT_PLAIN).
                        post(Entity.json("null")); // no me deja poner el post sin nada en parentesis
    
    System.out.println("Respuesta POST1 añadir amigo: " + res121.getStatus() + "\n" +res121.getEntity());
    System.out.println("\n\n");

    Response res122 = client.target(getBaseURI()).
                        path("/usuarios/User1/amigos?id_Amigo=User3").
                        request().accept(MediaType.TEXT_PLAIN).
                        post(Entity.json("null")); // no me deja poner el post sin nada en parentesis
    
    System.out.println("Respuesta POST2 añadir amigo: " + res122.getStatus() + "\n" +res122.getEntity());
    System.out.println("\n\n");    


    // GET /{ID_Usuario}/amigos
    // TODO probar con filtro de usuario?
    String res13 = client.target(getBaseURI()).
                    path("usuarios/User1/amigos?desplazamiento=1&limite=1").
                    request().accept(MediaType.TEXT_PLAIN).
                    get(String.class);

    System.out.println("respuesta GET amigos de usuario: " + res13);
    System.out.println("\n\n");


    // DELETE /{ID_Usuario}/amigos/{ID_Amigo}
    Response res14 = client.target(getBaseURI()).
                        path("usuarios/User1/amigos/User2").
                        request().accept(MediaType.TEXT_PLAIN).
                        delete();
                    
    System.out.println("respuesta DELETE amigo de usuario: " + res14.getStatus() + "\n" +res14.getEntity());
    System.out.println("\n\n");
                  
    // GET /{ID_Usuario}/resumen
    String res15 = client.target(getBaseURI()).
                        path("usuarios/User1/resumen").
                        request().accept(MediaType.APPLICATION_JSON).
                        get(String.class);
    
    System.out.println("respuesta GET resumen de usuario: " + res15);
    System.out.println("\n\n");            

    
    // creo que no va a ser necesario
    /*Json.put("ID","User1");
    Json.put("nombre","Nom1");
    Json.put("apellidos","ApUser1");
    Json.put("correo","cotiCotan@gmail.com");
    Json.put("localidad","Madrid");
    Json.put("edad",21);*/

    // por si el POST falla con Entity.json(Json) asi lo hacen creando antes el objeto a enviar
//    target.path("rest").path("todos").request()
//        .post(Entity.entity(usuario, MediaType.APPLICATION_JSON));
//    System.out.println("Form response " + response.getEntity(String.class));

    }

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost:8080/geoetsiinf/api/").build();
    }
} 