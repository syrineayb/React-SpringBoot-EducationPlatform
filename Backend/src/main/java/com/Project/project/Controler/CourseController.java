package com.Project.project.Controler;

import com.Project.project.Repository.CategoryRepo;
import com.Project.project.Repository.CourseRepo;
import com.Project.project.Repository.UserRepository;
import com.Project.project.ServiceImp.CourseServiceImp;
import com.Project.project.model.Category;
import com.Project.project.model.Course;
import com.Project.project.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class CourseController {
    @Autowired
    private CourseServiceImp CoService;
    @Autowired
    private CourseRepo Corepo;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CategoryRepo categoryRepository;

    @GetMapping("/getCourse")
    public List<Course> ListALLCourses() {
        return this.CoService.listAllCourse();
    }

    @GetMapping("/getCoursebypublisher")
    public List<Course> listAllCourses(Principal principal) {
        if (principal != null) {
            String publisherUsername = principal.getName();
            // Retrieve the User object using the username from your database
            Optional<User> publisherOptional = userRepository.findByUsername(publisherUsername);

            if (publisherOptional.isPresent()) {
                User publisher = publisherOptional.get();
                // Retrieve the courses associated with the publisher
                return Corepo.findByPublisher(publisher);
            } else {
                // Handle the case when the publisher is not found
                // You can return an appropriate response or handle it as per your application's requirements
            }
        } else {
            // Handle the case when no user is currently logged in
            // You can return an appropriate response or handle it as per your application's requirements
        }

        return Collections.emptyList(); // Return an empty list if no courses are found
    }

    @GetMapping("/findCoursebyid/{CourseID}")
    public ResponseEntity<Course> getCateByIdCat(@PathVariable("CourseID") int CourseID) {
        Course teach = CoService.getCourseById(CourseID);
        return new ResponseEntity<>(teach, HttpStatus.OK);
    }

    /*@PostMapping("/addCourse/{userId}")
    public ResponseEntity<Course> add(@PathVariable Long userId, @RequestBody Course course) {
        Optional<User> publisherOptional = userRepository.findById(userId);

        if (publisherOptional.isPresent()) {
            User publisher = publisherOptional.get();
            Long categoryId = course.getCategory().getIdCategory();

            // Check if the Category already exists in the database
            Optional<Category> categoryOptional = categoryRepository.findById(categoryId);

            if (categoryOptional.isPresent()) {
                Category category = categoryOptional.get();
                course.setPublisher(publisher);
                course.setCategory(category);
                Course newCourse = CoService.addCourse(course);
                return new ResponseEntity<>(newCourse, HttpStatus.CREATED);
            } else {
                // Handle the case when the category is not found
                // You can return an appropriate response or handle it as per your application's requirements
            }
        } else {
            // Handle the case when the publisher is not found
            // You can return an appropriate response or handle it as per your application's requirements
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }*/
    @PostMapping("/addCourse/{userId}")
    public ResponseEntity<Course> add(@PathVariable Long userId, @RequestBody Course course) {
        Optional<User> publisherOptional = userRepository.findById(userId);

        if (publisherOptional.isPresent()) {
            User publisher = publisherOptional.get();
            Long categoryId = course.getCategory().getIdCategory();

            // Check if the Category already exists in the database
            Optional<Category> categoryOptional = categoryRepository.findById(categoryId);

            if (categoryOptional.isPresent()) {
                Category category = categoryOptional.get();
                course.setPublisher(publisher);
                course.setCategory(category);
                Course newCourse = CoService.addCourse(course);
                return new ResponseEntity<>(newCourse, HttpStatus.CREATED);
            } else {
                // Handle the case when the category is not found
                // You can return an appropriate response or handle it as per your application's requirements
            }
        } else {
            // Handle the case when the publisher is not found
            // You can return an appropriate response or handle it as per your application's requirements
        }

        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/coursesByCategory/{categoryId}")
    public ResponseEntity<List<Course>> getCoursesByCategory(@PathVariable Long categoryId) {
        Optional<Category> categoryOptional = categoryRepository.findById(categoryId);

        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            List<Course> courses = CoService.getCoursesByCategory(category);
            return new ResponseEntity<>(courses, HttpStatus.OK);
        } else {
            // Handle the case when the category is not found
            // You can return an appropriate response or handle it as per your application's requirements
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/deleteCourse/{CourseID}")
    public ResponseEntity<?> deleteCourseBYID(@PathVariable("CourseID") int CourseID) {
        CoService.deleteCourse(CourseID);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("updateCourse/{userId}/{courseId}")
    public ResponseEntity<Course> updateCourseById(
            @PathVariable Long userId,
            @PathVariable Long courseId,
            @RequestBody Course updatedCourse
    ) {
        // Retrieve the User object using the userId from your database
        Optional<User> publisherOptional = userRepository.findById(userId);

        if (publisherOptional.isPresent()) {
            User publisher = publisherOptional.get();
            // Set the publisher for the updated course
            updatedCourse.setPublisher(publisher);
        } else {
            // Handle the case when the publisher is not found
            // You can return an appropriate response or handle it as per your application's requirements
            return ResponseEntity.notFound().build();
        }

        // Retrieve the existing course from the database
        Optional<Course> existingCourseOptional = Corepo.findById(courseId);

        if (existingCourseOptional.isPresent()) {
            Course existingCourse = existingCourseOptional.get();

            // Retrieve the managed Category entity based on the updatedCourse
            Category existingCategory = categoryRepository.findById(updatedCourse.getCategory().getIdCategory())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid Category ID"));

            // Update the properties of the existing course
            existingCourse.setName(updatedCourse.getName());
            existingCourse.setDescriptions(updatedCourse.getDescriptions());
            existingCourse.setDuration(updatedCourse.getDuration());
            existingCourse.setPhoto(updatedCourse.getPhoto());
            existingCourse.setCategory(existingCategory);

            // Save the updated course in the database
            Course updated = Corepo.save(existingCourse);
            return ResponseEntity.ok(updated);
        } else {
            // Handle the case when the course is not found
            // You can return an appropriate response or handle it as per your application's requirements
            return ResponseEntity.notFound().build();
        }
    }

}


