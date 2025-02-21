package com.arcis.vgil.activity;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root
public class ProductItems {

   @Element
   private String productcode;
   @Element
   private String productname;
   @Element
   private String quantity;
  

  ProductItems(String productcode, String productname, String quantity){
	  this.productcode=productcode;
	  this.productname=productname;
	  this.quantity=quantity;
   }
   ProductItems(){
	   
   }
   public String getproductcode() {
      return productcode;
   }

   public String getproductname() {
      return productname;
   }
   public String getquantity() {
	      return quantity;
	   }
   
}
