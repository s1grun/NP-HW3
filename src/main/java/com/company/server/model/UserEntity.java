package com.company.server.model;

import com.company.common.UserDTO;

import javax.persistence.*;
import java.util.Objects;
import javax.persistence.GeneratedValue;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.LockModeType;

@NamedQueries(
        {
                @NamedQuery(
                        name = "findUser",
                        query = "SELECT user FROM UserEntity user WHERE user.username LIKE :username"
//                        lockMode = LockModeType.OPTIMISTIC
                ),
                @NamedQuery(
                        name = "checkPassword",
                        query = "SELECT pass FROM UserEntity pass WHERE pass.password LIKE :password"
                )
        }
)

@Entity
@Table(name = "user", schema = "filesys")
public class UserEntity implements UserDTO {
    private String username;
    private String password;
    private int uid;

    public UserEntity(String userName, String password) {
        this.username = userName;
        this.password = password;
    }

    public UserEntity() {
    }

    @Id
    @Column(name = "username")
    public String getUsername() {
        return username;
    }


    public void setUsername(String username) {
        this.username = username;
    }

    @Basic
    @Column(name = "password")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "uid")
    @GeneratedValue(strategy = GenerationType.AUTO)
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity that = (UserEntity) o;
        return uid == that.uid &&
                Objects.equals(username, that.username) &&
                Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password, uid);
    }
}
