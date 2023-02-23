package lnu.exam.ProductApi.models;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "products")
@Setter
@Getter
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
    private Long id;

  @Column(name = "name", nullable = false, unique = true)
  @NotBlank(message = "Product name cannot be blank")
  private String name;

  @Column(name = "description")
  private String description;

  @Column(name = "price", nullable = false)
  @NotBlank(message = "Product price cannot be blank")
  private double price;

  public Product() {

  }

  public Product(String name, String description, double price) {
    this.name = name;
    this.description = description;
    this.price = price;
  }

  @Override
  public String toString() {
    return "Product(\nName: " + name + "\nDescription: " + description + "\nPrice: " + price + "\n)";
  }
}
