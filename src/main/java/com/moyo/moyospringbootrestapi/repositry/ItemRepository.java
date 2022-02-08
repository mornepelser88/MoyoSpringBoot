package com.moyo.moyospringbootrestapi.repositry;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.moyo.moyospringbootrestapi.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
  List<Item> findByOrderId(Long postId);
  
  @Transactional
  void deleteByOrderId(long orderId);
}
