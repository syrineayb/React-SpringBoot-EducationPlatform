package com.Project.project.ServiceImp;

import com.Project.project.Repository.VideoRepo;
import com.Project.project.Service.VideoService;
import com.Project.project.model.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class VideoServiceImp implements VideoService {
    @Autowired
    private VideoRepo repository;
    public List<Video> listAllVideo() {
        if(repository == null) {
            throw new RuntimeException("VideoRepo is null");
        }
        return repository.findAll();
    }


    @Override
    public Video addVideo(Video Vid) {
        return repository.save(Vid);
    }

    @Override
    public void deleteVideo(int id) {
        repository.deleteById((long) id);
    }
    @Override
    public Video update(long VideoId, Video Video) {
        Optional<Video> optionalVideo = repository.findById(VideoId);
        if (optionalVideo.isPresent()) {
            Video existingVideo = optionalVideo.get();
            existingVideo.setName(Video.getName());
            existingVideo.setName(Video.getName());
            existingVideo.setUrl(Video.getUrl());
            return repository.save(existingVideo);
        } else {
            return null;
        }
    }

}
