package jupiter.backend.order;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

@Document(collection = "order")
public class Order {

    @Id
    private String id;

    @NotBlank
    private String userId;

    private HashMap<String, Integer> dishAmount = new HashMap<>();

    @CreatedDate
    private Date createAt;

    public Order() {
    }

    public Order(@NotBlank String userId, HashMap<String, Integer> dishAmount) {
        this.userId = userId;
        this.dishAmount = dishAmount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public HashMap<String, Integer> getDishAmount() {
        return dishAmount;
    }

    public void setDishAmount(HashMap<String, Integer> dishAmount) {
        this.dishAmount = dishAmount;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
