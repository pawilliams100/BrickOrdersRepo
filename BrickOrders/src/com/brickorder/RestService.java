package com.brickorder;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("/RestService") 

public class RestService {
			
	@POST
	@Path("/Order")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String createOrder(@FormParam("id") int id,
	      @FormParam("reference") String reference,
	      @FormParam("quantity") int quantity,
	      @FormParam("customercode") String customercode,
	      @Context HttpServletResponse servletResponse, 
	      @Context HttpServletRequest response) throws IOException{

		String session = response.getSession(false).getId();
		if (session != null ){
	    	String uniquereference = session + "-" + customercode + "-" + System.currentTimeMillis();
	    	Order order = new Order(id, uniquereference, quantity, customercode, "Bricks");
	    	int result = Storage.addOrder(order);
	    	if(result == 1){
	    		return "<result>" + order.getReference()+ "</result>";
	    	}
	    }
	    return "<result>failure</result>";
	}
	
	@GET
	@Path("/GetOrder/{reference}")
	@Produces(MediaType.APPLICATION_XML)
	public Order getOrder(
			@PathParam("reference") String reference)  throws IOException{
			Order order = Storage.getOrder(reference);
		if ( order == null ) {
	    	  order = new Order();
		}
		return order;
	}

	@GET
	@Path("/GetOrders")
	@Produces(MediaType.APPLICATION_XML)
	public List<Order> getOrders()  throws IOException{
	      List<Order> orderList = Storage.getAllOrdersDB();
	      return orderList;
	}
	
	@PUT
	@Path("/UpdateOrder")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String UpdateOrder(
	    @FormParam("reference") String reference, 
	    @FormParam("quantity") int quantity,
	    @Context HttpServletResponse servletResponse) throws IOException{
		int updated = Storage.updateOrder( reference, quantity);
		if ( updated == 0 ) {
			servletResponse.sendError(404);
		}
		return "<result>" + reference + " " + updated + "</result>";
	 }

	@PUT
	@Path("/FullfillOrder")
	@Produces(MediaType.APPLICATION_XML)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	public String FullFillOver(
	    @FormParam("reference") String reference, 
	    @Context HttpServletResponse servletResponse) throws IOException{
		int updated = Storage.fulfillOrder(reference);
		if ( updated == 0 ) {
			servletResponse.sendError(404);
		}
		return "<result>updated</result>";
	 }
}
