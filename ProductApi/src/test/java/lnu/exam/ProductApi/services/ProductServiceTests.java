package lnu.exam.ProductApi.services;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;

import java.util.Optional;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.hateoas.EntityModel;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import lnu.exam.ProductApi.components.ProductModelAssembler;
import lnu.exam.ProductApi.models.Product;
import lnu.exam.ProductApi.repositories.ProductRepository;
import lnu.exam.testUtils.PerformanceTests;

public class ProductServiceTests {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductModelAssembler productAssembler;

    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test(description = "Basic test to check TestNG")
    public void verifyTrueIsTrue() {
        Assert.assertTrue(true);
    }

    @Test
    public void testModify() {
        long memoryBefore = PerformanceTests.getMemoryUsage();
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

        long memoryAfter = PerformanceTests.getMemoryUsage();
        System.out.println("Memory usage for testModify(): " + (memoryAfter - memoryBefore) + " bytes");
    }
}
