package io.nangrande.grandelattedashboard.service;

import io.nangrande.grandelattedashboard.model.Inventory;
import io.nangrande.grandelattedashboard.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Autowired
    public InventoryService(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public List<Inventory> findAll() {
        return inventoryRepository.findAll();
    }

    public Inventory findById(Long id) {
        Optional<Inventory> inventory = inventoryRepository.findById(id);
        if (inventory.isPresent()) {
            return inventory.get();
        } else {
            // Handle the case where the inventory item is not found
            throw new RuntimeException("Inventory not found for id: " + id);
        }
    }

    public Inventory update(Long id, Inventory inventoryDetails) {
        Inventory inventory = findById(id); // This will throw if the item doesn't exist
        inventory.setProductName(inventoryDetails.getProductName());
        inventory.setQuantityInStock(inventoryDetails.getQuantityInStock());
        inventory.setPurchasePrice(inventoryDetails.getPurchasePrice());
        inventory.setSellingPrice(inventoryDetails.getSellingPrice());
        inventory.setSupplier(inventoryDetails.getSupplier());
        inventory.setExpirationDate(inventoryDetails.getExpirationDate());
        inventory.setLocation(inventoryDetails.getLocation());
        return inventoryRepository.save(inventory);
    }

    public void delete(Long id) {
        Inventory inventory = findById(id); // This will throw if the item doesn't exist
        inventoryRepository.delete(inventory);
    }

    public void save(Inventory inventory) {

    }
}
