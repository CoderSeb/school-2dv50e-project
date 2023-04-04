package lnu.exam.ProductApi.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.hateoas.EntityModel;

import lnu.exam.ProductApi.components.ProductModelAssembler;
import lnu.exam.ProductApi.models.Product;
import lnu.exam.ProductApi.repositories.ProductRepository;
import lnu.exam.ProductApi.testUtils.PerformanceTester;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductModelAssembler productAssembler;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testModify() {
        long memoryBefore = PerformanceTester.getUsedMemory();
        // Prepare test data
        Long productId = 1L;
        Product currentProduct = new Product();
        currentProduct.setId(productId);
        currentProduct.setName("Test product");
        currentProduct.setDescription("Test description");
        currentProduct.setPrice(100.0);
        Product updatedProduct = new Product();
        updatedProduct.setId(productId);
        updatedProduct.setName("Updated product");
        updatedProduct.setDescription("Updated description");
        updatedProduct.setPrice(200.0);

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

        long memoryAfter = PerformanceTester.getUsedMemory();

        System.out.println("Memory used by testModify: " + (memoryAfter - memoryBefore + " bytes"));
    }
}
