package com.manager.service;

import com.manager.dao.UserDao;
import com.manager.model.UserAccount;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserDao userDao, BCryptPasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserAccount save(UserAccount user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null) {
            user.setRole("ROLE_USER");
        }
        user.setCreatedAt(LocalDateTime.now());
        user.setStatus(true);
        return userDao.save(user);
    }

    @Override
    public boolean checkEmail(String email) {
        return userDao.existsByEmail(email);
    }

    @Override
    public List<UserAccount> findAll() {
        return userDao.findAll();
    }

    @Override
    public UserAccount findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    @Override
    public UserAccount findById(Integer id) {
        return userDao.findById(id).orElse(null);
    }

    @Override
    public UserAccount update(UserAccount user) {
        UserAccount userFromDb = userDao.getById(user.getId());
        if (user.getRole() == null) {
            userFromDb.setRole("ROLE_USER");
        } else {
            userFromDb.setRole(user.getRole());
        }
        userFromDb.setFirstName(user.getFirstName());
        userFromDb.setLastName(user.getLastName());
        userFromDb.setStatus(user.isStatus());
        return userDao.save(userFromDb);
    }
}
