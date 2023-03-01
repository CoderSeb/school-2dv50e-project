package lnu.exam.ProductApi.services

import lnu.exam.ProductApi.components.ProductModelAssembler
import lnu.exam.ProductApi.dtos.ProductRequest
import lnu.exam.ProductApi.models.Product
import lnu.exam.ProductApi.repositories.ProductRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.hateoas.EntityModel
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

@DataJpaTest
class ProductServiceSpec extends Specification {

    @Autowired
    ProductRepository repository = Mock()

    ProductModelAssembler assembler = new ProductModelAssembler()

    @Subject
    ProductService productService = new ProductService(repository, assembler)
    
    def "create method should save product and return entity model"() {
        given: "a product with name of 'test product'"
        ProductRequest productRequest = new ProductRequest(name: "test product", description: "test product desc", price: 10.0)
        Product product = productRequest.toProduct()
        EntityModel<Product> expected = assembler.toModel(product)

        when: "added with create() method"
        EntityModel<Product> result = productService.create(product)

        then: "result.getContent().name should be 'test product'"
        result.getContent().name == expected.getContent().name
    }
}
