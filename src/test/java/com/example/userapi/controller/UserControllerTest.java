package com.example.userapi.controller;

import com.example.userapi.dto.UserDTO;
import com.example.userapi.dto.UserRequestDTO;
import com.example.userapi.exception.GlobalExceptionHandler;
import com.example.userapi.exception.ResourceNotFoundException;
import com.example.userapi.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
public class UserControllerTest
{
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    private UserDTO userDTO;
    private UserRequestDTO userRequest;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).setControllerAdvice(new GlobalExceptionHandler()).build();
        objectMapper = new ObjectMapper();

        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setUsername("testuser");
        userDTO.setEmail("test@example.com");
        userDTO.setFirstName("Test");
        userDTO.setLastName("User");

        userRequest = new UserRequestDTO();
        userRequest.setUsername("newuser");
        userRequest.setPassword("validPass");
        userRequest.setEmail("new@example.com");
    }

    @Test
    void getAllUsersTest() throws Exception {
        when(userService.getAllUsers()).thenReturn(Collections.singletonList(userDTO));
        mockMvc.perform(get("/api/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("testuser"));
    }

    @Test
    void getUserByIdTest_Success() throws Exception {
        when(userService.getUserById(1L)).thenReturn(userDTO);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    void getUserByIdTest_NotFound() throws Exception {
        when(userService.getUserById(2L)).thenThrow(new ResourceNotFoundException("User not found"));

        mockMvc.perform(get("/api/users/2"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found"));
    }

    @Test
    void createUserTest_Success() throws Exception {
        when(userService.usernameExists("newuser")).thenReturn(false);
        when(userService.emailExists("new@example.com")).thenReturn(false);
        when(userService.createUser(any(UserRequestDTO.class))).thenReturn(userDTO);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"));
    }


    @Test
    void createUserTest_UsernameTaken() throws Exception {
        when(userService.usernameExists("newuser")).thenReturn(true);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Username is already taken"));
    }

    @Test
    void createUserTest_EmailTaken() throws Exception {
        when(userService.usernameExists("newuser")).thenReturn(false);
        when(userService.emailExists("new@example.com")).thenReturn(true);

        mockMvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Email is already in use"));
    }

    @Test
    void updateUserTest_Success() throws Exception {
        when(userService.updateUser(eq(1L), any(UserRequestDTO.class))).thenReturn(userDTO);

        mockMvc.perform(put("/api/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    void updateUserTest_NotFound() throws Exception {
        when(userService.updateUser(eq(2L), any(UserRequestDTO.class))).thenThrow(new ResourceNotFoundException("User not found"));

        mockMvc.perform(put("/api/users/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userRequest)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found"));
    }

    @Test
    void deleteUserTest_Success() throws Exception {
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isOk());
        Mockito.verify(userService, Mockito.times(1)).deleteUser(1L);
    }

    @Test
    void deleteUserTest_NotFound() throws Exception {
        doThrow(new ResourceNotFoundException("User not found")).when(userService).deleteUser(2L);

        mockMvc.perform(delete("/api/users/2"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("User not found"));
    }
}