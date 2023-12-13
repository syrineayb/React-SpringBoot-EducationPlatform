package com.Project.project.ServiceImp;

import com.Project.project.Repository.LessonRepo;
import com.Project.project.Repository.LessonRepo;
import com.Project.project.Service.LessonService;
import com.Project.project.Service.LessonService;
import com.Project.project.model.Course;
import com.Project.project.model.Lesson;
import com.Project.project.model.Lesson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
@Transactional

public class LessonServiceImp implements LessonService {
    @Autowired
    private LessonRepo repository;
    public List<Lesson> listAllLesson() {
        if(repository == null) {
            throw new RuntimeException("LessonRepo is null");
        }
        return repository.findAll();
    }

    public List<Lesson> getLessonsByCourse(Course course) {
        return repository.getLessonsByCourse(course);
    }

    @Override
    public Lesson getLessonById(int idLesson) {
        return repository.findById((long) idLesson).get();
    }

    @Override
    public Lesson addLesson(Lesson Les) {
        return repository.save(Les);
    }

    @Override
    public void deleteLesson(int id) {
        repository.deleteById((long) id);
    }

    @Override
    public Lesson update(Long idLesson, Lesson Lesson) {
        Optional<Lesson> optionalLesson = repository.findById(idLesson);
        if (optionalLesson.isPresent()) {
            Lesson existingLesson = optionalLesson.get();
            existingLesson.setTitle(Lesson.getTitle());
            existingLesson.setVideoUrl(Lesson.getVideoUrl());
            existingLesson.setDocumentId(Lesson.getDocumentId());
            existingLesson.setVideoConferenceLink(Lesson.getVideoConferenceLink());
            return repository.save(existingLesson);
        } else {
            return null;
        }
    }

}