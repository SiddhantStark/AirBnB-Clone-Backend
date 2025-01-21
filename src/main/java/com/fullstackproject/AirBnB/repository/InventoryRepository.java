package com.fullstackproject.AirBnB.repository;

import com.fullstackproject.AirBnB.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

}
