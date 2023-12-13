package com.Project.project.Service;

import com.Project.project.model.Course;

import java.util.List;

public interface CourseService {
    List<Course> listAllCourse();
    Course getCourseById(int idCourse);
    Course addCourse(Course course);
    void deleteCourse(int id );
    Course update(long idCourse, Course course);
}
