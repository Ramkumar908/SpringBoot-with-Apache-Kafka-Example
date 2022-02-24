package com.ram.web.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class TotalAmount {

	@Id
	@GeneratedValue
	private int id;
	@Column(columnDefinition="Decimal(10,2) default '100.00'")

	private float totalAmount;
	
	private float shippingCharge;
	@Column(columnDefinition="Decimal(10,2) default '100.00'")
	private float orderTotal;
	
	
	public TotalAmount() {
		// TODO Auto-generated constructor stub
	}
	public float getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(float totalAmount) {
		this.totalAmount = totalAmount;
	}
	public float getShippingCharge() {
		return shippingCharge;
	}
	public void setShippingCharge(float shippingCharge) {
		this.shippingCharge = shippingCharge;
	}
	public float getOrderTotal() {
		return orderTotal;
	}
	public void setOrderTotal(float orderTotal) {
		this.orderTotal = orderTotal;
	}
	
	
	
	
}
