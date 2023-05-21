package lnu.exam.ProductApi.services

import lnu.exam.ProductApi.components.ProductModelAssembler
import lnu.exam.ProductApi.exceptions.ResourceNotFoundException
import lnu.exam.ProductApi.models.Product
import lnu.exam.ProductApi.repositories.ProductRepository
import org.springframework.hateoas.EntityModel
import org.springframework.http.HttpStatus
import spock.lang.Shared
import spock.lang.Specification
import lnu.exam.testUtils.PerformanceTests

import java.util.Optional

class ProductServiceSpec extends Specification {

    @Shared ProductService productService

    @Shared ProductRepository productRepository

    @Shared ProductModelAssembler productAssembler

    @Shared Long productId = 1L

    @Shared Product productOne

    @Shared Product productTwo

    @Shared Long memoryBefore;
    @Shared Long memoryAfter;

    def setup() {
        productRepository = Mock(ProductRepository)
        productAssembler = Mock(ProductModelAssembler)

        productService = new ProductService(productRepository, productAssembler)

        productOne = new Product()
        productOne.setId(productId)
        productOne.setName("New product")
        productOne.setDescription("New description")
        productOne.setPrice(100.0)

        productTwo = new Product()
        productTwo.setId(productId)
        productTwo.setName("Updated product")
        productTwo.setDescription("Updated description")
        productTwo.setPrice(200.0)
        memoryBefore = PerformanceTests.getMemoryUsage()
    }

    def "testCreate()"() {
        given:
        EntityModel<Product> expected = EntityModel.of(productOne)

        when:
        productService.create(productOne)

        then:
        1 * productRepository.save(_) >> productOne
        1 * productAssembler.toModel(_) >> expected
    }

    def "testModify()"() {
        given:
        EntityModel<Product> expected = EntityModel.of(productTwo)

        when:
        productService.modify(productId, productTwo)

        then:
        1 * productRepository.findById(productId) >> Optional.of(productOne)
        1 * productRepository.save(_) >> productTwo
        1 * productAssembler.toModel(_) >> expected
    }

    def "testDelete()"() {
        when:
        def responseEntity = productService.delete(productId)

        then:
        1 * productRepository.findById(productId) >> Optional.of(productOne)
        1 * productRepository.delete(_)
        responseEntity.getStatusCode() == HttpStatus.NO_CONTENT
    }

    def "testModify()_NotFound"() {
        given:
        Long wrongProductId = 3L

        when:
        productService.modify(wrongProductId, productTwo)

        then:
        1 * productRepository.findById(wrongProductId) >> Optional.empty()
        thrown(ResourceNotFoundException)
    }

    def "testDelete()_NotFound"() {
        given:
        Long wrongProductId = 3L

        when:
        productService.delete(wrongProductId)

        then:
        1 * productRepository.findById(wrongProductId) >> Optional.empty()
        thrown(ResourceNotFoundException)
    }

    def cleanup() throws MissingMethodException {
        memoryAfter = PerformanceTests.getMemoryUsage()
        println "Memory used: " + (memoryAfter - memoryBefore) + " bytes"
    }
}
