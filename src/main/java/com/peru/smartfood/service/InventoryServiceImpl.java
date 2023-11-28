package com.peru.smartfood.service;

import com.peru.smartfood.domain.model.Inventory;
import com.peru.smartfood.domain.repository.InventoryRepository;
import com.peru.smartfood.domain.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


import javax.persistence.EntityNotFoundException;

import java.util.Date;
import java.util.List;
@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public Inventory createInventory(Inventory inventory) {
        return inventoryRepository.save(inventory);
    }

    @Override
    public Inventory getInventoryById(Integer inventoryId) {
        return null;
    }

    @Override
    public Inventory updateInventory(Integer inventoryId, Inventory inventoryRequest) {
        return inventoryRepository.findById(inventoryId).map(inventory -> {
            inventory.setCurrentSystem(inventoryRequest.getCurrentSystem());
            inventory.setTotalInventory(inventoryRequest.getTotalInventory());
            return inventoryRepository.save(inventory);
        }).orElseThrow(() -> new EntityNotFoundException("Inventory not found"));
    }

    @Override
    public ResponseEntity<?> deleteInventory(Integer inventoryId) {
        return null;
    }

    @Override
    public List<Inventory> getAllInventories() {

        return inventoryRepository.findAll();


    }
    @Override
    public Inventory getLatestInventory() {
        List<Inventory> inventories = inventoryRepository.findAll();
        if (!inventories.isEmpty()) {
            // Devuelve el último inventario registrado (asumiendo que están ordenados por ID)
            return inventories.get(inventories.size() - 1);
        }
        return null;
    }
}

