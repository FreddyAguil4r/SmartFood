package pe.edu.upc.spring.domain.service;


import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pe.edu.upc.spring.domain.model.Supplier;

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
