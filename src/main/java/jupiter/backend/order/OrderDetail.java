package jupiter.backend.order;

import org.springframework.data.annotation.CreatedDate;

import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class OrderDetail {

    private String id;

    private String userId;

    private List<OrderGallery> dishDetails = new ArrayList<>();

    private Date createAt;

    public OrderDetail() {
    }

    public OrderDetail(String id, String userId, List<OrderGallery> dishDetails, Date createAt) {
        this.id = id;
        this.userId = userId;
        this.dishDetails = dishDetails;
        this.createAt = createAt;
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

    public List<OrderGallery> getDishDetails() {
        return dishDetails;
    }

    public void setDishDetails(List<OrderGallery> dishDetails) {
        this.dishDetails = dishDetails;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
