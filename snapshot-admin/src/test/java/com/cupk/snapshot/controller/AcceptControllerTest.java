package com.cupk.snapshot.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cupk.snapshot.domain.entity.Accept;
import com.cupk.snapshot.domain.entity.Points;
import com.cupk.snapshot.domain.entity.Report;
import com.cupk.snapshot.domain.entity.SysUser;
import com.cupk.snapshot.service.AcceptService;
import com.cupk.snapshot.service.PointsService;
import com.cupk.snapshot.service.ReportService;
import com.cupk.snapshot.service.SysUserService;
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
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebMvcTest(AcceptController.class)
class AcceptControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AcceptService mockAcceptService;
    @MockBean
    private ReportService mockReportService;
    @MockBean
    private SysUserService mockSysUserService;
    @MockBean
    private PointsService mockPointsService;

    @Test
    void testGetAll() throws Exception {
        // Setup
        when(mockAcceptService.page(any(Page.class), any(Wrapper.class))).thenReturn(new Page<>(0L, 0L, 0L, false));

        // Configure ReportService.getOne(...).
        final Report report = new Report(0L, "wfAddress", "wfType", "hphm", "pic1Url", "pic2Url", "pic3Url");
        when(mockReportService.getOne(any(LambdaQueryWrapper.class))).thenReturn(report);

        // Configure SysUserService.getOne(...).
        final SysUser sysUser = new SysUser("username", "password", "phoneNum");
        when(mockSysUserService.getOne(any(LambdaQueryWrapper.class))).thenReturn(sysUser);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/accept/all")
                        .param("current", "0")
                        .param("size", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testExamine() throws Exception {
        // Setup
        when(mockPointsService.getTotalPoints(0L)).thenReturn(0);
        when(mockPointsService.save(any(Points.class))).thenReturn(false);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/accept/examine")
                        .param("userId", "0")
                        .param("acceptId", "0")
                        .param("status", "status")
                        .param("point", "0")
                        .param("examineId", "0")
                        .param("examineName", "examineName")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
        verify(mockAcceptService).update(any(LambdaUpdateWrapper.class));
    }

    @Test
    void testSearch() throws Exception {
        // Setup
        // Configure AcceptService.list(...).
        final List<Accept> accepts = Arrays.asList(new Accept(0L, 0, "status"));
        when(mockAcceptService.list(any(QueryWrapper.class))).thenReturn(accepts);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/accept/search")
                        .param("accept_id", "0")
                        .param("status", "status")
                        .param("is_public", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testSearch_AcceptServiceReturnsNoItems() throws Exception {
        // Setup
        when(mockAcceptService.list(any(QueryWrapper.class))).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/accept/search")
                        .param("accept_id", "0")
                        .param("status", "status")
                        .param("is_public", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testDelete() throws Exception {
        // Setup
        when(mockAcceptService.removeBatchByIds(Arrays.asList("value"))).thenReturn(false);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/accept/delete")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }
}
