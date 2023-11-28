package com.peru.smartfood.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class CategoriesAndProductsDto {

    private String name;

    private float totalValuesCategories;

    private List<ShortProductDto> products;

}
