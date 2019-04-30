package com.junyangcompany.demo.security.mapping;

import com.junyangcompany.demo.entity.professerEntity.Examinee;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * author:pan le
 * Date:2019/4/15
 * Time:15:19
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @javax.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private String username;

    private String password;

    @ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER)
    private List<Role> Roles;

    @NotEmpty @javax.validation.constraints.Email
    private String email;

    private Boolean isValid;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List<Examinee> examinees;

}
