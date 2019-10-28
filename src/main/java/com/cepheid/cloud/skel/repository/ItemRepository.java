package com.cepheid.cloud.skel.repository;

import com.cepheid.cloud.skel.model.Item;


public interface ItemRepository extends CustomRepository<Item, Long> {
  
  Item findByMName(String name);

}
