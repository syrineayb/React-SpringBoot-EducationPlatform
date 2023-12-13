package com.Project.project.Repository;

import com.Project.project.model.Course;
import com.Project.project.model.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LessonRepo extends JpaRepository<Lesson,Long> {
    @Query("SELECT l FROM Lesson l WHERE l.course = :course")
    List<Lesson> getLessonsByCourse(@Param("course") Course course);
    List<Lesson> findByCourse(Course course);
    @Query("SELECT l FROM Lesson l WHERE l.course.courseId= :courseId")
    List<Lesson> findByCourseId(Long courseId);
}