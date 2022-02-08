package com.moyo.moyospringbootrestapi.controller;

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
import org.springframework.web.bind.annotation.RestController;
import com.moyo.moyospringbootrestapi.exception.ResourceNotFoundException;
import com.moyo.moyospringbootrestapi.model.Item;

import com.moyo.moyospringbootrestapi.repositry.ItemRepository;
import com.moyo.moyospringbootrestapi.repositry.OrderRepository;


@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class ItemController {
  @Autowired
  private OrderRepository orderRepository;
  @Autowired
  private ItemRepository itemRepository;
  
  @GetMapping("/items")
	public List<Item> getAllItems(){
		return itemRepository.findAll();
	}
  
  @GetMapping("/orders/{orderId}/items")
  public ResponseEntity<List<Item>> getAllItemsByOrderId(@PathVariable(value = "orderId") Long orderId) {
    if (!orderRepository.existsById(orderId)) {
      throw new ResourceNotFoundException("Not found order with id = " + orderId);
    }
    List<Item> items = itemRepository.findByOrderId(orderId);
    return new ResponseEntity<>(items, HttpStatus.OK);
  }
  @GetMapping("/items/{id}")
  public ResponseEntity<Item> getItemsByOrderId(@PathVariable(value = "id") Long id) {
    Item item = itemRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Not found item with id = " + id));
    return new ResponseEntity<>(item, HttpStatus.OK);
  }
  @PostMapping("/orders/{orderId}/items")
  public ResponseEntity<Item> createItem(@PathVariable(value = "orderId") Long orderId,
      @RequestBody Item itemRequest) {
    Item item = orderRepository.findById(orderId).map(order -> {
      itemRequest.setOrder(order);
      return itemRepository.save(itemRequest);
    }).orElseThrow(() -> new ResourceNotFoundException("Not found Order with id = " + orderId));
    return new ResponseEntity<>(item, HttpStatus.CREATED);
  }
  @PutMapping("/items/{id}")
  public ResponseEntity<Item> updateItem(@PathVariable("id") long id, @RequestBody Item itemRequest) {
    Item item = itemRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("ItemId " + id + "not found"));
    item.setDescription(itemRequest.getDescription());
    return new ResponseEntity<>(itemRepository.save(item), HttpStatus.OK);
  }
  @DeleteMapping("/items/{id}")
  public ResponseEntity<HttpStatus> deleteItem(@PathVariable("id") long id) {
    itemRepository.deleteById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
  
  @DeleteMapping("/orders/{orderId}/items")
  public ResponseEntity<List<Item>> deleteAllItemsOfOrder(@PathVariable(value = "orderId") Long orderId) {
    if (!orderRepository.existsById(orderId)) {
      throw new ResourceNotFoundException("Not found Order with id = " + orderId);
    }
    itemRepository.deleteByOrderId(orderId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
