package com.peru.smartfood.controller;


import com.peru.smartfood.domain.model.Inventory;
import com.peru.smartfood.domain.service.InventoryService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/inventory")
@CrossOrigin(origins = "*")
public class InventaryController {

    @Autowired
    private InventoryService inventoryService;

    @GetMapping()
    public Inventory getLatestInventory() {
        return inventoryService.getLatestInventory();
    }

}
