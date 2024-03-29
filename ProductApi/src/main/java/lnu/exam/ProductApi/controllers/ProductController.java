package lnu.exam.ProductApi.controllers;

import jakarta.validation.Valid;
import lnu.exam.ProductApi.dtos.ProductRequest;
import lnu.exam.ProductApi.exceptions.ResourceNotFoundException;
import lnu.exam.ProductApi.models.Product;
import lnu.exam.ProductApi.services.ProductService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {
  private final ProductService productService;

  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @GetMapping
  public CollectionModel<EntityModel<Product>> getAll() {
    return productService.getAll();
  }

  @GetMapping("/{id}")
  public EntityModel<Product> getById(@PathVariable(value = "id") Long productId) throws ResourceNotFoundException {
    return productService.getById(productId);
  }

  @PutMapping("/{id}")
  public EntityModel<Product> modify(@PathVariable(value = "id") Long productId, @Valid @RequestBody ProductRequest productRequest) {
    return productService.modify(productId, productRequest.toProduct());
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public EntityModel<Product> create(@Valid @RequestBody ProductRequest productRequest) {
    return productService.create(productRequest.toProduct());
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public ResponseEntity<?> delete(@PathVariable(value = "id") Long productId) {
    return productService.delete(productId);
  }
}
