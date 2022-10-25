package thewindmills.com.au.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class IndustryIdentifier extends PanacheEntity {

    @ManyToOne
    @JoinColumn(name="volumeInfoId", nullable=false)
    private VolumeInfo volumeInfo;


    private String type;
    private String identifier;

    
    public Long getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public VolumeInfo getVolumeInfo() {
        return volumeInfo;
    }

    public void setVolumeInfo(VolumeInfo volumeInfo) {
        this.volumeInfo = volumeInfo;
    }

    
}
