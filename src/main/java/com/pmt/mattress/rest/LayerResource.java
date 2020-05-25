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

import com.pmt.mattress.Layer;

@Path("Layers")
public class LayerResource {
	
	@GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Layer> getAllLayers() {
    	return Layer.getAllLayers();
    }
	
	@GET
	@Path("/{layerId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Layer getLayer(@PathParam("layerId") int id) {
		return Layer.getLayer(id);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON) 
	@Consumes(MediaType.APPLICATION_JSON)
	public Layer createLayer(Layer l) {
		Layer.createLayer(l);
		return Layer.getLayer(Layer.getLayerID(l.getName())); 
	}
	
	@DELETE
	@Path("/{layerId}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteLayer(@PathParam("layerId") int id) {
		Layer.deleteLayer(id);
	}
	
	@PUT
	@Path("/{layerId}")
	@Produces(MediaType.APPLICATION_JSON) 
	@Consumes(MediaType.APPLICATION_JSON)
	public Layer updateName(@PathParam("layerId") int id,String newName) {
		Layer.updateName(id, newName);
		return Layer.getLayer(Layer.getLayerID(newName));
	}
}


