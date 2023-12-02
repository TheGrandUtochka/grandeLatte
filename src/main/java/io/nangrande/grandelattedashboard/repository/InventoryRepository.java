package io.nangrande.grandelattedashboard.repository;

import io.nangrande.grandelattedashboard.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
