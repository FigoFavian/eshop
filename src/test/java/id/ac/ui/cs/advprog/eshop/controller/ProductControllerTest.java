package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.ModelAndView;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import org.mockito.ArgumentCaptor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;


import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.Optional;


import static org.mockito.ArgumentMatchers.anyString;

import static org.mockito.Mockito.when;




import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;


import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService service;

    @Test
    void testCreateProductPage() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/product/create")).andExpect(status().isOk())
                .andExpect(view().name("CreateProduct")).andExpect(model().attributeExists("product")).andReturn();

        ModelAndView modelAndView = result.getModelAndView();
        assertTrue(modelAndView.getModel().get("product") instanceof Product);
    }

    @Test
    void testCreateProductPost() throws Exception {
        this.mockMvc.perform(post("/product/create")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("productName", "Sigma Sigma bois"))
                .andExpect(redirectedUrl("list"));

        ArgumentCaptor<Product> captor = ArgumentCaptor.forClass(Product.class);
        verify(service, times(1)).create(captor.capture());
        assertEquals("Sigma Sigma bois", captor.getValue().getProductName());
    }

    @Test
    void testProductListPage() throws Exception {
        Product product1 = new Product();
        product1.setProductName("20th century boy's mask");
        product1.setProductQuantity(2);

        Product product2 = new Product();
        product2.setProductName("Mrs Magic Radio");
        product2.setProductQuantity(3);

        List<Product> products = Arrays.asList(product1, product2);
        when(service.findAll()).thenReturn(products);

        this.mockMvc.perform(get("/product/list"))
                .andExpect(status().isOk())
                .andExpect(view().name("productList"))
                .andExpect(model().attribute("products", products));
    }

    @Test
    void testEditProductPage() throws Exception {
        Product product = new Product();
        product.setProductId(UUID.randomUUID().toString());
        product.setProductName("Classic wrist watch");
        product.setProductQuantity(1);

        when(service.findProductById(product.getProductId())).thenReturn(product);

        this.mockMvc
                .perform(get("/product/edit/" + product.getProductId())).andExpect(status().isOk())
                .andExpect(model().attribute("product", product)).andExpect(view().name("EditProduct"));
    }

    @Test
    void testEditProductPost() throws Exception {
        Product product = new Product();
        product.setProductId(UUID.randomUUID().toString());
        product.setProductName("Rogue Lineage");
        product.setProductQuantity(1);

        this.mockMvc.perform(post("/product/edit").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("productId", "1").param("productName", "Deepwoken")
                        .param("productQuantity", "4")).andExpect(redirectedUrl("list"));

        //while being made, get the product
        ArgumentCaptor<String> captorId = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Product> productCaptor = ArgumentCaptor.forClass(Product.class);
        verify(service, times(1)).updateProductAfterEdit(productCaptor.capture());
    }

    @Test
    void testEditProductPageNotFound() throws Exception {
        when(service.findProductById("999"))
                .thenReturn(null);

        this.mockMvc.perform(get("/product/edit/999"))
                .andExpect(redirectedUrl("/product/list"));
    }

    @Test
    void testDeleteProduct() throws Exception {
        this.mockMvc.perform(get("/product/delete/1"))
                .andExpect(redirectedUrl("/product/list"));

        verify(service, times(1)).deleteProductById("1");
    }
}
