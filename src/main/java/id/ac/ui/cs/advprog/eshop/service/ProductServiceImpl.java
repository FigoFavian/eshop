package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product create(Product product) {
        productRepository.create(product);
        return product;
    }

    @Override
    public List<Product> findAll() {
        Iterator<Product> productIterator = productRepository.findAll();
        List<Product> allProducts = new ArrayList<>();
        productIterator.forEachRemaining(allProducts::add);
        return allProducts;
    }

    @Override
    public Product findProductById(String productId) {
        return productRepository.findProductById(productId);
    }

    @Override
    public Product updateProductAfterEdit(Product updatedProduct) {
        return productRepository.updateProductAfterEdit(updatedProduct);
    }

    @Override
    public void deleteProductById(String productId) {
        productRepository.deleteProductById(productId);
    }
}