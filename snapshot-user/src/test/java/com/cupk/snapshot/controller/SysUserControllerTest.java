package com.cupk.snapshot.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.cupk.snapshot.domain.entity.SysUser;
import com.cupk.snapshot.service.SysUserService;
import com.cupk.snapshot.utils.RedisUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringRunner.class)
@WebMvcTest(SysUserController.class)
public class SysUserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SysUserService mockSysUserService;
    @MockBean
    private BCryptPasswordEncoder mockBCryptPasswordEncoder;
    @MockBean
    private RedisUtils mockRedisUtils;

    @Test
    public void testSignUp() throws Exception {
        // Setup
        when(mockSysUserService.count(any(LambdaQueryWrapper.class))).thenReturn(0L);
        when(mockRedisUtils.get("key")).thenReturn("result");
        when(mockBCryptPasswordEncoder.encode("password")).thenReturn("password");

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/signup")
                        .param("username", "username")
                        .param("password", "password")
                        .param("phone_num", "phoneNum")
                        .param("sms_code", "smsCode")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
        verify(mockSysUserService).save(any(SysUser.class));
    }

    @Test
    public void testSignUp_RedisUtilsReturnsNull() throws Exception {
        // Setup
        when(mockSysUserService.count(any(LambdaQueryWrapper.class))).thenReturn(0L);
        when(mockRedisUtils.get("key")).thenReturn(null);
        when(mockBCryptPasswordEncoder.encode("password")).thenReturn("password");

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/signup")
                        .param("username", "username")
                        .param("password", "password")
                        .param("phone_num", "phoneNum")
                        .param("sms_code", "smsCode")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
        verify(mockSysUserService).save(any(SysUser.class));
    }

    @Test
    public void testInfo() throws Exception {
        // Setup
        // Configure SysUserService.getOne(...).
        final SysUser sysUser = new SysUser("username", "password", "phoneNum");
        when(mockSysUserService.getOne(any(LambdaQueryWrapper.class))).thenReturn(sysUser);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/info")
                        .param("user_id", "0")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }

    @Test
    public void testUpdatePasswd() throws Exception {
        // Setup
        // Configure SysUserService.getOne(...).
        final SysUser sysUser = new SysUser("username", "password", "phoneNum");
        when(mockSysUserService.getOne(any(LambdaQueryWrapper.class))).thenReturn(sysUser);

        when(mockBCryptPasswordEncoder.matches("passwd", "password")).thenReturn(false);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/update/passwd")
                        .param("user_id", "0")
                        .param("passwd", "passwd")
                        .param("new_passwd", "newPasswd")
                        .param("repeat", "repeat")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
    }

    @Test
    public void testUpdatePasswd_BCryptPasswordEncoderMatchesReturnsTrue() throws Exception {
        // Setup
        // Configure SysUserService.getOne(...).
        final SysUser sysUser = new SysUser("username", "password", "phoneNum");
        when(mockSysUserService.getOne(any(LambdaQueryWrapper.class))).thenReturn(sysUser);

        when(mockBCryptPasswordEncoder.matches("passwd", "password")).thenReturn(true);
        when(mockBCryptPasswordEncoder.encode("newPasswd")).thenReturn("result");

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/update/passwd")
                        .param("user_id", "0")
                        .param("passwd", "passwd")
                        .param("new_passwd", "newPasswd")
                        .param("repeat", "repeat")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
        verify(mockSysUserService).update(any(LambdaUpdateWrapper.class));
        verify(mockRedisUtils).del("keys");
    }

    @Test
    public void testUpdateInfo() throws Exception {
        // Setup
        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/update/info")
                        .param("user_id", "0")
                        .param("nick_name", "nickName")
                        .param("phone_num", "phoneNum")
                        .param("name", "name")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
        verify(mockSysUserService).update(any(LambdaUpdateWrapper.class));
    }

    @Test
    public void testLogout() throws Exception {
        // Setup
        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/signout")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertEquals(HttpStatus.OK.value(), response.getStatus());
        assertEquals("expectedResponse", response.getContentAsString());
        verify(mockRedisUtils).del("keys");
    }
}
