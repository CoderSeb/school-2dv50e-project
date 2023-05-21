package lnu.exam.ProductApi.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;

import java.util.Optional;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import lnu.exam.ProductApi.components.ProductModelAssembler;
import lnu.exam.ProductApi.exceptions.ResourceNotFoundException;
import lnu.exam.ProductApi.models.Product;
import lnu.exam.ProductApi.repositories.ProductRepository;
import lnu.exam.testUtils.PerformanceTests;

public class ProductServiceTests {

    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductModelAssembler productAssembler;

    private long memoryBefore;
    private long memoryAfter;

    private Product productOne;
    private Product productTwo;
    private Long productId;

    @BeforeMethod
    public void init() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductService(productRepository, productAssembler);
        productOne = new Product();
        productOne.setId(productId);
        productOne.setName("New product");
        productOne.setDescription("New description");
        productOne.setPrice(100.0);

        productTwo = new Product();
        productTwo.setId(productId);
        productTwo.setName("Updated product");
        productTwo.setDescription("Updated description");
        productTwo.setPrice(200.0);

        memoryBefore = PerformanceTests.getMemoryUsage();
    }

    @Test(priority = 3)
    public void testCreate() {
        // Prepare test data
        Product newProduct = new Product();
        newProduct.setName(productOne.getName());
        newProduct.setDescription(productOne.getDescription());
        newProduct.setPrice(productOne.getPrice());

        // Prepare saved product (usually this would be created by the repository)
        Product savedProduct = productOne;

        // Create expected result
        EntityModel<Product> expected = EntityModel.of(savedProduct);

        // Set up mock behavior
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
            Product productToSave = invocation.getArgument(0);
            // Set ID to simulate DB generated ID
            productToSave.setId(savedProduct.getId());
            return productToSave;
        });
        when(productAssembler.toModel(any(Product.class))).thenReturn(expected);

        // Call the service method
        EntityModel<Product> actual = productService.create(newProduct);
        // Verify the result
        assertEquals(expected, actual);

        // Verify mock interactions
        verify(productRepository).save(any(Product.class));
        verify(productAssembler).toModel(any(Product.class));
    }

    @Test(priority = 4)
    public void testModify() {
        // Prepare test data
        Long productId = 1L;
        Product currentProduct = productOne;
        Product updatedProduct = productTwo;

        // Create expected result
        EntityModel<Product> expected = EntityModel.of(updatedProduct);

        // Set up mock behavior
        when(productRepository.findById(productId)).thenReturn(Optional.of(currentProduct));
        when(productRepository.save(any(Product.class))).thenAnswer(invocation -> {
            Product productToSave = invocation.getArgument(0);
            productToSave.setId(productId);
            return productToSave;
        });
        when(productAssembler.toModel(any(Product.class))).thenReturn(expected);

        // Call the service method
        EntityModel<Product> actual = productService.modify(productId, updatedProduct);

        // Verify the result
        assertEquals(actual, expected);

        // Verify mock interactions
        verify(productRepository).findById(productId);
        verify(productRepository).save(any(Product.class));
        verify(productAssembler).toModel(any(Product.class));
    }

    @Test(priority = 5)
    public void testDelete() {
        // Prepare test data
        Product existingProduct = productOne;

        // Set up mock behavior
        when(productRepository.findById(productId)).thenReturn(Optional.of(existingProduct));
        doNothing().when(productRepository).delete(existingProduct);

        // Call the service method
        ResponseEntity<?> responseEntity = productService.delete(productId);

        // Verify the result - expect no content
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

        // Verify mock interactions
        verify(productRepository).findById(productId);
        verify(productRepository).delete(existingProduct);
    }

    @Test(priority = 6)
    public void testModify_NotFound() {
        Long wrongProductId = 3L;
        // Set up mock behavior
        when(productRepository.findById(wrongProductId)).thenReturn(Optional.empty());

        // Prepare data
        Product updatedProduct = productTwo;

        // Expect an exception to be thrown
        assertThrows(
                ResourceNotFoundException.class,
                () -> productService.modify(wrongProductId, updatedProduct));

        // Verify mock interaction
        verify(productRepository).findById(wrongProductId);

        // Verify that save was never called
        verify(productRepository, never()).save(any(Product.class));
        verify(productAssembler, never()).toModel(any(Product.class));
    }

    @Test(priority = 7)
    public void testDelete_NotFound() {
        Long wrongProductId = 3L;
        // Set up mock behavior
        when(productRepository.findById(wrongProductId)).thenReturn(Optional.empty());

        // Expect an exception to be thrown
        Assert.assertThrows(
                ResourceNotFoundException.class,
                () -> productService.delete(wrongProductId));

        // Verify mock interaction
        verify(productRepository).findById(wrongProductId);

        // Verify that delete was never called
        verify(productRepository, never()).delete(any(Product.class));
    }

    @AfterMethod
    public void tearDown() {
        memoryAfter = PerformanceTests.getMemoryUsage();
        System.out.println("Memory usage: " + (memoryAfter - memoryBefore) + " bytes");
    }
}
