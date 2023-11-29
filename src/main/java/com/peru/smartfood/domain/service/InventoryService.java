package com.peru.smartfood.domain.service;


import com.peru.smartfood.domain.model.Inventory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InventoryService {

    Inventory createInventory(Inventory inventory);
    //read

    Inventory getInventoryById(Integer inventoryId);

    //update

    Inventory updateInventory(Integer inventoryId, Inventory inventoryRequest);

    //delete
    ResponseEntity<?> deleteInventory(Integer inventoryId);

    List<Inventory> getAllInventories();

    Inventory getLatestInventory();



}
