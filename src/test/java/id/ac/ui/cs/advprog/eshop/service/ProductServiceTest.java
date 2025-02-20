package id.ac.ui.cs.advprog.eshop.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId(UUID.randomUUID().toString());
        product.setProductName("Test Product");
        product.setProductQuantity(10);
    }

    @Test
    void testCreateProduct() {
        when(productRepository.create(product)).thenReturn(product);

        Product createdProduct = productService.create(product);

        assertNotNull(createdProduct);
        assertEquals(product, createdProduct);
        verify(productRepository, times(1)).create(product);
    }

    @Test
    void testFindAllProducts() {
        Product product2 = new Product();
        product2.setProductId(UUID.randomUUID().toString());
        product2.setProductName("Uniqlo Collab Shirt");
        product2.setProductQuantity(5);

        Iterator<Product> productIterator = Arrays.asList(product, product2).iterator();
        when(productRepository.findAll()).thenReturn(productIterator);

        List<Product> products = productService.findAll();

        assertEquals(2, products.size());
        assertEquals(product, products.get(0));
        assertEquals(product2, products.get(1));
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindProductById() {
        when(productRepository.findProductById(product.getProductId())).thenReturn(product);

        Product foundProduct = productService.findProductById(product.getProductId());

        assertNotNull(foundProduct);
        assertEquals(product, foundProduct);
        verify(productRepository, times(1)).findProductById(product.getProductId());
    }

    @Test
    void testFindProductByIdNotFound() {
        when(productRepository.findProductById("this id dont exist")).thenReturn(null);

        Product foundProduct = productService.findProductById("this id dont exist");

        assertNull(foundProduct);
        verify(productRepository, times(1)).findProductById("this id dont exist");
    }

    @Test
    void testUpdateProductAfterEdit() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId(product.getProductId());
        updatedProduct.setProductName("Galaxy Nokia Note");
        updatedProduct.setProductQuantity(20);

        when(productRepository.updateProductAfterEdit(updatedProduct)).thenReturn(updatedProduct);

        Product result = productService.updateProductAfterEdit(updatedProduct);

        assertNotNull(result);
        assertEquals(updatedProduct, result);
        verify(productRepository, times(1)).updateProductAfterEdit(updatedProduct);
    }

    @Test
    void testUpdateNonExistentProduct() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId("this id dont exist");
        updatedProduct.setProductName("Matcha Parfume");
        updatedProduct.setProductQuantity(30);

        when(productRepository.updateProductAfterEdit(updatedProduct)).thenReturn(null);

        Product result = productService.updateProductAfterEdit(updatedProduct);

        assertNull(result);
        verify(productRepository, times(1)).updateProductAfterEdit(updatedProduct);
    }

    @Test
    void testDeleteProductById() {
        doNothing().when(productRepository).deleteProductById(product.getProductId());

        productService.deleteProductById(product.getProductId());

        verify(productRepository, times(1)).deleteProductById(product.getProductId());
    }
}
