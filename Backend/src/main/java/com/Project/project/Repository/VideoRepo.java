package com.Project.project.Repository;

import com.Project.project.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepo extends JpaRepository<Video,Long> {
}
