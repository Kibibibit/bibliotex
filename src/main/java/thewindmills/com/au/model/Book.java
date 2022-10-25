package thewindmills.com.au.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;


@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book extends PanacheEntityBase {

    @Id
    private String id;

    private String selfLink;

    @OneToOne
    private VolumeInfo volumeInfo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSelfLink() {
        return selfLink;
    }

    public void setSelfLink(String selfLink) {
        this.selfLink = selfLink;
    }

    public VolumeInfo getVolumeInfo() {
        return volumeInfo;
    }

    public void setVolumeInfo(VolumeInfo volumeInfo) {
        this.volumeInfo = volumeInfo;
    }



}
