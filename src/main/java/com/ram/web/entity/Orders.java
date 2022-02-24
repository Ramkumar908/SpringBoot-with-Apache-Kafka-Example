package com.ram.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Orders {

	
	@Id
	private String itemId;
	private String itemType;
	private  String itemName;
	private int itemQuantity;
	@Column(columnDefinition="Decimal(10,2) default '100.00'")

	private float itemPrice;
	@Column(columnDefinition="Decimal(10,2) default '100.00'")
	private float itemDiscount;
	@Column(columnDefinition="Decimal(10,2) default '100.00'")
	private float itemTotalAmount;
	
	
	public Orders() {
		
	}
	public Orders(String itemId, String itemType, String itemName, int itemQuantity, float itemPrice,
		float itemDiscount, float itemTotalAmount) {
		this.itemId = itemId;
		this.itemType = itemType;
		this.itemName = itemName;
		this.itemQuantity = itemQuantity;
		this.itemPrice = itemPrice;
		this.itemDiscount = itemDiscount;
		this.itemTotalAmount = itemTotalAmount;
	}
	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public int getItemQuantity() {
		return itemQuantity;
	}
	public void setItemQuantity(int itemQuantity) {
		this.itemQuantity = itemQuantity;
	}
	public float getItemPrice() {
		return itemPrice;
	}
	public void setItemPrice(float itemPrice) {
		this.itemPrice = itemPrice;
	}
	public float getItemDiscount() {
		return itemDiscount;
	}
	public void setItemDiscount(float itemDiscount) {
		this.itemDiscount = itemDiscount;
	}
	public float getItemTotalAmount() {
		return itemTotalAmount;
	}
	public void setItemTotalAmount(float itemTotalAmount) {
		this.itemTotalAmount = itemTotalAmount;
	}
	@Override
	public String toString() {
		return "Orders [itemId=" + itemId + ", itemType=" + itemType + ", itemName=" + itemName
				+ ", itemQuantity=" + itemQuantity + ", itemPrice=" + itemPrice + ", itemDiscount=" + itemDiscount
				+ ", itemTotalAmount=" + itemTotalAmount + "]";
	}	
	
	
	
	
}
