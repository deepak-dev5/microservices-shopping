package com.deepak.orderservice.dto;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderLineItemsDto {
    private Long id;
    private String skucode;
    private BigDecimal price;
    private Integer quantity;
}
