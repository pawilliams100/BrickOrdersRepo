package com.brickorder;

import java.io.File; 
import java.io.FileInputStream; 
import java.io.FileNotFoundException;  
import java.io.FileOutputStream; 
import java.io.IOException; 
import java.io.ObjectInputStream; 
import java.io.ObjectOutputStream; 
import java.util.ArrayList; 
import java.util.List;  

public class Storage {
	  
	private static String DBFile="Orders.dat";
	private static void saveOrdersDB(List<Order> orderList){
		try {
			File file = new File(DBFile);
			FileOutputStream fs;
			fs = new FileOutputStream(file);

			ObjectOutputStream os = new ObjectOutputStream(fs);		
			os.writeObject(orderList);
			os.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static List<Order> getAllOrdersDB(){
		List<Order> orderList = null;
		try {
			File file = new File(DBFile);
			if (!file.exists()) {
				return orderList;		
			}
			else{
				FileInputStream fs = new FileInputStream(file);
				ObjectInputStream os = new ObjectInputStream(fs);
				orderList = (List<Order>) os.readObject();
				os.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}		
		return orderList;
	}
		  
	public static Order getOrder(String reference){
		List<Order> orders = getAllOrdersDB();

	    for(Order order: orders){
	    	if(order.getReference().compareTo(reference) == 0){
	    		return order;
	    	}
	    }
	    return null;
	}

	public static int addOrder(Order OrderToAdd){
		List<Order> orderList = getAllOrdersDB();
	     
		boolean orderExists = false;
		if (orderList != null ){
			for(Order order: orderList){
				if(order.getReference().compareTo(OrderToAdd.getReference()) == 0){
					orderExists = true;
					break;
				}
			}
		}
	      
		if(!orderExists){
			if ( orderList == null ) {
				orderList = new ArrayList<Order>();
			}
			orderList.add(OrderToAdd);
			saveOrdersDB(orderList);
			return 1;
		}
		return 0;
	}
	   
	public static int updateOrder( String reference, int quantity ) {
		List<Order> orders = getAllOrdersDB();
		int orderExists = 0;
		if (orders != null ){
			for(Order order: orders){
				if(order.getReference().compareTo(reference) == 0){
					if (order.getOrderFullFilled() == 0){
						orderExists = 1;
						order.setQuantity(quantity);
						saveOrdersDB(orders);
					}
					orderExists = 2;
					break;
				}
			}
		}
		return orderExists;
	}
	   
	public static int fulfillOrder( String reference ) {
		List<Order> orders = getAllOrdersDB();
		int orderExists = 0;
		if (orders != null ){
			for(Order order: orders){
				if(order.getReference().compareTo(reference) == 0){
					orderExists = 1;
					order.setOrderFullFilled();
					saveOrdersDB(orders);
					break;
				}
			}
		}
		return orderExists;
	}
}
