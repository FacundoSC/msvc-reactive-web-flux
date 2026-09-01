package org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.service;

import org.faccordoba.springcloud.msvc.msvc_crud_productos_reactivo_bd.model.Product;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductServiceImpTest {
    private  ProductService productService;
    @BeforeEach
    void setUp() {
        productService = new ProductServiceImp();
    }

    @Test
    @Order(1)
    void findByCategory() {
        StepVerifier.create(productService.findByCategory("Electronics"))
                .expectNextMatches(product -> product.getName().equals("Laptop"))
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    @Order(2)
    void deleteProduct() {
        StepVerifier.create(productService.delete(1))
                .expectNextMatches(product -> product.getName().equals("Laptop"))
                .verifyComplete();
    }

    @Test
    @Order(3)
    void addProduct() {
        Product product = new Product();
        product.setCode(23);
        product.setName("Pencil");
        StepVerifier.create(productService.save(product))
                .expectComplete()
                .verify();
    }

}