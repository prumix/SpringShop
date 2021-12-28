package ru.prumix.springshop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.prumix.springshop.entities.Product;
import ru.prumix.springshop.entities.Role;

import javax.persistence.*;
import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private Long id;
    private String title;
    private int price;

}