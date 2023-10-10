package ru.kata.spring.boot_security.demo.model;

import java.util.Set;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(name = "t_role")
public class Role implements GrantedAuthority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_role;
    @Column(name = "rolename")
    private String roleName;
//    @ManyToMany(cascade = CascadeType.ALL)
//    @JoinTable(
//            name = "t_user_role",
//            joinColumns = @JoinColumn(name = "id_role"),
//            inverseJoinColumns = @JoinColumn(name = "id_user")
//    )
//    private Set<User> users;

    public Role() {
    }

    public Role(Long id_role) {
        this.id_role = id_role;
    }

    public Role(Long id_role, String roleName) {
        this.id_role = id_role;
        this.roleName = roleName;
    }

    public Long getId() {
        return id_role;
    }

    public void setId(Long id_role) {
        this.id_role = id_role;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

//    public Set<User> getUsers() {
//        return users;
//    }
//
//    public void setUsers(Set<User> users) {
//        this.users = users;
//    }

    @Override
    public String getAuthority() {
        return getRoleName();
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id_role +
                ", name='" + roleName + '\'' +
                '}';
    }
}