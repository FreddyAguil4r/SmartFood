package com.peru.smartfood.controller;

import com.peru.smartfood.domain.model.Supplier;
import com.peru.smartfood.domain.service.SupplierService;
import com.peru.smartfood.dto.SaveSupplierDto;
import com.peru.smartfood.dto.SupplierDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@AllArgsConstructor
@RequestMapping("/supplier")
@CrossOrigin(origins = "*")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    @Autowired
    private final ModelMapper mapper;

    @PostMapping
    public SupplierDto createSupplier(@Valid @RequestBody SaveSupplierDto resource) {
        Supplier supplier = convertToEntity(resource);
        return convertToResource(supplierService.createSupplier(supplier));
    }

    @GetMapping("/{supplierId}")
    public Supplier getSupplierById(@PathVariable Integer supplierId) {
        return supplierService.getSupplierById(supplierId);
    }

    @PutMapping("/{supplierId}")
    public Supplier updateSupplier(@PathVariable Integer supplierId, @RequestBody Supplier supplierRequest) {
        return supplierService.updateSupplier(supplierId, supplierRequest);
    }


    @DeleteMapping("/{supplierId}")
    public ResponseEntity<?> deleteSupplier(@PathVariable Integer supplierId) {
        return supplierService.deleteSupplier(supplierId);
    }

    @GetMapping("/all")
    public Iterable<Supplier> getAllSuppliers() {
        return supplierService.getAllSuppliers();
    }


    private Supplier convertToEntity(SaveSupplierDto resource) {
        return mapper.map(resource, Supplier.class);
    }
    private SupplierDto convertToResource(Supplier entity)
    {
        return mapper.map(entity, SupplierDto.class);
    }
    
}
