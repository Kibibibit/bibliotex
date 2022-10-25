package thewindmills.com.au.model;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class ImageLinks extends PanacheEntity {

    private String thumbnail;
    private String small;
    private String medium;
    private String large;
    private String samllThumbnail;
    private String extraLarge;

    public Long getId() {
        return id;
    }
    public String getThumbnail() {
        return thumbnail;
    }
    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
    public String getSmall() {
        return small;
    }
    public void setSmall(String small) {
        this.small = small;
    }
    public String getMedium() {
        return medium;
    }
    public void setMedium(String medium) {
        this.medium = medium;
    }
    public String getLarge() {
        return large;
    }
    public void setLarge(String large) {
        this.large = large;
    }
    public String getSamllThumbnail() {
        return samllThumbnail;
    }
    public void setSamllThumbnail(String samllThumbnail) {
        this.samllThumbnail = samllThumbnail;
    }
    public String getExtraLarge() {
        return extraLarge;
    }
    public void setExtraLarge(String extraLarge) {
        this.extraLarge = extraLarge;
    }

    
    
}
