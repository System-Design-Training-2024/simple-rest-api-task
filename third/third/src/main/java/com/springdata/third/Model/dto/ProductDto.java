package com.springdata.third.Model.dto;

import com.springdata.third.Model.Entity.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
    private Integer id;
    private String Name;
    private String description;
    private Double price;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    public static ProductDto fromEntityToDto(Product entity) {
        return ProductDto.builder()
                .id(entity.getId())
                .Name(entity.getName())
                .description(entity.getDescription())
                .price(entity.getPrice())
                .created_at(entity.getCreated_at())
                .updated_at(entity.getUpdated_at())
                .build();
    }
}
