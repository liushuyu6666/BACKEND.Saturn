package jupiter.backend.user;

import org.springframework.stereotype.Service;

@Service
public class UserDetail {

    private String username;
    private String role;

    public UserDetail() {
    }

    public UserDetail(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
