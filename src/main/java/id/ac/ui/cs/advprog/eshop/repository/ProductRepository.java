package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.UUID;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();

    public Product create(Product product) {
        if (product.getProductId() == null || product.getProductId().trim().isEmpty()) {
            product.setProductId(UUID.randomUUID().toString());
        }
        productData.add(product);
        return product;
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    public Product findProductById(String productId) {
        return productData.stream().filter(product -> product.getProductId().equals(productId)).findFirst().orElse(null);
    }

    public Product updateProductAfterEdit(Product updatedProduct) {
        for(int i = 0 ; i < productData.size() ; i++){
           if(productData.get(i).getProductId().equals(updatedProduct.getProductId())){
                productData.set(i, updatedProduct);
               return updatedProduct;
           }
        }
        return null;
    }

    public void deleteProductById(String productId) {
        productData.removeIf(product -> product.getProductId().equals(productId));
    }
}