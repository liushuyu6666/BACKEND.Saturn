package jupiter.backend.dish;


import jupiter.backend.review.Review;
import org.joda.time.DateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.List;

public class Dish {

    @Id
    private String _id;

    private String name;

    private String imgUrl;

    private String desc;

    private String category;

    private Float price;

    private List<Review> review;

    @LastModifiedDate
    private Date modifiedAt;

    public Dish() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id() {
        Date now = new Date();
        this._id = Long.toString(now.getTime());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public List<Review> getReview() {
        return review;
    }

    public void setReview(List<Review> review) {
        this.review = review;
    }

    public Date getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt() {
        Date now = new Date();
        Date currentTime = new Date(now.getTime());
        this.modifiedAt = currentTime;
    }
}
