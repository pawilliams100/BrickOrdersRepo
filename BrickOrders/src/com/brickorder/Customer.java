package com.brickorder;

import java.io.Serializable;  
import javax.xml.bind.annotation.XmlElement; 
import javax.xml.bind.annotation.XmlRootElement; 
@XmlRootElement(name = "customer") 

public class Customer implements Serializable {  
	   private static final long serialVersionUID = 1L; 
	   private int id; 
	   private String name; 
	   private String address;
	   private String code;
	   private int active;
	   
	   public Customer(){} 
	    
	   public Customer(int id, String address, String code, String name, int active){  
	      this.id = id; 
	      this.name = name; 
	      this.address = address;
	      this.code = code;
	      this.active = active;
	   }  
	   public int getId() { 
	      return id; 
	   }  
	   @XmlElement 
	   public void setId(int id) { 
	      this.id = id; 
	   } 
	   public String getName() { 
	      return name; 
	   } 
	   @XmlElement
	   public void setName(String name) { 
	      this.name = name; 
	   } 
	   public String getAddress() { 
	      return address; 
	   } 
	   @XmlElement 
	   public void setAddress(String address) { 
	      this.address = address; 
	   }   
	   public String getCode() { 
		      return code; 
	   } 
	   @XmlElement 
	   public void setCode(String code) { 
		   this.code = code; 
	   }   
	   public int getActive() { 
		      return active; 
	   } 
	   @XmlElement 
	   public void setActive(int active) { 
		   this.active = active; 
	   }   
} 
