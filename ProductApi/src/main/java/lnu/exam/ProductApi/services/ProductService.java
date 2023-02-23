package lnu.exam.ProductApi.services;

import java.util.List;
import java.util.stream.Collectors;
import lnu.exam.ProductApi.components.ProductModelAssembler;
import lnu.exam.ProductApi.controllers.ProductController;
import lnu.exam.ProductApi.exceptions.ResourceNotFoundException;
import lnu.exam.ProductApi.models.Product;
import lnu.exam.ProductApi.repositories.ProductRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class ProductService {
  private final ProductRepository repository;

  private final ProductModelAssembler assembler;

  ProductService(ProductRepository repository, ProductModelAssembler assembler) {
    this.repository = repository;
    this.assembler = assembler;
  }

  public CollectionModel<EntityModel<Product>> getAll() {
    List<EntityModel<Product>> products = repository.findAll().stream()
        .map(assembler::toModel)
        .collect(Collectors.toList());

    return CollectionModel.of(products, linkTo(methodOn(ProductController.class).getAll()).withSelfRel());
  }

  public EntityModel<Product> getById(Long id) throws ResourceNotFoundException {
    Product product = repository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException(id));
    return assembler.toModel(product);
  }

  public EntityModel<Product> create(Product product) {
    Product created = repository.save(product);
    return assembler.toModel(created);
  }
}
