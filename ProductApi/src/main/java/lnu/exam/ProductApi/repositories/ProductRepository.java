package lnu.exam.ProductApi.repositories;

import lnu.exam.ProductApi.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
