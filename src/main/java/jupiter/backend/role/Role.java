package jupiter.backend.role;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "role")
public class Role {

    @Id
    private String id;

    private ERole name;

    public Role() {
    }

    public String getid() {
        return id;
    }

    public void setid(String id) {
        this.id = id;
    }

    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
        this.name = name;
    }
}
