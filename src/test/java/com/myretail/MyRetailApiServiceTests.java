package com.myretail;

import com.myretail.api.async.AsyncService;
import com.myretail.api.home.HomeController;
import com.myretail.api.mock.PricingApiController;
import com.myretail.api.product.ProductsApiController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class MyRetailApiServiceTests {

	@Autowired
	HomeController homeController;

	@Autowired
	AsyncService asyncService;

	@Autowired
	ProductsApiController productApiController;

	@Autowired
	PricingApiController mockPriceApiController;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testContextLoads() throws Exception {

		assertThat(homeController).isNotNull();

		this.mockMvc.perform(get("/"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("redirect:swagger-ui.html"))
		);

		assertThat(asyncService).isNotNull();

		assertThat(productApiController).isNotNull();

		assertThat(mockPriceApiController).isNotNull();

	}

}
