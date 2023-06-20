package com.cupk.snapshot.controller;

import com.cupk.snapshot.mapper.AcceptMapper;
import com.cupk.snapshot.mapper.PointsMapper;
import com.cupk.snapshot.mapper.ReportMapper;
import com.cupk.snapshot.mapper.SysUserMapper;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EcharsController.class)
class EcharsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SysUserMapper mockSysUserMapper;
    @MockBean
    private ReportMapper mockReportMapper;
    @MockBean
    private AcceptMapper mockAcceptMapper;
    @MockBean
    private PointsMapper mockPointsMapper;

    @Test
    void testTotalUser() throws Exception {
        // Setup
        when(mockSysUserMapper.getTotalUser()).thenReturn(0);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/charts/total/user")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testTotalReport() throws Exception {
        // Setup
        when(mockReportMapper.getTotalReport()).thenReturn(0);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/charts/total/report")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testTotalAccept() throws Exception {
        // Setup
        when(mockAcceptMapper.getTotalAccept()).thenReturn(0);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/charts/total/accept")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testTotalPoints() throws Exception {
        // Setup
        when(mockPointsMapper.getAllPoints()).thenReturn(0);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/charts/total/points")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testTypePercent() throws Exception {
        // Setup
        when(mockReportMapper.getQt()).thenReturn(0);
        when(mockReportMapper.getYxbd()).thenReturn(0);
        when(mockReportMapper.getChd()).thenReturn(0);
        when(mockReportMapper.getWztc()).thenReturn(0);
        when(mockReportMapper.getKcddh()).thenReturn(0);
        when(mockReportMapper.getNx()).thenReturn(0);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/charts/type/percent")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testReportNum() throws Exception {
        // Setup
        when(mockReportMapper.senven()).thenReturn(0);
        when(mockReportMapper.six()).thenReturn(0);
        when(mockReportMapper.five()).thenReturn(0);
        when(mockReportMapper.four()).thenReturn(0);
        when(mockReportMapper.three()).thenReturn(0);
        when(mockReportMapper.two()).thenReturn(0);
        when(mockReportMapper.one()).thenReturn(0);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/charts/report/num")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }
}
