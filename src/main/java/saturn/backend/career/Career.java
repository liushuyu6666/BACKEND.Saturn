package saturn.backend.career;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "career")
public class Career {

    @Id
    private String id;

    private String companyName;

    private Date start;

    private Date deadline;

    private String city;

    private String position;

    private String content;

    private Boolean isActive;

    private Boolean isApplied;

    private String link;

    private String createBy;

    private String lastModify;

    @CreatedDate
    private Date createAt;

    @LastModifiedDate
    private Date modifiedAt;

    public Career() {
    }

    public Career(String companyName, Date start, Date deadline, String city, String position, String content, Boolean isActive, Boolean isApplied, String link, String createBy, String lastModify) {
        this.companyName = companyName;
        this.start = start;
        this.deadline = deadline;
        this.city = city;
        this.position = position;
        this.content = content;
        this.isActive = isActive;
        this.isApplied = isApplied;
        this.link = link;
        this.createBy = createBy;
        this.lastModify = lastModify;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getApplied() {
        return isApplied;
    }

    public void setApplied(Boolean applied) {
        isApplied = applied;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getLastModify() {
        return lastModify;
    }

    public void setLastModify(String lastModify) {
        this.lastModify = lastModify;
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
