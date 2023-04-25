package com.manager.service;

import com.manager.model.UserAccount;
import java.util.List;
import java.util.Optional;

public interface UserService {
    public UserAccount save(UserAccount user);

    public boolean checkEmail(String email);

    public List<UserAccount> findAll();

    public UserAccount findByEmail(String email);

    public UserAccount findById(Integer id);

    public UserAccount update(UserAccount user);
}
