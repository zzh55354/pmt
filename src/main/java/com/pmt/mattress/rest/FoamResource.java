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


@Path("Foams")
public class FoamResource {
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Foam> getAllFoam() {
    	return Foam.getAllFoams();
    }
	
	@GET
	@Path("/{FoamId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Foam getLayer(@PathParam("FoamId") int id) {
		return Foam.getFoam(id);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON) 
	@Consumes(MediaType.APPLICATION_JSON)
	public Foam createFoam(Foam f) {
		Foam.createFoam(f);
		return Foam.getFoam(Foam.getFoamID(f.getName()));
	}
	
	@PUT
	@Path("/{FoamId}/Update/Name")
	@Produces(MediaType.APPLICATION_JSON) 
	@Consumes(MediaType.APPLICATION_JSON)
	public Foam updateName(@PathParam("FoamId") int id,String newName) {
		Foam.updateName(id, newName);
		return Foam.getFoam(id);
	}
	
	@PUT
	@Path("/{FoamId}/Update/Price")
	@Produces(MediaType.APPLICATION_JSON) 
	@Consumes(MediaType.APPLICATION_JSON)
	public Foam updateName(@PathParam("FoamId") int id,Double newPrice) {
		Foam.updatePrice(id, newPrice);
		return Foam.getFoam(id);
	}
	
	@DELETE
	@Path("/{FoamId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteFoam(@PathParam("FoamId") int id) {
		Foam.deleteFoam(id);
	}
	
}
