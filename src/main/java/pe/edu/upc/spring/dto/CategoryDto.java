package pe.edu.upc.spring.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDto {

    private String name;

    private float totalValuesCategories;

    private InventoryDto inventory;

}
