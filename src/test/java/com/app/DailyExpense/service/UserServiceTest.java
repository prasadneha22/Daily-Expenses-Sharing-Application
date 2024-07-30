package com.app.DailyExpense.service;

import com.app.DailyExpense.Entity.User;
import com.app.DailyExpense.Repository.UserRepository;
import com.app.DailyExpense.Service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(1L);
        user.setEmail("john.doe@example.com");
        user.setName("John Doe");
        user.setMobileNumber("1234567890");
    }

    @Test
    void testCreateUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        User createdUser = userService.createUser(user);
        assertNotNull(createdUser);
        assertEquals("john.doe@example.com", createdUser.getEmail());
    }

    @Test
    void testGetUser() {
        when(userRepository.findById(1L)).thenReturn(java.util.Optional.of(user));
        User fetchedUser = userService.getUser(1L);
        assertNotNull(fetchedUser);
        assertEquals("John Doe", fetchedUser.getName());
    }

    @Test
    void testGetUserByEmail() {
        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(user);
        User fetchedUser = userService.getUserByEmail("john.doe@example.com");
        assertNotNull(fetchedUser);
        assertEquals("john.doe@example.com", fetchedUser.getEmail());
    }
}

