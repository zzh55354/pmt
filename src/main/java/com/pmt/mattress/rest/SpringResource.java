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

import com.pmt.mattress.mattress.Spring;

@Path("Springs")
public class SpringResource {

	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Spring> getAllSprings() {
    	return Spring.getAllSprings();
    }
	
	@GET
	@Path("/{SpringID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Spring getLayer(@PathParam("SpringID") int id) {
		return Spring.getSpring(id);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON) 
	@Consumes(MediaType.APPLICATION_JSON)
	public Spring createSpring(Spring s) {
		Spring.createSpring(s);
		return Spring.getSpring(Spring.getSpringID(s.getName()));
	}
	
	@PUT
	@Path("/{SpringID}/Update/Name")
	@Produces(MediaType.APPLICATION_JSON) 
	@Consumes(MediaType.APPLICATION_JSON)
	public Spring updateName(@PathParam("SpringID") int id,String newName) {
		Spring.updateSpringName(id, newName);
		return Spring.getSpring(id);
	}
	
	@PUT
	@Path("/{SpringID}/Update/Twin_Price")
	@Produces(MediaType.APPLICATION_JSON) 
	@Consumes(MediaType.APPLICATION_JSON)
	public Spring updateSpringTwinPrice(@PathParam("SpringID") int id,Double newPrice) {
		Spring.updateSpringTwinPrice(id, newPrice);
		return Spring.getSpring(id);
	}
	
	@PUT
	@Path("/{SpringID}/Update/Txl_Price")
	@Produces(MediaType.APPLICATION_JSON) 
	@Consumes(MediaType.APPLICATION_JSON)
	public Spring updateSpringTxlPrice(@PathParam("SpringID") int id,Double newPrice) {
		Spring.updateSpringTwinPrice(id, newPrice);
		return Spring.getSpring(id);
	}
	
	@PUT
	@Path("/{SpringID}/Update/Full_Price")
	@Produces(MediaType.APPLICATION_JSON) 
	@Consumes(MediaType.APPLICATION_JSON)
	public Spring updateSpringFullPrice(@PathParam("SpringID") int id,Double newPrice) {
		Spring.updateSpringFullPrice(id, newPrice);
		return Spring.getSpring(id);
	}
	
	@PUT
	@Path("/{SpringID}/Update/Queen_Price")
	@Produces(MediaType.APPLICATION_JSON) 
	@Consumes(MediaType.APPLICATION_JSON)
	public Spring updateSpringQueenPrice(@PathParam("SpringID") int id,Double newPrice) {
		Spring.updateSpringQueenPrice(id, newPrice);
		return Spring.getSpring(id);
	}
	
	@PUT
	@Path("/{SpringID}/Update/King_Price")
	@Produces(MediaType.APPLICATION_JSON) 
	@Consumes(MediaType.APPLICATION_JSON)
	public Spring updateSpringKingPrice(@PathParam("SpringID") int id,Double newPrice) {
		Spring.updateSpringTwinPrice(id, newPrice);
		return Spring.getSpring(id);
	}
	
	@PUT
	@Path("/{SpringID}/Update/Cking_Price")
	@Produces(MediaType.APPLICATION_JSON) 
	@Consumes(MediaType.APPLICATION_JSON)
	public Spring updateSpringCkingPrice(@PathParam("SpringID") int id,Double newPrice) {
		Spring.updateSpringTwinPrice(id, newPrice);
		return Spring.getSpring(id);
	}
	
	@DELETE
	@Path("/{SpringID}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteSpring(@PathParam("SpringID") int id) {
		Spring.deleteSpring(id);
	}
}
