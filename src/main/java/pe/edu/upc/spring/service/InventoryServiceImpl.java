package pe.edu.upc.spring.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pe.edu.upc.spring.domain.model.Inventory;
import pe.edu.upc.spring.domain.repository.InventoryRepository;
import pe.edu.upc.spring.domain.service.InventoryService;

import javax.persistence.EntityNotFoundException;
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
}
