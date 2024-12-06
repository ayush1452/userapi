package com.example.userapi.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.userapi.dto.UserDTO;
import com.example.userapi.dto.UserRequestDTO;
import com.example.userapi.exception.ResourceNotFoundException;
import com.example.userapi.model.User;
import com.example.userapi.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.aot.DisabledInAotMode;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {UserService.class, PasswordEncoder.class})
@ExtendWith(SpringExtension.class)
@DisabledInAotMode
class UserServiceDiffblueTest {
    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    /**
     * Test {@link UserService#getAllUsers()}.
     * <p>
     * Method under test: {@link UserService#getAllUsers()}
     */
    @Test
    @DisplayName("Test getAllUsers()")
    @Disabled("TODO: Complete this test")
    void testGetAllUsers() {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Failed to create Spring context.
        //   Attempt to initialize test context failed with
        //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@5c81274f testClass = com.example.userapi.service.DiffblueFakeClass268, locations = [], classes = [com.example.userapi.service.UserService, org.springframework.security.crypto.password.PasswordEncoder], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@592d60a, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@7f7e00dd, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@fe67b40b, org.springframework.boot.test.web.reactor.netty.DisableReactorResourceFactoryGlobalResourcesContextCustomizerFactory$DisableReactorResourceFactoryGlobalResourcesContextCustomizerCustomizer@2b0e0bb0, org.springframework.boot.test.autoconfigure.OnFailureConditionReportContextCustomizerFactory$OnFailureConditionReportContextCustomizer@5a2f4537, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@40b929de, org.springframework.test.context.support.DynamicPropertiesContextCustomizer@0], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
        //       at java.base/java.util.stream.ReferencePipeline$3$1.accept(ReferencePipeline.java:197)
        //       at java.base/java.util.ArrayList$ArrayListSpliterator.forEachRemaining(ArrayList.java:1625)
        //       at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:509)
        //       at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
        //       at java.base/java.util.stream.ReduceOps$ReduceOp.evaluateSequential(ReduceOps.java:921)
        //       at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
        //       at java.base/java.util.stream.ReferencePipeline.collect(ReferencePipeline.java:682)
        //   See https://diff.blue/R026 to resolve this issue.

        // Arrange and Act
        userService.getAllUsers();
    }

    /**
     * Test {@link UserService#getAllUsers()}.
     * <ul>
     *   <li>Given {@link User} (default constructor) Email is
     * {@code jane.doe@example.org}.</li>
     *   <li>Then return size is one.</li>
     * </ul>
     * <p>
     * Method under test: {@link UserService#getAllUsers()}
     */
    @Test
    @DisplayName("Test getAllUsers(); given User (default constructor) Email is 'jane.doe@example.org'; then return size is one")
    void testGetAllUsers_givenUserEmailIsJaneDoeExampleOrg_thenReturnSizeIsOne() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setUsername("janedoe");

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findAll()).thenReturn(userList);
        UserService userService = new UserService();
        userService.userRepository = userRepository;

        // Act
        List<UserDTO> actualAllUsers = userService.getAllUsers();

        // Assert
        verify(userRepository).findAll();
        assertEquals(1, actualAllUsers.size());
        UserDTO getResult = actualAllUsers.get(0);
        assertEquals("Doe", getResult.getLastName());
        assertEquals("Jane", getResult.getFirstName());
        assertEquals("jane.doe@example.org", getResult.getEmail());
        assertEquals("janedoe", getResult.getUsername());
    }

    /**
     * Test {@link UserService#getAllUsers()}.
     * <ul>
     *   <li>Given {@link User} (default constructor) Email is
     * {@code john.smith@example.org}.</li>
     *   <li>Then return size is two.</li>
     * </ul>
     * <p>
     * Method under test: {@link UserService#getAllUsers()}
     */
    @Test
    @DisplayName("Test getAllUsers(); given User (default constructor) Email is 'john.smith@example.org'; then return size is two")
    void testGetAllUsers_givenUserEmailIsJohnSmithExampleOrg_thenReturnSizeIsTwo() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setUsername("janedoe");

        User user2 = new User();
        user2.setEmail("john.smith@example.org");
        user2.setFirstName("John");
        user2.setLastName("Smith");
        user2.setPassword("Fetching all users");
        user2.setUsername("Fetching all users");

        ArrayList<User> userList = new ArrayList<>();
        userList.add(user2);
        userList.add(user);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findAll()).thenReturn(userList);
        UserService userService = new UserService();
        userService.userRepository = userRepository;

        // Act
        List<UserDTO> actualAllUsers = userService.getAllUsers();

        // Assert
        verify(userRepository).findAll();
        assertEquals(2, actualAllUsers.size());
        UserDTO getResult = actualAllUsers.get(1);
        assertEquals("Doe", getResult.getLastName());
        UserDTO getResult2 = actualAllUsers.get(0);
        assertEquals("Fetching all users", getResult2.getUsername());
        assertEquals("Jane", getResult.getFirstName());
        assertEquals("John", getResult2.getFirstName());
        assertEquals("Smith", getResult2.getLastName());
        assertEquals("jane.doe@example.org", getResult.getEmail());
        assertEquals("janedoe", getResult.getUsername());
        assertEquals("john.smith@example.org", getResult2.getEmail());
        assertNull(getResult.getId());
    }

    /**
     * Test {@link UserService#getAllUsers()}.
     * <ul>
     *   <li>Given {@link UserRepository} {@link ListCrudRepository#findAll()} return
     * {@link ArrayList#ArrayList()}.</li>
     *   <li>Then return Empty.</li>
     * </ul>
     * <p>
     * Method under test: {@link UserService#getAllUsers()}
     */
    @Test
    @DisplayName("Test getAllUsers(); given UserRepository findAll() return ArrayList(); then return Empty")
    void testGetAllUsers_givenUserRepositoryFindAllReturnArrayList_thenReturnEmpty() {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findAll()).thenReturn(new ArrayList<>());
        UserService userService = new UserService();
        userService.userRepository = userRepository;

        // Act
        List<UserDTO> actualAllUsers = userService.getAllUsers();

        // Assert
        verify(userRepository).findAll();
        assertTrue(actualAllUsers.isEmpty());
    }

    /**
     * Test {@link UserService#getUserById(Long)}.
     * <p>
     * Method under test: {@link UserService#getUserById(Long)}
     */
    @Test
    @DisplayName("Test getUserById(Long)")
    @Disabled("TODO: Complete this test")
    void testGetUserById() throws ResourceNotFoundException {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Failed to create Spring context.
        //   Attempt to initialize test context failed with
        //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@7df1a33a testClass = com.example.userapi.service.DiffblueFakeClass269, locations = [], classes = [com.example.userapi.service.UserService, org.springframework.security.crypto.password.PasswordEncoder], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@592d60a, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@7f7e00dd, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@fe67b40b, org.springframework.boot.test.web.reactor.netty.DisableReactorResourceFactoryGlobalResourcesContextCustomizerFactory$DisableReactorResourceFactoryGlobalResourcesContextCustomizerCustomizer@2b0e0bb0, org.springframework.boot.test.autoconfigure.OnFailureConditionReportContextCustomizerFactory$OnFailureConditionReportContextCustomizer@5a2f4537, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@40b929de, org.springframework.test.context.support.DynamicPropertiesContextCustomizer@0], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
        //       at java.base/java.util.stream.ReferencePipeline$3$1.accept(ReferencePipeline.java:197)
        //       at java.base/java.util.ArrayList$ArrayListSpliterator.forEachRemaining(ArrayList.java:1625)
        //       at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:509)
        //       at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
        //       at java.base/java.util.stream.ReduceOps$ReduceOp.evaluateSequential(ReduceOps.java:921)
        //       at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
        //       at java.base/java.util.stream.ReferencePipeline.collect(ReferencePipeline.java:682)
        //   See https://diff.blue/R026 to resolve this issue.

        // Arrange and Act
        userService.getUserById(1L);
    }

    /**
     * Test {@link UserService#getUserById(Long)}.
     * <ul>
     *   <li>Given {@link User} (default constructor) Email is
     * {@code jane.doe@example.org}.</li>
     *   <li>Then return LastName is {@code Doe}.</li>
     * </ul>
     * <p>
     * Method under test: {@link UserService#getUserById(Long)}
     */
    @Test
    @DisplayName("Test getUserById(Long); given User (default constructor) Email is 'jane.doe@example.org'; then return LastName is 'Doe'")
    void testGetUserById_givenUserEmailIsJaneDoeExampleOrg_thenReturnLastNameIsDoe() throws ResourceNotFoundException {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setUsername("janedoe");
        Optional<User> ofResult = Optional.of(user);
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        UserService userService = new UserService();
        userService.userRepository = userRepository;

        // Act
        UserDTO actualUserById = userService.getUserById(1L);

        // Assert
        verify(userRepository).findById(eq(1L));
        assertEquals("Doe", actualUserById.getLastName());
        assertEquals("Jane", actualUserById.getFirstName());
        assertEquals("jane.doe@example.org", actualUserById.getEmail());
        assertEquals("janedoe", actualUserById.getUsername());
        assertNull(actualUserById.getId());
    }

    /**
     * Test {@link UserService#getUserById(Long)}.
     * <ul>
     *   <li>Then throw {@link ResourceNotFoundException}.</li>
     * </ul>
     * <p>
     * Method under test: {@link UserService#getUserById(Long)}
     */
    @Test
    @DisplayName("Test getUserById(Long); then throw ResourceNotFoundException")
    void testGetUserById_thenThrowResourceNotFoundException() throws ResourceNotFoundException {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        UserService userService = new UserService();
        userService.userRepository = userRepository;

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> userService.getUserById(1L));
        verify(userRepository).findById(eq(1L));
    }

    /**
     * Test {@link UserService#createUser(UserRequestDTO)}.
     * <p>
     * Method under test: {@link UserService#createUser(UserRequestDTO)}
     */
    @Test
    @DisplayName("Test createUser(UserRequestDTO)")
    void testCreateUser() {
        // Arrange
        when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn("secret");

        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setUsername("janedoe");
        when(userRepository.save(Mockito.<User>any())).thenReturn(user);

        UserRequestDTO userRequest = new UserRequestDTO();
        userRequest.setEmail("jane.doe@example.org");
        userRequest.setFirstName("Jane");
        userRequest.setLastName("Doe");
        userRequest.setPassword("iloveyou");
        userRequest.setUsername("janedoe");

        // Act
        UserDTO actualCreateUserResult = userService.createUser(userRequest);

        // Assert
        verify(userRepository).save(isA(User.class));
        verify(passwordEncoder).encode(isA(CharSequence.class));
        assertEquals("Doe", actualCreateUserResult.getLastName());
        assertEquals("Jane", actualCreateUserResult.getFirstName());
        assertEquals("jane.doe@example.org", actualCreateUserResult.getEmail());
        assertEquals("janedoe", actualCreateUserResult.getUsername());
        assertNull(actualCreateUserResult.getId());
    }

    /**
     * Test {@link UserService#updateUser(Long, UserRequestDTO)}.
     * <p>
     * Method under test: {@link UserService#updateUser(Long, UserRequestDTO)}
     */
    @Test
    @DisplayName("Test updateUser(Long, UserRequestDTO)")
    @Disabled("TODO: Complete this test")
    void testUpdateUser() throws ResourceNotFoundException {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Failed to create Spring context.
        //   Attempt to initialize test context failed with
        //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@474df973 testClass = com.example.userapi.service.DiffblueFakeClass270, locations = [], classes = [com.example.userapi.service.UserService, org.springframework.security.crypto.password.PasswordEncoder], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@592d60a, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@7f7e00dd, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@fe67b40b, org.springframework.boot.test.web.reactor.netty.DisableReactorResourceFactoryGlobalResourcesContextCustomizerFactory$DisableReactorResourceFactoryGlobalResourcesContextCustomizerCustomizer@2b0e0bb0, org.springframework.boot.test.autoconfigure.OnFailureConditionReportContextCustomizerFactory$OnFailureConditionReportContextCustomizer@5a2f4537, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@40b929de, org.springframework.test.context.support.DynamicPropertiesContextCustomizer@0], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
        //       at java.base/java.util.stream.ReferencePipeline$3$1.accept(ReferencePipeline.java:197)
        //       at java.base/java.util.ArrayList$ArrayListSpliterator.forEachRemaining(ArrayList.java:1625)
        //       at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:509)
        //       at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
        //       at java.base/java.util.stream.ReduceOps$ReduceOp.evaluateSequential(ReduceOps.java:921)
        //       at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
        //       at java.base/java.util.stream.ReferencePipeline.collect(ReferencePipeline.java:682)
        //   See https://diff.blue/R026 to resolve this issue.

        // Arrange
        UserRequestDTO userRequest = new UserRequestDTO();
        userRequest.setEmail("jane.doe@example.org");
        userRequest.setFirstName("Jane");
        userRequest.setLastName("Doe");
        userRequest.setPassword("iloveyou");
        userRequest.setUsername("janedoe");

        // Act
        userService.updateUser(1L, userRequest);
    }

    /**
     * Test {@link UserService#updateUser(Long, UserRequestDTO)}.
     * <ul>
     *   <li>Given {@link UserRepository} {@link CrudRepository#save(Object)} return
     * {@link User} (default constructor).</li>
     *   <li>Then return LastName is {@code Doe}.</li>
     * </ul>
     * <p>
     * Method under test: {@link UserService#updateUser(Long, UserRequestDTO)}
     */
    @Test
    @DisplayName("Test updateUser(Long, UserRequestDTO); given UserRepository save(Object) return User (default constructor); then return LastName is 'Doe'")
    void testUpdateUser_givenUserRepositorySaveReturnUser_thenReturnLastNameIsDoe() throws ResourceNotFoundException {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setUsername("janedoe");
        Optional<User> ofResult = Optional.of(user);

        User user2 = new User();
        user2.setEmail("jane.doe@example.org");
        user2.setFirstName("Jane");
        user2.setLastName("Doe");
        user2.setPassword("iloveyou");
        user2.setUsername("janedoe");
        UserRepository userRepository = mock(UserRepository.class);
        when(userRepository.save(Mockito.<User>any())).thenReturn(user2);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        UserService userService = new UserService();
        userService.userRepository = userRepository;
        UserRequestDTO userRequest = mock(UserRequestDTO.class);
        when(userRequest.getEmail()).thenReturn("jane.doe@example.org");
        when(userRequest.getFirstName()).thenReturn("Jane");
        when(userRequest.getLastName()).thenReturn("Doe");
        when(userRequest.getPassword()).thenReturn("");
        doNothing().when(userRequest).setEmail(Mockito.<String>any());
        doNothing().when(userRequest).setFirstName(Mockito.<String>any());
        doNothing().when(userRequest).setLastName(Mockito.<String>any());
        doNothing().when(userRequest).setPassword(Mockito.<String>any());
        doNothing().when(userRequest).setUsername(Mockito.<String>any());
        userRequest.setEmail("jane.doe@example.org");
        userRequest.setFirstName("Jane");
        userRequest.setLastName("Doe");
        userRequest.setPassword("iloveyou");
        userRequest.setUsername("janedoe");

        // Act
        UserDTO actualUpdateUserResult = userService.updateUser(1L, userRequest);

        // Assert
        verify(userRequest).getEmail();
        verify(userRequest).getFirstName();
        verify(userRequest).getLastName();
        verify(userRequest, atLeast(1)).getPassword();
        verify(userRequest).setEmail(eq("jane.doe@example.org"));
        verify(userRequest).setFirstName(eq("Jane"));
        verify(userRequest).setLastName(eq("Doe"));
        verify(userRequest).setPassword(eq("iloveyou"));
        verify(userRequest).setUsername(eq("janedoe"));
        verify(userRepository).findById(eq(1L));
        verify(userRepository).save(isA(User.class));
        assertEquals("Doe", actualUpdateUserResult.getLastName());
        assertEquals("Jane", actualUpdateUserResult.getFirstName());
        assertEquals("jane.doe@example.org", actualUpdateUserResult.getEmail());
        assertEquals("janedoe", actualUpdateUserResult.getUsername());
        assertNull(actualUpdateUserResult.getId());
    }

    /**
     * Test {@link UserService#updateUser(Long, UserRequestDTO)}.
     * <ul>
     *   <li>Then throw {@link ResourceNotFoundException}.</li>
     * </ul>
     * <p>
     * Method under test: {@link UserService#updateUser(Long, UserRequestDTO)}
     */
    @Test
    @DisplayName("Test updateUser(Long, UserRequestDTO); then throw ResourceNotFoundException")
    void testUpdateUser_thenThrowResourceNotFoundException() throws ResourceNotFoundException {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        UserService userService = new UserService();
        userService.userRepository = userRepository;

        UserRequestDTO userRequest = new UserRequestDTO();
        userRequest.setEmail("jane.doe@example.org");
        userRequest.setFirstName("Jane");
        userRequest.setLastName("Doe");
        userRequest.setPassword("iloveyou");
        userRequest.setUsername("janedoe");

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> userService.updateUser(1L, userRequest));
        verify(userRepository).findById(eq(1L));
    }

    /**
     * Test {@link UserService#deleteUser(Long)}.
     * <p>
     * Method under test: {@link UserService#deleteUser(Long)}
     */
    @Test
    @DisplayName("Test deleteUser(Long)")
    @Disabled("TODO: Complete this test")
    void testDeleteUser() throws ResourceNotFoundException {
        // TODO: Diffblue Cover was only able to create a partial test for this method:
        //   Reason: Failed to create Spring context.
        //   Attempt to initialize test context failed with
        //   java.lang.IllegalStateException: ApplicationContext failure threshold (1) exceeded: skipping repeated attempt to load context for [MergedContextConfiguration@103f1816 testClass = com.example.userapi.service.DiffblueFakeClass267, locations = [], classes = [com.example.userapi.service.UserService, org.springframework.security.crypto.password.PasswordEncoder], contextInitializerClasses = [], activeProfiles = [], propertySourceDescriptors = [], propertySourceProperties = [], contextCustomizers = [org.springframework.boot.test.context.filter.ExcludeFilterContextCustomizer@592d60a, org.springframework.boot.test.json.DuplicateJsonObjectContextCustomizerFactory$DuplicateJsonObjectContextCustomizer@7f7e00dd, org.springframework.boot.test.mock.mockito.MockitoContextCustomizer@fe67b40b, org.springframework.boot.test.web.reactor.netty.DisableReactorResourceFactoryGlobalResourcesContextCustomizerFactory$DisableReactorResourceFactoryGlobalResourcesContextCustomizerCustomizer@2b0e0bb0, org.springframework.boot.test.autoconfigure.OnFailureConditionReportContextCustomizerFactory$OnFailureConditionReportContextCustomizer@5a2f4537, org.springframework.boot.test.autoconfigure.actuate.observability.ObservabilityContextCustomizerFactory$DisableObservabilityContextCustomizer@1f, org.springframework.boot.test.autoconfigure.properties.PropertyMappingContextCustomizer@0, org.springframework.boot.test.autoconfigure.web.servlet.WebDriverContextCustomizer@40b929de, org.springframework.test.context.support.DynamicPropertiesContextCustomizer@0], contextLoader = org.springframework.test.context.support.DelegatingSmartContextLoader, parent = null]
        //       at org.springframework.test.context.cache.DefaultCacheAwareContextLoaderDelegate.loadContext(DefaultCacheAwareContextLoaderDelegate.java:145)
        //       at org.springframework.test.context.support.DefaultTestContext.getApplicationContext(DefaultTestContext.java:130)
        //       at java.base/java.util.stream.ReferencePipeline$3$1.accept(ReferencePipeline.java:197)
        //       at java.base/java.util.ArrayList$ArrayListSpliterator.forEachRemaining(ArrayList.java:1625)
        //       at java.base/java.util.stream.AbstractPipeline.copyInto(AbstractPipeline.java:509)
        //       at java.base/java.util.stream.AbstractPipeline.wrapAndCopyInto(AbstractPipeline.java:499)
        //       at java.base/java.util.stream.ReduceOps$ReduceOp.evaluateSequential(ReduceOps.java:921)
        //       at java.base/java.util.stream.AbstractPipeline.evaluate(AbstractPipeline.java:234)
        //       at java.base/java.util.stream.ReferencePipeline.collect(ReferencePipeline.java:682)
        //   See https://diff.blue/R026 to resolve this issue.

        // Arrange and Act
        userService.deleteUser(1L);
    }

    /**
     * Test {@link UserService#deleteUser(Long)}.
     * <ul>
     *   <li>Given {@link User} (default constructor) Email is
     * {@code jane.doe@example.org}.</li>
     *   <li>Then calls {@link CrudRepository#delete(Object)}.</li>
     * </ul>
     * <p>
     * Method under test: {@link UserService#deleteUser(Long)}
     */
    @Test
    @DisplayName("Test deleteUser(Long); given User (default constructor) Email is 'jane.doe@example.org'; then calls delete(Object)")
    void testDeleteUser_givenUserEmailIsJaneDoeExampleOrg_thenCallsDelete() throws ResourceNotFoundException {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        User user = new User();
        user.setEmail("jane.doe@example.org");
        user.setFirstName("Jane");
        user.setLastName("Doe");
        user.setPassword("iloveyou");
        user.setUsername("janedoe");
        Optional<User> ofResult = Optional.of(user);
        UserRepository userRepository = mock(UserRepository.class);
        doNothing().when(userRepository).delete(Mockito.<User>any());
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        UserService userService = new UserService();
        userService.userRepository = userRepository;

        // Act
        userService.deleteUser(1L);

        // Assert
        verify(userRepository).delete(isA(User.class));
        verify(userRepository).findById(eq(1L));
    }

    /**
     * Test {@link UserService#deleteUser(Long)}.
     * <ul>
     *   <li>Then throw {@link ResourceNotFoundException}.</li>
     * </ul>
     * <p>
     * Method under test: {@link UserService#deleteUser(Long)}
     */
    @Test
    @DisplayName("Test deleteUser(Long); then throw ResourceNotFoundException")
    void testDeleteUser_thenThrowResourceNotFoundException() throws ResourceNotFoundException {
        //   Diffblue Cover was unable to create a Spring-specific test for this Spring method.

        // Arrange
        UserRepository userRepository = mock(UserRepository.class);
        Optional<User> emptyResult = Optional.empty();
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(emptyResult);
        UserService userService = new UserService();
        userService.userRepository = userRepository;

        // Act and Assert
        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(1L));
        verify(userRepository).findById(eq(1L));
    }
}
