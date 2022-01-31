package com.prumi.web.core.dto;

import com.prumi.web.api.dto.OrderItemDto;
import lombok.Data;

import java.util.List;

@Data
public class OrderDto {
    private Long id;
    private String username;
    private List<OrderItemDto> items;
    private Integer totalPrice;
    private String address;
    private String phone;
}
