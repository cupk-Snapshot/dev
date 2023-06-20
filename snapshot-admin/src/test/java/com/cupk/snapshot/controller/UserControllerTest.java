package com.cupk.snapshot.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cupk.snapshot.domain.entity.SysRole;
import com.cupk.snapshot.domain.entity.SysUser;
import com.cupk.snapshot.service.SysRoleService;
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
import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SysUserService mockSysUserService;
    @MockBean
    private SysRoleService mockSysRoleService;

    @Test
    void testGetAll() throws Exception {
        // Setup
        when(mockSysUserService.page(any(Page.class), any(Wrapper.class))).thenReturn(new Page<>(0L, 0L, 0L, false));

        // Configure SysRoleService.getSysRole(...).
        final SysRole sysRole = new SysRole();
        sysRole.setRoleId(0L);
        sysRole.setRoleName("roleName");
        sysRole.setRoleCode("roleCode");
        sysRole.setRoleDesc("roleDesc");
        sysRole.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        when(mockSysRoleService.getSysRole(0L)).thenReturn(sysRole);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/user/all")
                        .param("current", "0")
                        .param("size", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testInfo() throws Exception {
        // Setup
        // Configure SysUserService.getOne(...).
        final SysUser sysUser = new SysUser("username", "password", "phoneNum");
        when(mockSysUserService.getOne(any(LambdaQueryWrapper.class))).thenReturn(sysUser);

        // Configure SysRoleService.getSysRole(...).
        final SysRole sysRole = new SysRole();
        sysRole.setRoleId(0L);
        sysRole.setRoleName("roleName");
        sysRole.setRoleCode("roleCode");
        sysRole.setRoleDesc("roleDesc");
        sysRole.setCreateTime(new GregorianCalendar(2020, Calendar.JANUARY, 1).getTime());
        when(mockSysRoleService.getSysRole(0L)).thenReturn(sysRole);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/user/info")
                        .param("user_id", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testUpdate() throws Exception {
        // Setup
        when(mockSysUserService.update(any(LambdaUpdateWrapper.class))).thenReturn(false);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/user/update")
                        .param("userId", "0")
                        .param("nickName", "nickName")
                        .param("username", "username")
                        .param("phoneNum", "0")
                        .param("name", "name")
                        .param("isEnabled", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }

    @Test
    void testDelete() throws Exception {
        // Setup
        when(mockSysUserService.removeBatchByIds(Arrays.asList("value"))).thenReturn(false);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/user/delete")
                        .content("content").contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("expectedResponse");
    }
}
