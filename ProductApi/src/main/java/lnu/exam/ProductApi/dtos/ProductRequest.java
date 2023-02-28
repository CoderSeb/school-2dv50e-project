package lnu.exam.ProductApi.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lnu.exam.ProductApi.models.Product;
import lombok.Data;

@Data
public class ProductRequest {
    @NotBlank(message = "Name cannot be blank")
    @NotNull(message = "Name is NULL")
    @Size(min = 3, max = 50, message = "Name is to be of 3 - 50 characters")
    private String name;

    @NotBlank(message = "Description cannot be blank")
    @NotNull(message = "Description is NULL")
    @Size(min = 5, max = 300, message = "Description is to be of 5 - 300 characters")
    private String description;

    @NotNull(message = "Price is NULL")
    private double price;

    public Product toProduct() {
        Product newProduct = new Product();
        newProduct.setName(name);
        newProduct.setDescription(description);
        newProduct.setPrice(price);
        return newProduct;
    }
}
