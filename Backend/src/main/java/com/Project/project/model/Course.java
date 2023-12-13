package com.Project.project.model;

import com.Project.project.models.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer"})
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long courseId;
    private String name ;
    private String descriptions;
    private String duration;
    private String photo;

    @Column(name = "publisher_username")
    private String publisherUsername;


    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "publisher_id", referencedColumnName = "id")
    private User publisher;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id")
    private Category category;

    @JsonIgnore
    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Lesson> lessons = new ArrayList<>();
    public Course() {
    }

    public Course(long courseId, String name, String descriptions, String duration, String photo, User publisher, Category category) {
        this.courseId = courseId;
        this.name = name;
        this.descriptions = descriptions;
        this.duration = duration;
        this.photo = photo;
        this.publisher = publisher;
        this.category = category;
        this.publisherUsername = publisher.getUsername();
    }
    public Course(String courseId) {
        this.courseId = Long.valueOf(courseId);
    }
    public void setPublisher(User publisher) {
        this.publisher = publisher;
        if (publisher != null) {
            this.publisherUsername = publisher.getUsername();
        }
    }
    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public User getPublisher() {
        return publisher;
    }



    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }
}
