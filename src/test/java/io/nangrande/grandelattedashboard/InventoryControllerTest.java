package io.nangrande.grandelattedashboard;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.nangrande.grandelattedashboard.controller.InventoryController;
import io.nangrande.grandelattedashboard.model.Inventory;
import io.nangrande.grandelattedashboard.service.InventoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
public class InventoryControllerTest {

    private MockMvc mockMvc;

    @Mock
    private InventoryService inventoryService;

    @InjectMocks
    private InventoryController inventoryController;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Inventory inventory;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(inventoryController).build();
        inventory = new Inventory();
        inventory.setId(1L);
        inventory.setProductName("Coffee Beans");
        inventory.setQuantityInStock(100);
        inventory.setPurchasePrice(new BigDecimal("10.00"));
        inventory.setSellingPrice(new BigDecimal("15.00"));
        inventory.setSupplier("Best Beans Co.");
        inventory.setExpirationDate(LocalDateTime.now().plusDays(30));
        inventory.setLocation("Aisle 3");
    }

    @Test
    void testGetAllInventory() throws Exception {
        List<Inventory> inventories = Arrays.asList(inventory);
        given(inventoryService.findAll()).willReturn(inventories);

        mockMvc.perform(get("/inventory"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].productName").value(inventory.getProductName()));
    }

    @Test
    void testGetInventoryById() throws Exception {
        given(inventoryService.findById(inventory.getId())).willReturn(inventory);

        mockMvc.perform(get("/inventory/{id}", inventory.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productName").value(inventory.getProductName()));
    }

    @Test
    void testCreateInventory() throws Exception {
        given(inventoryService.save(inventory)).willReturn(inventory);

        mockMvc.perform(post("/inventory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inventory)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productName").value(inventory.getProductName()));
    }

    @Test
    void testUpdateInventory() throws Exception {
        given(inventoryService.update(inventory.getId(), inventory)).willReturn(inventory);

        mockMvc.perform(put("/inventory/{id}", inventory.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inventory)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.productName").value(inventory.getProductName()));
    }

    @Test
    void testDeleteInventory() throws Exception {
        doNothing().when(inventoryService).delete(inventory.getId());

        mockMvc.perform(delete("/inventory/{id}", inventory.getId()))
                .andExpect(status().isNoContent());
    }
}
