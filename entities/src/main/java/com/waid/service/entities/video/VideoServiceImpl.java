package com.waid.service.entities.video;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.waid.service.entities.entity.Video;
import com.waid.service.entities.entity.Viewer;

public class VideoServiceImpl implements VideoService {

	private EntityManager em;

	public void setEntityManager(EntityManager em) {
		this.em = em;
	}

	public Video addVideo(Video video) {


		Video vd = null;
		try  {
			CriteriaBuilder queryBuilder = em.getCriteriaBuilder();
			CriteriaQuery<Object> qdef = queryBuilder.createQuery();
			Root<Video> vid = qdef.from(Video.class);
			//qdef.where(queryBuilder.equal(vid.get("id"), queryBuilder.parameter(Long.class, "id")));
			qdef.where(queryBuilder.equal(vid.get("title"), queryBuilder.parameter(String.class, "title")));
			Query query = em.createQuery(qdef);
			//query.setParameter("id", id);
			query.setParameter("title", video.getTitle());
			vd = (Video)query.getSingleResult();

		} catch (NoResultException nre) {
			vd = null;
		}
		
		if (vd == null) {
			em.persist(video);
			vd = video;
		} else {
			for(Viewer viewer: video.getViewers()) {

				vd.addViewer(viewer);
			}
		}

		em.flush();
		return vd;

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
