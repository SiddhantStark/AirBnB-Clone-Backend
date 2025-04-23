package com.fullstackproject.AirBnB.controller;


import com.fullstackproject.AirBnB.dto.InventoryDto;
import com.fullstackproject.AirBnB.dto.UpdateInventoryRequestDto;
import com.fullstackproject.AirBnB.entity.Inventory;
import com.fullstackproject.AirBnB.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.List;

@RestController
@RequestMapping("/admin/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping("/room/{roomId}")
    public ResponseEntity<List<InventoryDto>> getAllInventoryByRoom(@PathVariable Long roomId) throws AccessDeniedException {
        return ResponseEntity.ok(inventoryService.getAllInventoriesByRoom(roomId));
    }

    @PatchMapping("/room/{roomId}")
    public ResponseEntity<Void> updateInventory(@PathVariable Long roomId, @RequestBody UpdateInventoryRequestDto updateInventoryRequestDto) throws AccessDeniedException {
        inventoryService.updateInventory(roomId, updateInventoryRequestDto);
        return ResponseEntity.noContent().build();
    }
}
