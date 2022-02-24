package com.ram.web.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class OrderDetail {
	
	@Id
	@GeneratedValue
	private int id;
	@OneToMany
	private List<Orders> orders =new ArrayList<>();
	
	@OneToOne(cascade = CascadeType.ALL)
	private TotalAmount totalAmount;
	
	@OneToOne(cascade = CascadeType.ALL)
	private DeliveryAddress address;
	
	public List<Orders> getOrders() {
		return orders;
	}
	public void setOrders(List<Orders> orders) {
		this.orders = orders;
	}
	public TotalAmount getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(TotalAmount totalAmount) {
		this.totalAmount = totalAmount;
	}
	public DeliveryAddress getAddress() {
		return address;
	}
	public void setAddress(DeliveryAddress address) {
		this.address = address;
	}
	
	

}
