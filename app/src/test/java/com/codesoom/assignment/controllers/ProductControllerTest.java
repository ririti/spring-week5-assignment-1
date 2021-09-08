package com.codesoom.assignment.controllers;

import com.codesoom.assignment.application.ProductService;
import com.codesoom.assignment.domain.Product;
import com.google.common.collect.Lists;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.endsWith;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.codesoom.assignment.domain.ProductConstants.ID;
import static com.codesoom.assignment.domain.ProductConstants.NAME;
import static com.codesoom.assignment.domain.ProductConstants.MAKER;
import static com.codesoom.assignment.domain.ProductConstants.PRICE;
import static com.codesoom.assignment.domain.ProductConstants.IMAGE_URL;
import static com.codesoom.assignment.domain.ProductConstants.PRODUCT;

@WebMvcTest(ProductController.class)
@DisplayName("ProductController 클래스")
class ProductControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @AfterEach
    void afterEach() {
        reset(productService);
    }

    @Nested
    @DisplayName("list 메서드는")
    class Describe_list {
        ResultActions subject() throws Exception {
            return mockMvc.perform(
                get("/products")
                    .accept(MediaType.APPLICATION_JSON_UTF8)
            );
        }

        OngoingStubbing<List<Product>> mockSubject() {
            return when(productService.getProducts());
        }

        @AfterEach
        void afterEach() {
            verify(productService).getProducts();
        }

        @Nested
        @DisplayName("저장된 Product가 없다면")
        class Context_product_empty {
            @BeforeEach
            void beforeEach() {
                mockSubject()
                    .thenReturn(Lists.newArrayList());
            }

            @Test
            @DisplayName("빈 목록을 리턴한다.")
            void it_returns_a_empty_list() throws Exception {
                subject()
                    .andExpect(status().isOk())
                    .andExpect(content().string("[]"));
            }
        }

        @Nested
        @DisplayName("저장된 Product가 있다면")
        class Context_product_exist {
            @BeforeEach
            void beforeEach() {
                mockSubject()
                    .thenReturn(Lists.newArrayList(PRODUCT));
            }

            @Test
            @DisplayName("Product 목록을 리턴한다.")
            void it_returns_a_product_list() throws Exception {
                subject()
                    .andExpect(status().isOk())
                    .andExpect(content().string(containsString("[")))
                    .andExpect(content().string(containsString("]")))
                    .andExpect(content().string(
                        containsString(PRODUCT.getId().toString())
                    ));
            }
        }
    }
}
