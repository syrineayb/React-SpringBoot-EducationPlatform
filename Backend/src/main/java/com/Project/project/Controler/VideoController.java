package com.Project.project.Controler;

import com.Project.project.Repository.VideoRepo;
import com.Project.project.ServiceImp.VideoServiceImp;
import com.Project.project.model.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class VideoController {
    @Autowired
    private VideoServiceImp VidService;
    @Autowired
    private VideoRepo Corepo;

    @GetMapping("/getVideo")
    public List<Video> ListALLVideos(){
        return this.VidService.listAllVideo();
    }

    @PostMapping("/addVideo")
    public ResponseEntity<Video> add(@RequestBody Video Video) {
        Video newteach = VidService.addVideo(Video);
        return new ResponseEntity<>(newteach, HttpStatus.CREATED);
    }
    @DeleteMapping("/deleteVideo/{VideoID}")
    public ResponseEntity<?> deleteVideoBYID(@PathVariable("VideoID") int VideoID) {
        VidService.deleteVideo(VideoID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("updateVideo/{VideoID}")
    public Video updateVideoById(@PathVariable Long VideoID, @RequestBody Video updatedVideo) {
        return VidService.update(VideoID, updatedVideo);
    }
}