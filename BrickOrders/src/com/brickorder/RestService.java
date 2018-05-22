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

		String session = null;
		try {
			session = response.getSession(true).getId();
		}
		catch (Exception e)
		{
			return "<result>session does not exist</result>";
		}

		if (session != null ){
			try {
				if(customercode.equals(null) || customercode.equals("")){
					return "<result>customer number must be provided</result>";
				}
			}
			catch (Exception e)
			{
				return "<result>customer number must be provided</result>";
			}

			try {
				if (quantity == 0) {
					return "<result>A quantity of bricks has not been specified</result>";
				}
			}
			catch (Exception e)
			{
				return "<result>A quantity of bricks has not been specified</result>";
			}

	    	String uniquereference = session + "-" + customercode + "-" + System.currentTimeMillis();
    		Order order = new Order(id, uniquereference, quantity, customercode, "Bricks");
    		int result = Storage.addOrder(order);
    		if(result == 1){
    			return "<result>" + order.getReference()+ "</result>";
    		}else {
    				return "<result>An error occurred whilst trying to save the order </result>";
    		}
	    }
	    return "<result>failure</result>";
	}
	
	@GET
	@Path("/GetOrder/{reference}")
	@Produces(MediaType.APPLICATION_XML)
	public Order getOrder(
			@PathParam("reference") String reference)  throws IOException{
		Order order = new Order();
		try {
			if (reference.equals("")){
				return order;
			}
		}
		catch ( Exception e) {
			return order;
		}
		
		order = Storage.getOrder(reference);
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
		
		try {
			if(reference.equals(null) || reference.equals("")){
				return "<result>reference must be provided</result>";
			}
		}
		catch (Exception e)
		{
			return "<result>reference must be provided</result>";
		}

		try {
			if (quantity == 0) {
				return "<result>A quantity of bricks has not been specified</result>";
			}
		}
		catch (Exception e)
		{
			return "<result>A quantity of bricks has not been specified</result>";
		}
		
		int updated = Storage.updateOrder( reference, quantity);
		if ( updated == 0 ) {
			return "Unable to update";
		}else if ( updated == 2 ) { 
			servletResponse.sendError(404);
			return "";
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
		try {
			if(reference.equals(null) || reference.equals("")){
				return "<result>reference must be provided</result>";
			}
		}
		catch (Exception e)
		{
			return "<result>reference must be provided</result>";
		}

		int updated = Storage.fulfillOrder(reference);
		if ( updated == 0 ) {
			servletResponse.sendError(404);
		}
		return "<result>updated</result>";
	 }
}
