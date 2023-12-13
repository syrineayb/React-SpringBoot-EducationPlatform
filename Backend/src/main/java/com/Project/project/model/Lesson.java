package com.Project.project.model;

import javax.persistence.*;
import java.util.List;

@Entity
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lessonId;
    private String title;
    private String videoConferenceLink;
    private String videoUrl;
    private String documentId;

    @Lob
    private byte[] fileContent;
    private String fileName;

    @OneToOne(mappedBy = "lesson", cascade = CascadeType.ALL)
    private Document document;

    public Document getDocument() {
        return document;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    public Lesson() {
    }

    public Lesson(String title, String videoConferenceLink, Course course, String videoUrl, String documentId) {
        this.title = title;
        this.videoConferenceLink = videoConferenceLink;
        this.course = course;
        this.videoUrl = videoUrl;
        this.documentId = documentId;
    }

    public Long getLessonId() {
        return lessonId;
    }

    public void setLessonId(Long lessonId) {
        this.lessonId = lessonId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoConferenceLink() {
        return videoConferenceLink;
    }

    public void setVideoConferenceLink(String videoConferenceLink) {
        this.videoConferenceLink = videoConferenceLink;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public byte[] getFileContent() {
        return fileContent;
    }

    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }


    public void setDocument(Document document) {
        this.document = document;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
