package jupiter.backend.review;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.UUID;

public class Review {

    @Id
    private String _id; // even if there is a @Id notation, _id will not generate automatically.

    private String userId;

    private String content;

    private Integer stars;

    private Date createAt;

    public Review() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String userId) {
        Date now = new Date();
        this._id = Long.toString(now.getTime()) + userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt() {
        Date now = new Date();
        Date currentTime = new Date(now.getTime());
        this.createAt = currentTime;
    }
}
