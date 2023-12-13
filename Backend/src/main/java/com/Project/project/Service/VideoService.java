package com.Project.project.Service;

import com.Project.project.model.Video;

import java.util.List;

public interface VideoService {
    List<Video> listAllVideo();
    Video addVideo(Video Video);
    void deleteVideo(int id );
    Video update(long idVideo, Video Video);
}
