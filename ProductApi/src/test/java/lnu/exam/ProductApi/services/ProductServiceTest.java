package lnu.exam.ProductApi.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import lnu.exam.ProductApi.components.ProductModelAssembler;
import lnu.exam.ProductApi.exceptions.ResourceNotFoundException;
import lnu.exam.ProductApi.models.Product;
import lnu.exam.ProductApi.repositories.ProductRepository;
import lnu.exam.ProductApi.testUtils.PerformanceTester;

@TestMethodOrder(OrderAnnotation.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductModelAssembler productAssembler;

    @InjectMocks
    private ProductService productService;

    private final Long productId = 1L;
    private Product productOne;
    private Product productTwo;

    private long memoryBefore;
    private long memoryAfter;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        productOne = new Product();
        productOne.setId(productId);
        productOne.setName("Test product");
        productOne.setDescription("Test description");
        productOne.setPrice(100.0);
        productTwo = new Product();
        productTwo.setId(productId);
        productTwo.setName("Updated product");
        productTwo.setDescription("Updated description");
        productTwo.setPrice(200.0);
        memoryBefore = PerformanceTester.getUsedMemory();
    }

    @AfterEach
    public void tearDown() throws InterruptedException {
        memoryAfter = PerformanceTester.getUsedMemory();
        System.out.println("Memory used by test: " +
                (memoryAfter - memoryBefore + " bytes"));
        System.gc();
    }

    @Test
    @Order(1)
    public void testCreate() {
        // Prepare test data
        Product newProduct = productOne;

        // Prepare saved product (usually this would be created by the repository)
        Product savedProduct = productTwo;

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

    @Test
    @Order(2)
    public void testModify() {
        // Prepare test data
        Product currentProduct = productOne;
        Product updatedProduct = productTwo;

        // Create expected result
        EntityModel<Product> expected = EntityModel.of(updatedProduct);

        // Set up mock behavior
        when(productRepository.findById(productId))
                .thenReturn(Optional.of(currentProduct));
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

    @Test
    @Order(3)
    public void testDelete() {
        // Prepare test data
        Product existingProduct = productOne;

        // Set up mock behavior
        when(productRepository.findById(productId))
                .thenReturn(Optional.of(existingProduct));
        doNothing().when(productRepository).delete(existingProduct);

        // Call the service method
        ResponseEntity<?> responseEntity = productService.delete(productId);

        // Verify the result - expect no content
        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

        // Verify mock interactions
        verify(productRepository).findById(productId);
        verify(productRepository).delete(existingProduct);
    }

    @Test
    @Order(4)
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

    @Test
    @Order(5)
    public void testDelete_NotFound() {
        Long wrongProductId = 3L;
        // Set up mock behavior
        when(productRepository.findById(wrongProductId)).thenReturn(Optional.empty());

        // Expect an exception to be thrown
        assertThrows(
                ResourceNotFoundException.class,
                () -> productService.delete(productId));

        // Verify mock interaction
        verify(productRepository).findById(productId);

        // Verify that delete was never called
        verify(productRepository, never()).delete(any(Product.class));
    }
}
