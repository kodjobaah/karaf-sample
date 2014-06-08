package com.waid.service.entities.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "video")
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Video extends BaseEntity {


	@OneToMany(targetEntity = com.waid.service.entities.entity.Viewer.class, cascade = {CascadeType.PERSIST,CascadeType.REMOVE}, mappedBy = "video")
    private List<Viewer> viewers = new ArrayList<Viewer>();
	
	
	private String title;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Video(String title) {
		this.title = title;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<Viewer> getViewers() {
		return viewers;
	}


	public void setViewers(List<Viewer> viewers) {
		this.viewers = viewers;
	}

	public void addViewer(Viewer viewer) {
		viewer.setVideo(this);
		viewers.add(viewer);
	}

}
