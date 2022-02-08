package com.moyo.moyospringbootrestapi.model;
import javax.persistence.*;


@Entity
@Table(name = "orders")
public class Order {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_generator")
  private long id;
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orderNumber_generator")
  private long orderNumber;
  @Column(name = "customerName")
  private String customerName;
  @Column(name = "orderDescription")
  private String orderDescription;
  
  
  public Order() {
  }
  public Order(Long orderNumber, String customerName, String orderDescription) {
    this.orderNumber = orderNumber;
    this.customerName = customerName;
    this.orderDescription = orderDescription;
  }
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(long orderNumber) {
		this.orderNumber = orderNumber;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getOrderDescription() {
		return orderDescription;
	}
	public void setOrderDescription(String orderDescription) {
		this.orderDescription = orderDescription;
	}
  
  

  
}
