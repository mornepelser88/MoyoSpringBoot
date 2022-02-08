package com.moyo.moyospringbootrestapi.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moyo.moyospringbootrestapi.exception.ResourceNotFoundException;
import com.moyo.moyospringbootrestapi.model.Order;
import com.moyo.moyospringbootrestapi.repositry.OrderRepository;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class OrderController {
  @Autowired
  OrderRepository orderRepository;
  
  @GetMapping("/orders")
  public ResponseEntity<List<Order>> getAllOrders(@RequestParam(required = false) String title) {
    List<Order> orders = new ArrayList<Order>();
    if (title == null)
      orderRepository.findAll().forEach(orders::add);
    else
      orderRepository.findByCustomerNameContaining(title).forEach(orders::add);
    if (orders.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    return new ResponseEntity<>(orders, HttpStatus.OK);
  }
  @GetMapping("/orders/{id}")
  public ResponseEntity<Order> getOrderById(@PathVariable("id") long id) {
	  Order order = orderRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found orders with id = " + id));
    return new ResponseEntity<>(order, HttpStatus.OK);
  }
  @PostMapping("/orders")
  public ResponseEntity<Order> createOrder(@RequestBody Order order) {
	  Order _order = orderRepository.save(new Order(order.getOrderNumber(), order.getOrderDescription(), order.getCustomerName()));
    return new ResponseEntity<>(_order, HttpStatus.CREATED);
  }
  @PutMapping("/orders/{id}")
  public ResponseEntity<Order> updateOrder(@PathVariable("id") long id, @RequestBody Order order) {
	  Order _order = orderRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found Order with id = " + id));
    _order.setCustomerName(order.getCustomerName());
    _order.setOrderDescription(order.getOrderDescription());
    _order.setOrderNumber(order.getOrderNumber());
    
    return new ResponseEntity<>(orderRepository.save(_order), HttpStatus.OK);
  }
  @DeleteMapping("/orders/{id}")
  public ResponseEntity<HttpStatus> deleteOrder(@PathVariable("id") long id) {
	  orderRepository.deleteById(id);
    
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
  @DeleteMapping("/orders")
  public ResponseEntity<HttpStatus> deleteAllOrders() {
	  orderRepository.deleteAll();
    
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
//  @GetMapping("/orders/published")
//  public ResponseEntity<List<Order>> findByPublished() {
//    List<Order> tutorials = orderRepository.findByPublished(true);
//    if (tutorials.isEmpty()) {
//      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//    
//    return new ResponseEntity<>(tutorials, HttpStatus.OK);
//  }
}
