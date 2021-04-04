package saturn.backend.user;

import saturn.backend.role.Role;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Document(collection = "user")
public class User {

    @Id
    private String id; // if use .save, id can generate automatically

    @NotBlank
    @Size(max=50)
    private String username;

    @NotBlank
    @Size(max=100)
    private String password;

    @Size(max=100)
    @Email
    private String email;

    @DBRef
    private Set<Role> authorities = new HashSet<>();

    @CreatedDate
    private Date createAt;

    @LastModifiedDate
    private Date modifiedAt;

    public User() {
    }

    public User(
            @NotBlank @Size(max = 50) String username,
            @Size(max = 100) @Email String email,
            Set<Role> authorities) {
        this.username = username;
        this.email = email;
        this.authorities = authorities;
    }

    public User(
            @NotBlank @Size(max = 50) String username,
            @NotBlank @Size(max = 100) String password,
            @Size(max = 100) @Email String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public User(
            @NotBlank @Size(max = 50) String username,
            @NotBlank @Size(max = 100) String password,
            @Size(max = 100) @Email String email,
            Set<Role> authorities) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.authorities = authorities;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Set<Role> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Role> authorities) {
        this.authorities = authorities;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(Date modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}
