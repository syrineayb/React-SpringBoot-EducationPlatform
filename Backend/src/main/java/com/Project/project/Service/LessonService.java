package com.Project.project.Service;

import com.Project.project.model.Lesson;

import java.util.List;

public interface LessonService {
    List<Lesson> listAllLesson();
    Lesson getLessonById(int idLesson);
    Lesson addLesson(Lesson Lesson);
    void deleteLesson(int id );
    Lesson update(Long idLesson, Lesson Lesson);
}
