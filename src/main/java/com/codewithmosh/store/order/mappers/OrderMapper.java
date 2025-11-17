package com.codewithmosh.store.order.mappers;

import com.codewithmosh.store.order.dtos.OrderDto;
import com.codewithmosh.store.order.entities.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toDto(Order order);
}
