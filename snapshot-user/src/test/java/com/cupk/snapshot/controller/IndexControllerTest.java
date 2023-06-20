package com.cupk.snapshot.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cupk.snapshot.domain.entity.Accept;
import com.cupk.snapshot.domain.entity.Report;
import com.cupk.snapshot.domain.entity.SysUser;
import com.cupk.snapshot.service.AcceptService;
import com.cupk.snapshot.service.ReportService;
import com.cupk.snapshot.service.SysUserService;
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
@WebMvcTest(IndexController.class)
public class IndexControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AcceptService mockAcceptService;
    @MockBean
    private ReportService mockReportService;
    @MockBean
    private SysUserService mockSysUserService;

    @Test
    public void testGetAll() throws Exception {
        // Setup
        // Configure AcceptService.list(...).
        final List<Accept> accepts = Arrays.asList(new Accept(0L, 0, "status"));
        when(mockAcceptService.list(any(LambdaQueryWrapper.class))).thenReturn(accepts);

        // Configure ReportService.getOne(...).
        final Report report = new Report(0L, "wfAddress", "wfType", "hphm", "pic1Url", "pic2Url", "pic3Url");
        when(mockReportService.getOne(any(LambdaQueryWrapper.class))).thenReturn(report);

        // Configure SysUserService.getOne(...).
        final SysUser sysUser = new SysUser("username", "password", "phoneNum");
        when(mockSysUserService.getOne(any(LambdaQueryWrapper.class))).thenReturn(sysUser);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/index")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }

    @Test
    public void testGetAll_AcceptServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockAcceptService.list(any(LambdaQueryWrapper.class))).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/index")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }
}
