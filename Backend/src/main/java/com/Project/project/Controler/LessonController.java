package com.Project.project.Controler;
import com.Project.project.Repository.CourseRepo;
import com.Project.project.Repository.DocumentRepo;
import com.Project.project.Repository.LessonRepo;
import com.Project.project.ServiceImp.LessonServiceImp;
import com.Project.project.model.Course;
import com.Project.project.model.Document;
import com.Project.project.model.Lesson;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class LessonController {
    @Autowired
    private LessonServiceImp LService;
    @Autowired
    private LessonRepo Lrepo;
    @Autowired
    private CourseRepo courseRepo;
    @Autowired
    private DocumentRepo documentRepo;

    @GetMapping("/getLesson")
    public List<Lesson> ListALLLessons(){
        return this.LService.listAllLesson();
    }

    @GetMapping("/findLessonbyid/{LessonID}")
    public ResponseEntity<Lesson> getCateByIdCat(@PathVariable("LessonID") int LessonID) {
        Lesson teach = LService.getLessonById(LessonID);
        return new ResponseEntity<>(teach, HttpStatus.OK);
    }
    @PostMapping("/addLesson/{courseId}")
    public ResponseEntity<Lesson> addLessonWithDocument(
            @PathVariable("courseId") Long courseId,
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("videoConferenceLink") String videoConferenceLink
    ) throws IOException {
        Course course = courseRepo.findById(courseId).orElse(null);

        if (course != null) {
            Lesson lesson = new Lesson();
            lesson.setTitle(title);
            lesson.setVideoConferenceLink(videoConferenceLink);
            lesson.setCourse(course);

            Lesson newLesson = LService.addLesson(lesson);

            Document document = new Document();
            document.setName(file.getOriginalFilename());
            document.setContent(file.getBytes());
            document.setContentType(getContentTypeByExtension(FilenameUtils.getExtension(file.getOriginalFilename())));
            document.setLesson(newLesson);

            documentRepo.save(document);

            newLesson.setDocument(document);
            Lrepo.save(newLesson);

            return new ResponseEntity<>(newLesson, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private String getContentTypeByExtension(String fileExtension) {
        switch (fileExtension.toLowerCase()) {
            case "pdf":
                return "application/pdf";
            case "rar":
                return "application/x-rar-compressed";
            // Add more cases for other file extensions if needed
            default:
                return "application/octet-stream";
        }
    }

    @GetMapping("/lessonsByCourse/{courseId}")
    public ResponseEntity<List<Lesson>> getLessonsByCourse(@PathVariable Long courseId) {
        // Retrieve the course object using the courseId
        Course course = courseRepo.findById(courseId).orElse(null);

        if (course != null) {
            // Retrieve the lessons associated with the course
            List<Lesson> lessons = LService.getLessonsByCourse(course);

            return new ResponseEntity<>(lessons, HttpStatus.OK);
        } else {
            // Handle the case when the course is not found
            // You can return an appropriate response or handle it as per your application's requirements
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addlesson/{courseId}")
    public ResponseEntity<Lesson> add(@PathVariable("courseId") Long courseId, @RequestBody Lesson lesson) {
        // Retrieve the course object using the courseId from the path variable
        Course course = courseRepo.findById(courseId).orElse(null);

        if (course != null) {
            // Set the course for the lesson
            lesson.setCourse(course);

            // Save the lesson to the database
            Lesson newLesson = LService.addLesson(lesson);
            return new ResponseEntity<>(newLesson, HttpStatus.CREATED);
        } else {
            // Handle the case when the course is not found
            // You can return an appropriate response or handle it as per your application's requirements
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("/deleteLesson/{LessonID}")
    public ResponseEntity<?> deleteLessonBYID(@PathVariable("LessonID") int LessonID) {
        LService.deleteLesson(LessonID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("updateLesson/{LessonID}")
    public Lesson updateLessonById(@PathVariable Long LessonID, @RequestBody Lesson updatedLesson) {
        return LService.update(LessonID, updatedLesson);
    }
}