package io.nangrande.grandelattedashboard.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Inventory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 256)
    private String productName;

    @NotNull
    @Min(0)
    private Integer quantityInStock;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal purchasePrice;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal sellingPrice;

    @NotNull
    @Size(max = 256)
    private String supplier;

    @NotNull
    private LocalDateTime expirationDate;

    @NotNull
    @Size(max = 256)
    private String location;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Version
    private Integer version;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Inventory inventory = (Inventory) o;
        return Objects.equals(id, inventory.id) && Objects.equals(productName, inventory.productName) && Objects.equals(quantityInStock, inventory.quantityInStock) && Objects.equals(purchasePrice, inventory.purchasePrice) && Objects.equals(sellingPrice, inventory.sellingPrice) && Objects.equals(supplier, inventory.supplier) && Objects.equals(expirationDate, inventory.expirationDate) && Objects.equals(location, inventory.location) && Objects.equals(createdAt, inventory.createdAt) && Objects.equals(updatedAt, inventory.updatedAt) && Objects.equals(version, inventory.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productName, quantityInStock, purchasePrice, sellingPrice, supplier, expirationDate, location, createdAt, updatedAt, version);
    }
}
