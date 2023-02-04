//package com.deepak.productservice;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//import java.math.BigDecimal;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.testcontainers.containers.MongoDBContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//import com.deepak.productservice.dto.ProductRequest;
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//@SpringBootTest
//@Testcontainers
//class ProductServiceApplicationTests {
//
//	@Container
//	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.2");
//
//	@Autowired
//	private MockMvc mockmvc;
//
//	@Autowired
//	private ObjectMapper objectMapper;
//
//	@DynamicPropertySource
//	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
//		dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
//	}
//
//
//
//	@Test
//	void shouldCreateProduct() throws Exception {
//
//		ProductRequest productRequest = getProductRequest();
//		String productRequestStirng = objectMapper.writeValueAsString(productRequest);
//		mockmvc.perform(MockMvcRequestBuilders.post("api/product")
//						.contentType(MediaType.APPLICATION_JSON)
//						.content(productRequestStirng))
//				.andExpect(status().isCreated());
//	}
//
//	private ProductRequest getProductRequest() {
//		return ProductRequest.builder()
//				.name("iphone 13")
//				.description("iphone 13")
//				.price(BigDecimal.valueOf(1200))
//				.build();
//	}
//
//}
