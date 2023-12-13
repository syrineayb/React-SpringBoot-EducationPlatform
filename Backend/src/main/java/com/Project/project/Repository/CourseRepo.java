package com.Project.project.Repository;

import com.Project.project.model.Category;
import com.Project.project.model.Course;
import com.Project.project.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepo extends JpaRepository<Course,Long> {
    List<Course> findByPublisher(User publisher);
    List<Course> findByCategory(Category category);

}
