package com.manager.model;

import com.manager.lib.ValidPassword;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    @Size(min = 3, max = 16)
    @Pattern(regexp = "^[a-zA-Z]*$")
    @Column(unique = true)
    private String email;
    @NotNull
    @Size(min = 3, max = 16)
    @Pattern(regexp = "^[a-zA-Z0-9]*$")
    @ValidPassword
    private String password;
    @Size(min = 1, max = 16)
    @Pattern(regexp = "^[a-zA-Z]*$")
    private String firstName;
    @Size(min = 1, max = 16)
    @Pattern(regexp = "^[a-zA-Z]*$")
    private String lastName;
    private String role;
    private boolean status;
    private LocalDateTime createdAt;
}
