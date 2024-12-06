package com.example.userapi.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.example.userapi.dto.UserDTO;
import com.example.userapi.dto.UserRequestDTO;
import com.example.userapi.repository.UserRepository;
import com.example.userapi.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {UserController.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class UserControllerDiffblueTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    /**
     * Test {@link UserController#getAllUsers()}.
     * <p>
     * Method under test: {@link UserController#getAllUsers()}
     */
    @Test
    @DisplayName("Test getAllUsers()")
    void testGetAllUsers() throws Exception {
        // Arrange
        when(userService.getAllUsers()).thenReturn(new ArrayList<>());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/users");

        // Act and Assert
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("[]"));
    }

    /**
     * Test {@link UserController#getUserById(Long)}.
     * <p>
     * Method under test: {@link UserController#getUserById(Long)}
     */
    @Test
    @DisplayName("Test getUserById(Long)")
    void testGetUserById() throws Exception {
        // Arrange
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("jane.doe@example.org");
        userDTO.setFirstName("Jane");
        userDTO.setId(1L);
        userDTO.setLastName("Doe");
        userDTO.setUsername("janedoe");
        when(userService.getUserById(Mockito.<Long>any())).thenReturn(userDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/api/users/{id}", 1L);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"username\":\"janedoe\",\"email\":\"jane.doe@example.org\",\"firstName\":\"Jane\",\"lastName\":\"Doe\"}"));
    }

    /**
     * Test {@link UserController#createUser(UserRequestDTO)}.
     * <p>
     * Method under test: {@link UserController#createUser(UserRequestDTO)}
     */
    @Test
    @DisplayName("Test createUser(UserRequestDTO)")
    void testCreateUser() throws Exception {
        // Arrange
        UserRequestDTO userRequest = new UserRequestDTO();
        userRequest.setEmail("jane.doe@example.org");
        userRequest.setFirstName("Jane");
        userRequest.setLastName("Doe");
        userRequest.setPassword("password123");
        userRequest.setUsername("janedoe");

        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("jane.doe@example.org");
        userDTO.setFirstName("Jane");
        userDTO.setId(1L);
        userDTO.setLastName("Doe");
        userDTO.setUsername("janedoe");

        // Mock repository behavior
        when(userRepository.existsByUsername(userRequest.getUsername())).thenReturn(false);
        when(userRepository.existsByEmail(userRequest.getEmail())).thenReturn(false);

        // Mock service behavior
        when(userService.createUser(Mockito.any(UserRequestDTO.class))).thenReturn(userDTO);

        // Convert the request object to JSON
        String content = new ObjectMapper().writeValueAsString(userRequest);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content()
                        .json("{\"id\":1,\"username\":\"janedoe\",\"email\":\"jane.doe@example.org\",\"firstName\":\"Jane\",\"lastName\":\"Doe\"}"));
    }



    /**
     * Test {@link UserController#updateUser(Long, UserRequestDTO)}.
     * <ul>
     *   <li>Given {@code jane.doe@example.org}.</li>
     *   <li>Then status {@link StatusResultMatchers#isOk()}.</li>
     * </ul>
     * <p>
     * Method under test: {@link UserController#updateUser(Long, UserRequestDTO)}
     */
    @Test
    @DisplayName("Test updateUser(Long, UserRequestDTO); given 'jane.doe@example.org'; then status isOk()")
    void testUpdateUser_givenJaneDoeExampleOrg_thenStatusIsOk() throws Exception {
        // Arrange
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("jane.doe@example.org");
        userDTO.setFirstName("Jane");
        userDTO.setId(1L);
        userDTO.setLastName("Doe");
        userDTO.setUsername("janedoe");
        when(userService.updateUser(Mockito.<Long>any(), Mockito.<UserRequestDTO>any())).thenReturn(userDTO);

        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setEmail("jane.doe@example.org");
        userRequestDTO.setFirstName("Jane");
        userRequestDTO.setLastName("Doe");
        userRequestDTO.setPassword("iloveyou");
        userRequestDTO.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(userRequestDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/users/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"id\":1,\"username\":\"janedoe\",\"email\":\"jane.doe@example.org\",\"firstName\":\"Jane\",\"lastName\":\"Doe\"}"));
    }

    /**
     * Test {@link UserController#updateUser(Long, UserRequestDTO)}.
     * <ul>
     *   <li>Given {@code U.U.U}.</li>
     *   <li>When {@link UserRequestDTO} (default constructor) Email is
     * {@code U.U.U}.</li>
     *   <li>Then status four hundred.</li>
     * </ul>
     * <p>
     * Method under test: {@link UserController#updateUser(Long, UserRequestDTO)}
     */
    @Test
    @DisplayName("Test updateUser(Long, UserRequestDTO); given 'U.U.U'; when UserRequestDTO (default constructor) Email is 'U.U.U'; then status four hundred")
    void testUpdateUser_givenUUU_whenUserRequestDTOEmailIsUUU_thenStatusFourHundred() throws Exception {
        // Arrange
        UserDTO userDTO = new UserDTO();
        userDTO.setEmail("jane.doe@example.org");
        userDTO.setFirstName("Jane");
        userDTO.setId(1L);
        userDTO.setLastName("Doe");
        userDTO.setUsername("janedoe");
        when(userService.updateUser(Mockito.<Long>any(), Mockito.<UserRequestDTO>any())).thenReturn(userDTO);

        UserRequestDTO userRequestDTO = new UserRequestDTO();
        userRequestDTO.setEmail("U.U.U");
        userRequestDTO.setFirstName("Jane");
        userRequestDTO.setLastName("Doe");
        userRequestDTO.setPassword("iloveyou");
        userRequestDTO.setUsername("janedoe");
        String content = (new ObjectMapper()).writeValueAsString(userRequestDTO);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/users/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(400));
    }

    /**
     * Test {@link UserController#deleteUser(Long)}.
     * <ul>
     *   <li>Given {@code /api/users/{id}}.</li>
     *   <li>When formLogin.</li>
     *   <li>Then status {@link StatusResultMatchers#isNotFound()}.</li>
     * </ul>
     * <p>
     * Method under test: {@link UserController#deleteUser(Long)}
     */
    @Test
    @DisplayName("Test deleteUser(Long); given '/api/users/{id}'; when formLogin; then status isNotFound()")
    void testDeleteUser_givenApiUsersId_whenFormLogin_thenStatusIsNotFound() throws Exception {
        // Arrange
        doNothing().when(userService).deleteUser(Mockito.<Long>any());
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder requestBuilder = SecurityMockMvcRequestBuilders.formLogin();

        // Act
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(userController).build().perform(requestBuilder);

        // Assert
        actualPerformResult.andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    /**
     * Test {@link UserController#deleteUser(Long)}.
     * <ul>
     *   <li>When one.</li>
     *   <li>Then status {@link StatusResultMatchers#isOk()}.</li>
     * </ul>
     * <p>
     * Method under test: {@link UserController#deleteUser(Long)}
     */
    @Test
    @DisplayName("Test deleteUser(Long); when one; then status isOk()")
    void testDeleteUser_whenOne_thenStatusIsOk() throws Exception {
        // Arrange
        doNothing().when(userService).deleteUser(Mockito.<Long>any());
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/users/{id}", 1L);

        // Act and Assert
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
