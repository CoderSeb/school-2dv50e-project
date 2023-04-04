package lnu.exam.ProductApi.services

import lnu.exam.ProductApi.components.ProductModelAssembler
import lnu.exam.ProductApi.models.Product
import lnu.exam.ProductApi.repositories.ProductRepository
import lnu.exam.testUtils.PerformanceTests
import org.springframework.hateoas.EntityModel
import spock.lang.Specification
import spock.lang.Subject

class ProductServiceTest extends Specification {

    ProductRepository productRepository = Mock()
    ProductModelAssembler productModelAssembler = new ProductModelAssembler()

    @Subject
    ProductService productService = new ProductService(productRepository, productModelAssembler)

    def "Modify"() {
        long startMemory = PerformanceTests.getMemoryUsage()
        given:
        Long productId = 1L
        Product currentProduct = new Product()
        currentProduct.setId(productId)
        currentProduct.setName("Current Name")
        currentProduct.setDescription("Current Description")
        currentProduct.setPrice(100.0)
        Product updatedProduct = currentProduct
        updatedProduct.setId(productId)
        updatedProduct.setName("Updated Name")
        updatedProduct.setDescription("Updated Description")
        updatedProduct.setPrice(200.0)

        EntityModel<Product> expected = EntityModel.of(updatedProduct)

        productRepository.findById(productId) >> Optional.of(currentProduct)

        when:
        EntityModel<Product> actual = productService.modify(productId, updatedProduct)

        then:
        actual.getContent() == expected.getContent()

        long endMemory = PerformanceTests.getMemoryUsage()

        println("Memory usage modify test: " + (endMemory - startMemory) + " bytes")
    }
}
