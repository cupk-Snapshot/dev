package com.cupk.snapshot.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cupk.snapshot.domain.entity.Points;
import com.cupk.snapshot.service.PointsService;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@WebMvcTest(PointsController.class)
public class PointsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PointsService mockPointsService;

    @Test
    public void testGetAll() throws Exception {
        // Setup
        // Configure PointsService.list(...).
        final List<Points> points = Arrays.asList(new Points(0L, "description", 0, 0));
        when(mockPointsService.list()).thenReturn(points);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/points/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }

    @Test
    public void testGetAll_PointsServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockPointsService.list()).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/points/all")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("[]", response.getContentAsString());
    }

    @Test
    public void testGetUserPoints() throws Exception {
        // Setup
        // Configure PointsService.list(...).
        final List<Points> points = Arrays.asList(new Points(0L, "description", 0, 0));
        when(mockPointsService.list(any(LambdaQueryWrapper.class))).thenReturn(points);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/points/details")
                        .param("user_id", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }

    @Test
    public void testGetUserPoints_PointsServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockPointsService.list(any(LambdaQueryWrapper.class))).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/points/details")
                        .param("user_id", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }
}
