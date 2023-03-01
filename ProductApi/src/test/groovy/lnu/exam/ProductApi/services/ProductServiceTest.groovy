package lnu.exam.ProductApi.services

import spock.lang.*
import spock.mock.MockitoExt

import java.util.Arrays

import lnu.exam.ProductApi.components.ProductModelAssembler
import lnu.exam.ProductApi.controllers.ProductController
import lnu.exam.ProductApi.exceptions.ResourceNotFoundException
import lnu.exam.ProductApi.models.Product
import lnu.exam.ProductApi.repositories.ProductRepository

import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.LinkRelation
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(SpringExtension.class)
class ProductServiceSpec extends Specification {
    @MockitoExt
    private ProductRepository repository
    private ProductModelAssembler assembler = new ProductModelAssembler()
    private ProductService service = new ProductService(repository, assembler)

    def "should return all products"() {
        given:
        def p1 = new Product(name: "Product 1", description: "Description 1", price: 10.0)
        def p2 = new Product(name: "Product 2", description: "Description 2", price: 20.0)
        def products = Arrays.asList(p1, p2)
        def e1 = EntityModel.of(p1)
        def e2 = EntityModel.of(p2)
        def expected = CollectionModel.of(Arrays.asList(e1, e2),
            linkTo(methodOn(ProductController.class).getAll()).withSelfRel())
        repository.findAll() >> products
        assembler.toModel(p1) >> e1
        assembler.toModel(p2) >> e2

        when:
        CollectionModel<EntityModel<Product>> actual = service.getAll()

        then:
        actual.getContent().size() == expected.getContent().size()
        actual.getLink(LinkRelation.of("self")).get().getHref() == expected.getLink(LinkRelation.of("self")).get().getHref()
    }
}
