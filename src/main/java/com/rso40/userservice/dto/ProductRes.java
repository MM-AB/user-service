package com.rso40.userservice.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRes {

    private String id;
    private String name;
    private String description;
    private String restaurant;
    private String address;
    private BigDecimal price;

}
