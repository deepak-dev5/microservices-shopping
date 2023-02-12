package com.deepak.inventoryservice.service;

import com.deepak.inventoryservice.dto.InventoryResponse;
import com.deepak.inventoryservice.repository.InventoryRepository;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

//    @Transactional(readOnly=true)
//    public boolean isInStock(List<String> skuCode){
//        return inventoryRepository.findBySkuCode(skuCode).isPresent();
//    }

    @Transactional(readOnly=true)
    public List<InventoryResponse> isInStock(List<String> skuCode){
        log.info("inside api inventory method skuCode {}", skuCode);
        List<InventoryResponse> result =  inventoryRepository.findBySkuCodeIn(skuCode).stream()
                .map(inventory ->
                    InventoryResponse.builder()
                            .skuCode(inventory.getSkuCode())
                            .isInStock(inventory.getQuantity()>0)
                            .build()
                ).toList();

        log.info("inside api inventory method result {}", result);

        return  result;
    }
}
