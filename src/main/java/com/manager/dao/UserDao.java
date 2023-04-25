package com.manager.dao;

import com.manager.model.UserAccount;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<UserAccount, Integer> {
    public boolean existsByEmail(String email);

    public UserAccount findByEmail(String email);

    public Optional<UserAccount> findById(Integer id);
}
