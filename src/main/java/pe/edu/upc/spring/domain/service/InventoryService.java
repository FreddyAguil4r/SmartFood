package pe.edu.upc.spring.domain.service;



import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pe.edu.upc.spring.domain.model.Inventory;

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

}
