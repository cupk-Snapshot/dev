package com.cupk.snapshot.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.cupk.snapshot.domain.entity.Address;
import com.cupk.snapshot.service.AddressService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@WebMvcTest(AddressController.class)
public class AddressControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressService mockAddressService;

    @Test
    public void testGetAll() throws Exception {
        // Setup
        // Configure AddressService.list(...).
        final List<Address> addresses = Arrays.asList(new Address(0L, "name", "phoneNum", "area", "address", 0));
        when(mockAddressService.list(any(LambdaQueryWrapper.class))).thenReturn(addresses);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/address/all/{user_id}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }

    @Test
    public void testGetAll_AddressServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockAddressService.list(any(LambdaQueryWrapper.class))).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/address/all/{user_id}", 0)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }

    @Test
    public void testGetDefault() throws Exception {
        // Setup
        // Configure AddressService.getOne(...).
        final Address address = new Address(0L, "name", "phoneNum", "area", "address", 0);
        when(mockAddressService.getOne(any(LambdaQueryWrapper.class))).thenReturn(address);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/address/default")
                        .param("user_id", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }

    @Test
    public void testAdd() throws Exception {
        // Setup
        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/address/add")
                        .param("user_id", "0")
                        .param("name", "name")
                        .param("phone_num", "phoneNum")
                        .param("area", "area")
                        .param("address", "address")
                        .param("is_default", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
        verify(mockAddressService).update(any(LambdaUpdateWrapper.class));
        verify(mockAddressService).save(any(Address.class));
    }

    @Test
    public void testUpdate1() throws Exception {
        // Setup
        // Configure AddressService.getOne(...).
        final Address address = new Address(0L, "name", "phoneNum", "area", "address", 0);
        when(mockAddressService.getOne(any(LambdaQueryWrapper.class))).thenReturn(address);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/address/update")
                        .param("address_id", "0")
                        .param("user_id", "0")
                        .param("name", "name")
                        .param("phone_num", "phoneNum")
                        .param("area", "area")
                        .param("address", "address")
                        .param("is_default", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
        verify(mockAddressService).update(any(LambdaUpdateWrapper.class));
    }

    @Test
    public void testUpdate2() throws Exception {
        // Setup
        when(mockAddressService.remove(any(LambdaQueryWrapper.class))).thenReturn(false);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/address/delete")
                        .param("user_id", "0")
                        .param("address_id", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }
}
