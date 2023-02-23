package lnu.exam.ProductApi.exceptions;


public class ResourceNotFoundException extends RuntimeException {

  public ResourceNotFoundException(Long id) {
    super("Could not find resource with id: " + id);
  }
}
