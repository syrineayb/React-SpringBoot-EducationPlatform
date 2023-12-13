package com.Project.project.ServiceImp;
import com.Project.project.Repository.CourseRepo;
import com.Project.project.Service.CourseService;
import com.Project.project.model.Category;
import com.Project.project.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
@Service
@Transactional

public class CourseServiceImp implements CourseService {
    @Autowired
    private CourseRepo repository;
    public List<Course> listAllCourse() {
        if(repository == null) {
            throw new RuntimeException("CourseRepo is null");
        }
        return repository.findAll();
    }

    public Course updateCourse(Course course) {
        return repository.save(course);
    }
    public List<Course> getCoursesByCategory(Category category) {
        return repository.findByCategory(category);
    }

    @Override
    public Course getCourseById(int idCourse) {
        return repository.findById((long) idCourse).get();
    }

    @Override
    public Course addCourse(Course Cour) {
        return repository.save(Cour);
    }

    @Override
    public void deleteCourse(int id) {
        repository.deleteById((long) id);
    }
    @Override
    public Course update(long courseId, Course course) {
        Optional<Course> optionalCourse = repository.findById(courseId);
        if (optionalCourse.isPresent()) {
            Course existingCourse = optionalCourse.get();
            existingCourse.setName(course.getName());
            existingCourse.setDescriptions(course.getDescriptions());
            existingCourse.setDuration(course.getDuration());
            existingCourse.setPhoto(course.getPhoto());
            existingCourse.setPublisher(course.getPublisher());
            existingCourse.setCategory(course.getCategory());
            return repository.save(existingCourse);
        } else {
            return null;
        }
    }

}
