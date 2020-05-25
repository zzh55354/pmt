package com.pmt.mattress.rest;


import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.pmt.mattress.Mattress;

/**
 * Root resource (exposed at "Mattress" path)
 */
@Path("Mattresses")
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
    
    @POST
    @Path("/{mattressId}/layers")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Mattress addLayer(@PathParam("mattressId") int mId, int lId) {//take mattress object
    	Mattress.addLayer(mId, lId);
    	return Mattress.getMattress(mId);
    }
    
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public  Mattress createMattress(Mattress m) {//take mattress object
    	 Mattress.createMattress(m);
    	 return Mattress.getMattress(m.getID());
    }
    
    @DELETE
    @Path("/{mattressId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Mattress deleteMattress(@PathParam("mattressId") int mId) {
    	Mattress m = Mattress.getMattress(mId);
    	Mattress.deleteMattress(mId);
    	return m;
    }
    
}
