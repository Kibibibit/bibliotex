package thewindmills.com.au.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class VolumeInfo extends PanacheEntity {

    private String title;
    private String subtitle;

    @ElementCollection
    @CollectionTable(name="Authors", joinColumns=@JoinColumn(name="volume_info_id"))
    private List<String> authors;
    private String publisher;
    private String publishedDate;

    @Column(length=4096)
    private String description;

    @OneToMany(mappedBy="volumeInfo")
    private List<IndustryIdentifier> industryIdentifiers;
    private String printType;

    @ElementCollection
    @CollectionTable(name="Categories", joinColumns=@JoinColumn(name="volume_info_id"))
    private List<String> categories;
    private double averageRating;
    private int ratingsCount;

    @OneToOne
    private ImageLinks imageLinks;
    private String language;
    private String mainCategory;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description.substring(0, Math.min(4096, description.length()));
    }

    public List<IndustryIdentifier> getIndustryIdentifiers() {
        if (industryIdentifiers == null) {
            return new ArrayList<IndustryIdentifier>();
        }
        return industryIdentifiers;
    }

    public void setIndustryIdentifiers(List<IndustryIdentifier> industryIdentifiers) {
        this.industryIdentifiers = industryIdentifiers;
    }

    public String getPrintType() {
        return printType;
    }

    public void setPrintType(String printType) {
        this.printType = printType;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public int getRatingsCount() {
        return ratingsCount;
    }

    public void setRatingsCount(int ratingsCount) {
        this.ratingsCount = ratingsCount;
    }

    public ImageLinks getImageLinks() {
        return imageLinks;
    }

    public void setImageLinks(ImageLinks imageLinks) {
        this.imageLinks = imageLinks;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getMainCategory() {
        return mainCategory;
    }

    public void setMainCategory(String mainCategory) {
        this.mainCategory = mainCategory;
    }

    
}
