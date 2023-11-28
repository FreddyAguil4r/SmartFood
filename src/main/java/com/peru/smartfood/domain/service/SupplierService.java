package com.peru.smartfood.domain.service;

import com.peru.smartfood.domain.model.Supplier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface SupplierService {

    Supplier createSupplier(Supplier supplier);
    //read

    Supplier getSupplierById(Integer supplierId);

    //update

    Supplier updateSupplier(Integer supplierId, Supplier supplierRequest);

    //delete
    ResponseEntity<?> deleteSupplier(Integer supplierId);

    List<Supplier> getAllSuppliers();

}
