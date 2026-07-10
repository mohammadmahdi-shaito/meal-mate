package com.mealmate.backend.pantry;

import com.mealmate.backend.user.user;
import jakarta.persistence.*;

@Entity
@Table(name = "pantry_items")
public class pantryItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private Double quantity;
    private String unit;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private user user;

    public pantryItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public user getUser() {
        return user;
    }
    
    public void setUser(user user) {
        this.user = user;
    }
}