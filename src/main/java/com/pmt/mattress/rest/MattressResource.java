package com.pmt.mattress.rest;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.pmt.mattress.Layer;
import com.pmt.mattress.Mattress;

/**
 * Root resource (exposed at "Mattress" path)
 */
@Path("Mattress")
public class MattressResource {

    /**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Path("/{mattressId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Mattress getMattress(@PathParam("mattressId") int id) {
    	return Mattress.getMattress(id);
    	
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Mattress> getAllMattress() {
    	return Mattress.getAllMattresses();
    }
    
   /* @PUT
    @Path("/{mattressId}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Mattress addLayer(@PathParam("mattressId"),String Name) {
    	return (Mattress.c);
    }*/
    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public  Mattress createMattress(Mattress m) {
  
    	return Mattress.createMattress(m.getName2());
    	
    }
    
}
