package com.waid.service.entities.video;

import java.util.List;

import javax.persistence.EntityManager;

import com.waid.service.entities.entity.Video;
import com.waid.service.entities.entity.Viewer;

public class VideoServiceImpl implements VideoService {

	private EntityManager em;

	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	public void addVideo(Video video) {
		
		Video vd = (Video) em.find(Video.class, video.getId());
		
		if (vd == null) {
		em.persist(video);
		} else {
			for(Viewer viewer: video.getViewers()) {
				
				vd.addViewer(viewer);
			}
		}
	
		em.flush();

	}

	public void deleteAll() {
		em.createQuery("delete from Video").executeUpdate();
		em.flush();
	}

	
	public List<Video> getAll() {
		return em.createQuery("select v from Video v", Video.class)
				.getResultList();
	}



    
}
