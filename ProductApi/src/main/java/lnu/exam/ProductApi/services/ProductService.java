package lnu.exam.ProductApi.services;

import java.util.List;
import java.util.Objects;
import lnu.exam.ProductApi.dtos.ProductDTO;
import lnu.exam.ProductApi.exceptions.ResourceNotFoundException;
import lnu.exam.ProductApi.models.Product;
import lnu.exam.ProductApi.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
  @Autowired
  private ProductRepository productRepo;

  public List<Product> getAll() {
    return productRepo.findAll();
  }

  public Product getById(Long id) throws ResourceNotFoundException {
    return productRepo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Product not found for this id: " + id));
  }

  public Product create(Product product) {

    return productRepo.save(product);
  }
}
