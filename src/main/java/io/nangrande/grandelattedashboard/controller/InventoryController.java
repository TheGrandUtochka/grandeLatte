package io.nangrande.grandelattedashboard.controller;
import io.nangrande.grandelattedashboard.model.Employee;
import io.nangrande.grandelattedashboard.model.Inventory;
import io.nangrande.grandelattedashboard.service.InventoryService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import io.nangrande.grandelattedashboard.shared.GenericResponse;

@RestController
@RequestMapping("api/v1/inventory")
public class InventoryController {
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping
    public GenericResponse registerInventory(@Valid @RequestBody Inventory inventory) {
        inventoryService.save(inventory);
        return new GenericResponse("Позиция добавлена");
    }
}
