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

    @Test
    void getAllUsersTest() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        List<UserDTO> result = userService.getAllUsers();
        assertEquals(1, result.size());
        assertEquals("testuser", result.get(0).getUsername());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserByIdTest_Success() throws ResourceNotFoundException {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        UserDTO result = userService.getUserById(1L);
        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void getUserByIdTest_NotFound() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(2L));
        verify(userRepository, times(1)).findById(2L);
    }

    @Test
    void createUserTest() {
        // Mock password encoding
        when(passwordEncoder.encode("plainPassword")).thenReturn("encodedPassword");

        // Mock repository save: When a user is saved, set its ID to mimic DB behavior
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> {
            User savedUser = invocation.getArgument(0, User.class);
            // Use ReflectionTestUtils to set the private 'id' field
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

    @Test
    void updateUserTest_NotFound() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userService.updateUser(2L, userRequestDTO));
        verify(userRepository, times(1)).findById(2L);
    }

    @Test
    void deleteUserTest_Success() throws ResourceNotFoundException {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        userService.deleteUser(1L);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void deleteUserTest_NotFound() {
        when(userRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(2L));
        verify(userRepository, times(1)).findById(2L);
    }

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
