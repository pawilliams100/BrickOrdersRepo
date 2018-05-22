package com.brickorder;

import java.io.Serializable;
import javax.xml.bind.annotation.XmlElement; 
import javax.xml.bind.annotation.XmlRootElement; 
@XmlRootElement(name = "Order")

public class Order implements Serializable {  
	   private static final long serialVersionUID = 1L; 
	   private int id; 
	   private String reference; 
	   private int quantity;
	   private String customercode;
	   private String Type;
	   private int orderFullFilled;
	   
	   public Order(){} 
	    
	   public Order(int id, String reference, int quantity, String customercode, String Type ){  
	      this.id = id; 
	      this.reference = reference; 
	      this.quantity = quantity;
	      this.customercode = customercode;
	      this.Type = Type;
	   }  
	   public int getId() { 
	      return id; 
	   }  

	   public void setId(int id) { 
	      this.id = id; 
	   } 
	   public String getReference() { 
	      return reference; 
	   } 

	   public void setReference(String reference) { 
	      this.reference = reference; 
	   } 
	   public int getQuantity() { 
	      return quantity; 
	   } 

	   public void setQuantity(int quantity) { 
	      this.quantity = quantity; 
	   }   
	
	   public String getCustomerCode() { 
		      return customercode; 
	   } 
	  
	   public void setCustomerCode(String customercode) { 
		   this.customercode = customercode; 
	   }   
	   @XmlElement
	   public String getType() { 
		      return Type; 
	   } 
	   public void setType(String Type) { 
		   this.Type = Type; 
	   }   
	   @XmlElement   
	   public int getOrderFullFilled()
	   {
		   return this.orderFullFilled;
	   } 

	   public void setOrderFullFilled()
	   {
		   this.orderFullFilled = 1;   
	   }
} 
