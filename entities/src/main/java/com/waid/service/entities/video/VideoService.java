package com.waid.service.entities.video;

import java.util.List;

import com.waid.service.entities.entity.Video;


public interface VideoService {

	void addVideo(Video video);
	   
    void deleteAll();
    
    List<Video> getAll();
    
}
