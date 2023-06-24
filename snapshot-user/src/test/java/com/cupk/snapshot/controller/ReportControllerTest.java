package com.cupk.snapshot.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cupk.snapshot.domain.entity.Accept;
import com.cupk.snapshot.domain.entity.Report;
import com.cupk.snapshot.service.AcceptService;
import com.cupk.snapshot.service.ReportService;
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
@WebMvcTest(ReportController.class)
public class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportService mockReportService;
    @MockBean
    private AcceptService mockAcceptService;

    @Test
    public void testGetAll() throws Exception {
        // Setup
        // Configure ReportService.list(...).
        final List<Report> reports = Arrays.asList(
                new Report(0L, "address", "type", "hphm", "pic1Url", "pic2Url", "pic3Url"));
        when(mockReportService.list(any(LambdaQueryWrapper.class))).thenReturn(reports);

        when(mockAcceptService.getOne(any(LambdaQueryWrapper.class))).thenReturn(new Accept(0L, 0, "status"));

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/report/all")
                        .param("user_id", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }

    @Test
    public void testGetAll_ReportServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockReportService.list(any(LambdaQueryWrapper.class))).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/report/all")
                        .param("user_id", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }

    @Test
    public void testSubmit() throws Exception {
        // Setup
        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/report/submit")
                        .param("user_id", "0")
                        .param("hphm", "hphm")
                        .param("address", "address")
                        .param("type", "type")
                        .param("pic1_url", "pic1Url")
                        .param("pic2_url", "pic2Url")
                        .param("pic3_url", "pic3Url")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
        verify(mockReportService).save(any(Report.class));
        verify(mockAcceptService).save(any(Accept.class));
    }
}
