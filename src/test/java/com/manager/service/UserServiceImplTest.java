package com.manager.service;

import com.manager.dao.UserDao;
import com.manager.model.UserAccount;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class UserServiceImplTest {
    private static UserAccount user;
    private static UserService userService;
    private static UserDao userDao;
    private static BCryptPasswordEncoder passwordEncoder;

    @BeforeAll
    static void beforeAll() {
        userDao = Mockito.mock(UserDao.class);
        passwordEncoder = Mockito.mock(BCryptPasswordEncoder.class);
        userService = new UserServiceImpl(userDao, passwordEncoder);
        user = new UserAccount();
        user.setId(1);
        user.setEmail("bob@i.ua");
        user.setPassword("password");
        user.setFirstName("Bob");
        user.setLastName("Johnson");
        user.setRole("ROLE_USER");
        user.setStatus(true);
    }

    @Test
    void save_Ok() {
        Mockito.when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        Mockito.when(userDao.save(user)).thenReturn(user);
        UserAccount actual = userService.save(user);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(user, actual);
        Assertions.assertEquals("encodedPassword", actual.getPassword());
        Assertions.assertEquals("ROLE_USER", actual.getRole());
        Assertions.assertNotNull(actual.getCreatedAt());
        Assertions.assertTrue(actual.isStatus());
    }

    @Test
    void findById_Ok() {
        Mockito.when(userDao.findById(1)).thenReturn(Optional.of(user));
        UserAccount actual = userService.findById(1);
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(user, actual);
    }

    @Test
    void findByInvalidId_notOk() {
        Mockito.when(userDao.findById(100)).thenReturn(Optional.empty());
        UserAccount actual = userService.findById(100);
        Assertions.assertNull(actual);
    }

    @Test
    void findByEmail_Ok() {
        Mockito.when(userDao.findByEmail("bob@i.ua")).thenReturn(user);
        UserAccount actual = userService.findByEmail("bob@i.ua");
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(user, actual);
    }

    @Test
    void findByInvalidEmail_notOk() {
        Mockito.when(userDao.findByEmail("bob/i.ua")).thenReturn(null);
        UserAccount actual = userService.findByEmail("bob/i.ua");
        Assertions.assertNull(actual);
    }

    @Test
    void update_Ok() {
        UserAccount updatedUser = new UserAccount();
        updatedUser.setId(1);
        updatedUser.setEmail("bobby@i.ua");
        updatedUser.setFirstName("Bobby");
        updatedUser.setLastName("Johnson");
        updatedUser.setStatus(false);

        Mockito.when(userDao.getById(1)).thenReturn(user);
        Mockito.when(userDao.save(user)).thenReturn(user);
        UserAccount actual = userService.update(updatedUser);

        Assertions.assertEquals(updatedUser.getFirstName(), actual.getFirstName());
        Assertions.assertEquals(updatedUser.getLastName(), actual.getLastName());
        Assertions.assertEquals(updatedUser.isStatus(), actual.isStatus());
    }

    @Test
    void update_notOk() {
        UserAccount updatedUser = new UserAccount();
        updatedUser.setId(2);
        updatedUser.setEmail("alice@i.ua");
        updatedUser.setFirstName("Alice");
        updatedUser.setLastName("Smith");
        updatedUser.setStatus(false);

        Mockito.when(userDao.getById(1)).thenReturn(user);
        Mockito.when(userDao.save(user)).thenReturn(user);

        Assertions.assertNull(userDao.getById(updatedUser.getId()));
    }
}
