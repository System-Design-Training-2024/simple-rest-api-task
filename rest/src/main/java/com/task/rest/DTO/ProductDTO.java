package com.task.rest.DTO;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
@NoArgsConstructor(access=AccessLevel.PROTECTED, force=true)
@AllArgsConstructor
@Getter
@Setter
@Data
@ToString
public class ProductDTO {
    public ProductDTO(int id, String name, BigDecimal price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    private Integer id = null;

    private String name;
    private String description;

    BigDecimal price;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}


