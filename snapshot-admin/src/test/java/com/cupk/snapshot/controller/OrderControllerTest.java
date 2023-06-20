package com.cupk.snapshot.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cupk.snapshot.domain.entity.Address;
import com.cupk.snapshot.domain.entity.Goods;
import com.cupk.snapshot.service.AddressService;
import com.cupk.snapshot.service.GoodsService;
import com.cupk.snapshot.service.OrderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService mockOrderService;
    @MockBean
    private AddressService mockAddressService;
    @MockBean
    private GoodsService mockGoodsService;

    @Test
    void testGetAll() throws Exception {
        // Setup
        when(mockOrderService.page(any(Page.class), any(Wrapper.class))).thenReturn(new Page<>(0L, 0L, 0L, false));

        // Configure AddressService.getOne(...).
        final Address address = new Address(0L, "name", "phoneNum", "area", "address", 0);
        when(mockAddressService.getOne(any(LambdaUpdateWrapper.class))).thenReturn(address);

        when(mockGoodsService.getOne(any(LambdaUpdateWrapper.class))).thenReturn(new Goods("title", "picUrl", 0, 0));

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/order/all")
                        .param("current", "0")
                        .param("size", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testDelete() throws Exception {
        // Setup
        when(mockOrderService.removeBatchByIds(Arrays.asList("value"))).thenReturn(false);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/order/delete")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }
}
