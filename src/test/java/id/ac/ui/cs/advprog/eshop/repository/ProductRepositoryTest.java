package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;

    @BeforeEach
    void setup() {
        productRepository = new ProductRepository();
    }

    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());

    }
    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindALLIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2= new Product();
        product2.setProductId("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());

        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        assertEquals(product1.getProductName(), savedProduct.getProductName());
        assertEquals(product1.getProductQuantity(), savedProduct.getProductQuantity());

        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertEquals(product2.getProductName(), savedProduct.getProductName());
        assertEquals(product2.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testIfProductExistsAfterUpdate() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductId("eb558e9f-1c39-460-8860-71af6af63bd6");
        updatedProduct.setProductName("Sampo Cap Bambang Terbaru");
        updatedProduct.setProductQuantity(200);
        Product result = productRepository.updateProductAfterEdit(updatedProduct);

        assertNotNull(result);
        assertEquals("Sampo Cap Bambang Terbaru", result.getProductName());
        assertEquals(200, result.getProductQuantity());
    }

    @Test
    void testUpdateProductNotFound() {
        Product updatedProduct = new Product();
        updatedProduct.setProductId("non-existent-id");
        updatedProduct.setProductName("New Name");
        updatedProduct.setProductQuantity(100);
        Product result = productRepository.updateProductAfterEdit(updatedProduct);

        assertNull(result);
    }

    @Test
    void testIfProductIsActuallyDeleted(){

        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        productRepository.deleteProductById(product.getProductId());
        Product deletedProduct = productRepository.findProductById(product.getProductId());

        assertNull(deletedProduct);
    }

    @Test
    void testDeleteNonExistentProduct() {
        String fakeId = "non-existent-id";
        productRepository.deleteProductById(fakeId);
        Product result = productRepository.findProductById(fakeId);
        assertNull(result);
    }


    @Test
    void testFindProductByIdNotFound() {
        Product product = productRepository.findProductById("this id dont exist lil bro");
        assertNull(product);
    }

    @Test
    void testFindProductByIdReturnsNullIfNotFound() {
        Product result = productRepository.findProductById("non-existent-id");
        assertNull(result);
    }

    @Test
    void testCreateProductWithoutId() {
        Product product = new Product();
        product.setProductName("Test Product");
        product.setProductQuantity(10);

        Product savedProduct = productRepository.create(product);

        assertNotNull(savedProduct.getProductId());
        assertEquals("Test Product", savedProduct.getProductName());
        assertEquals(10, savedProduct.getProductQuantity());
    }

    @Test
    void testCreateProductWithWhitespaceId() {
        Product product = new Product();
        product.setProductId("   ");
        product.setProductName("Test Product");
        product.setProductQuantity(10);

        Product savedProduct = productRepository.create(product);

        assertNotNull(savedProduct.getProductId());
        assertEquals("Test Product", savedProduct.getProductName());
        assertEquals(10, savedProduct.getProductQuantity());
    }

    @Test
    void testUpdateProductNotFoundWithExistingProducts() {
        Product existingProduct = new Product();
        existingProduct.setProductId("existing-id");
        existingProduct.setProductName("Existing Product");
        existingProduct.setProductQuantity(50);
        productRepository.create(existingProduct);

        Product updatedProduct = new Product();
        updatedProduct.setProductId("non-existent-id");
        updatedProduct.setProductName("New Name");
        updatedProduct.setProductQuantity(100);

        Product result = productRepository.updateProductAfterEdit(updatedProduct);

        assertNull(result);
    }

    @Test
    void testFindProductByIdWithNullOrEmptyId() {
        assertNull(productRepository.findProductById(null), "Finding null should return null");
        assertNull(productRepository.findProductById(""), "Fidning empty string should return null");

        Product product = new Product();
        product.setProductId("valid-id");
        product.setProductName("Valid Product");
        product.setProductQuantity(10);
        productRepository.create(product);

        assertNull(productRepository.findProductById(null), "Even with data and should return null");
        assertNull(productRepository.findProductById(""), "Even with dataa and should return null");
    }


}