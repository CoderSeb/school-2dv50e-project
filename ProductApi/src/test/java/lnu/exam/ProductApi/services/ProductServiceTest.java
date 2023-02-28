package lnu.exam.ProductApi.services;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.LinkRelation;

import lnu.exam.ProductApi.components.ProductModelAssembler;
import lnu.exam.ProductApi.controllers.ProductController;
import lnu.exam.ProductApi.exceptions.ResourceNotFoundException;
import lnu.exam.ProductApi.models.Product;
import lnu.exam.ProductApi.repositories.ProductRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

public class ProductServiceTest {

    @Mock
    private ProductRepository repository;

    @Mock
    private ProductModelAssembler assembler;

    @InjectMocks
    private ProductService service;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAll() throws ResourceNotFoundException {
        Product p1 = new Product();
        p1.setName("Product 1");
        p1.setDescription("Description 1");
        p1.setPrice(10.0);
        
        Product p2 = new Product();
        p2.setName("Product 2");
        p2.setDescription("Description 2");
        p2.setPrice(20.0);
        
        List<Product> products = Arrays.asList(p1, p2);
        EntityModel<Product> e1 = EntityModel.of(p1);
        EntityModel<Product> e2 = EntityModel.of(p2);
        CollectionModel<EntityModel<Product>> expected = CollectionModel.of(Arrays.asList(e1, e2), 
                                            linkTo(methodOn(ProductController.class).getAll()).withSelfRel());
        when(repository.findAll()).thenReturn(products);
        when(assembler.toModel(p1)).thenReturn(e1);
        when(assembler.toModel(p2)).thenReturn(e2);

        CollectionModel<EntityModel<Product>> actual = service.getAll();

        assertEquals(expected.getContent().size(), actual.getContent().size());
        assertEquals(expected.getLink(LinkRelation.of("self")).get().getHref(), 
                     actual.getLink(LinkRelation.of("self")).get().getHref());
    }
}
