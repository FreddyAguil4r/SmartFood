package pe.edu.upc.spring.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pe.edu.upc.spring.domain.model.Supplier;
import pe.edu.upc.spring.domain.repository.SupplierRepository;
import pe.edu.upc.spring.domain.service.SupplierService;

import java.util.List;

@Service
public class SupplierServiceImp implements SupplierService {

    @Autowired
    private SupplierRepository supplierRepository;

    @Override
    public Supplier createSupplier(Supplier supplier) {

        return supplierRepository.save(supplier);
    }

    @Override
    public Supplier getSupplierById(Integer supplierId) {
        return supplierRepository.findById(supplierId).orElseThrow();
    }

    @Override
    public Supplier updateSupplier(Integer supplierId, Supplier supplierRequest) {

        return supplierRepository.findById(supplierId).map(supplier -> {
            supplier.setName(supplierRequest.getName());
            supplier.setRuc(supplierRequest.getRuc());
            supplier.setAddress(supplierRequest.getAddress());
            return supplierRepository.save(supplier);
        }).orElseThrow();

    }

    @Override
    public ResponseEntity<?> deleteSupplier(Integer supplierId) {

        Supplier supplier = supplierRepository.findById(supplierId).orElseThrow();
        supplierRepository.delete(supplier);
        return ResponseEntity.ok().build();
    }

    @Override
    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }
}
