package com.dylanwraith.ecommerce.service;

import com.dylanwraith.ecommerce.dto.ProductDTO;
import com.dylanwraith.ecommerce.model.Product;
import com.dylanwraith.ecommerce.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<Product> getByProductIds(List<Long> ids) {
        List<Product> products = productRepository.findAllById(ids);
        if (products.size() != ids.size()) throw new EntityNotFoundException(
                String.format("Expected %s products but found %s", ids.size(), products.size()));

        return products;
    }

    public Product createProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setName(productDTO.name());
        product.setPrice(productDTO.price());
        return productRepository.save(product);
    }

    public Product updateProduct(Long id, ProductDTO productDTO) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    existingProduct.setName(productDTO.name());
                    existingProduct.setPrice(productDTO.price());
                    return productRepository.save(existingProduct);
                })
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + id));
    }

    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException("Product not found with id: " + id);
        }
        productRepository.deleteById(id);
    }
}
