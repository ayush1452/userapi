package com.example.userapi.service;

import com.example.userapi.dto.UserDTO;
import com.example.userapi.dto.UserRequestDTO;
import com.example.userapi.exception.ResourceNotFoundException;
import com.example.userapi.model.User;
import com.example.userapi.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserRequestDTO userRequestDTO;

    /**
     * Sets up test data and configures mocks for UserService tests.
     *
     * - Initializes a User entity and a UserRequestDTO with test data.
     * - Configures mock behavior for UserRepository and PasswordEncoder.
     */
    @BeforeEach
    void setUp() {
        user = new User();
        ReflectionTestUtils.setField(user, "id", 1L);
        user.setUsername("testuser");
        user.setPassword("encodedPassword");
        user.setEmail("test@example.com");
        user.setFirstName("Test");
        user.setLastName("User");

        userRequestDTO = new UserRequestDTO();
        userRequestDTO.setUsername("newuser");
        userRequestDTO.setPassword("plainPassword");
        userRequestDTO.setEmail("new@example.com");
        userRequestDTO.setFirstName("New");
        userRequestDTO.setLastName("User");
    }

    /**
     * Tests the getAllUsers method to ensure it retrieves all users from the repository.
     *
     * - Verifies the returned list contains the expected UserDTOs.
     */
    @Test
    void getAllUsersTest() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        List<UserDTO> result = userService.getAllUsers();
        assertEquals(1, result.size());
        assertEquals("testuser", result.get(0).getUsername());
        verify(userRepository, times(1)).findAll();
    }

    /**
     * Tests the getUserById method for a valid user ID.
     *
     * - Verifies the returned UserDTO matches the expected user details.
     * @throws ResourceNotFoundException If the user is not found.
     */
    @Test
    void getUserByIdTest_Success() throws ResourceNotFoundException {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        UserDTO result = userService.getUserById(1L);
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        verify(userRepository, times(1)).findById(1L);
    }

    /**
     * Tests the getUserById method for an invalid user ID.
     *
     * - Ensures a ResourceNotFoundException is thrown.
     */
    @Test
    void getUserByIdTest_NotFound() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(2L));
        verify(userRepository, times(1)).findById(2L);
    }

    /**
     * Tests the createUser method for a valid request.
     *
     * - Verifies the User entity is saved correctly and the returned UserDTO matches the expected details.
     */
    @Test
    void createUserTest() {
        when(passwordEncoder.encode("plainPassword")).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0, User.class);
            ReflectionTestUtils.setField(savedUser, "id", 2L);
            return savedUser;
        });
        UserDTO result = userService.createUser(userRequestDTO);
        assertEquals("newuser", result.getUsername());
        assertEquals("new@example.com", result.getEmail());

        // The returned UserDTO should now have the ID that we set via reflection
        assertEquals(2L, result.getId());
        verify(passwordEncoder, times(1)).encode("plainPassword");
        verify(userRepository, times(1)).save(any(User.class));
    }

    /**
     * Tests the updateUser method for a valid request.
     *
     * - Verifies the User entity is updated correctly and the returned UserDTO matches the updated details.
     * @throws ResourceNotFoundException If the user is not found.
     */
    @Test
    void updateUserTest_Success() throws ResourceNotFoundException {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        userRequestDTO.setEmail("updated@example.com");
        userRequestDTO.setPassword("newPassword");
        when(passwordEncoder.encode("newPassword")).thenReturn("encodedNewPassword");
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        UserDTO updated = userService.updateUser(1L, userRequestDTO);
        assertEquals("updated@example.com", updated.getEmail());
        assertEquals("encodedNewPassword", user.getPassword());
        verify(userRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).save(user);
    }

    /**
     * Tests the updateUser method for an invalid user ID.
     *
     * - Ensures a ResourceNotFoundException is thrown.
     */
    @Test
    void updateUserTest_NotFound() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userService.updateUser(2L, userRequestDTO));
        verify(userRepository, times(1)).findById(2L);
    }

    /**
     * Tests the deleteUser method for a valid user ID.
     *
     * - Ensures the User entity is deleted successfully.
     * @throws ResourceNotFoundException If the user is not found.
     */
    @Test
    void deleteUserTest_Success() throws ResourceNotFoundException {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        userService.deleteUser(1L);
        verify(userRepository, times(1)).delete(user);
    }

    /**
     * Tests the deleteUser method for an invalid user ID.
     *
     * - Ensures a ResourceNotFoundException is thrown.
     */
    @Test
    void deleteUserTest_NotFound() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(2L));
        verify(userRepository, times(1)).findById(2L);
    }

    /**
     * Tests the usernameExists method.
     *
     * - Verifies the method correctly identifies whether a username exists in the repository.
     */
    @ParameterizedTest
    @CsvSource({
            "existingUser, true",
            "newUser, false"
    })
    void testUsernameExists(String username, boolean expectedResult) {
        when(userRepository.existsByUsername(username)).thenReturn(expectedResult);
        boolean result = userService.usernameExists(username);
        assertEquals(expectedResult, result, "usernameExists should return the expected result");
        verify(userRepository, times(1)).existsByUsername(username);
    }

    /**
     * Tests the emailExists method.
     *
     * - Verifies the method correctly identifies whether an email exists in the repository.
     */
    @ParameterizedTest
    @CsvSource({
            "existing@example.com, true",
            "new@example.com, false"
    })
    void testEmailExists(String email, boolean expectedResult) {
        when(userRepository.existsByEmail(email)).thenReturn(expectedResult);
        boolean result = userService.emailExists(email);
        assertEquals(expectedResult, result, "emailExists should return the expected result");
        verify(userRepository, times(1)).existsByEmail(email);
    }

}
