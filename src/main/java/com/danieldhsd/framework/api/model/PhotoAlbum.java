package com.danieldhsd.framework.api.model;

import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "photo_album")
public class PhotoAlbum {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ElementCollection
	private List<String> photos;
	
	@ManyToOne
	@JoinColumn(name = "users_id")
	private User photoAlbumCreator;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<String> getPhotos() {
		return photos;
	}

	public void setPhotos(List<String> photos) {
		this.photos = photos;
	}

	public User getPhotoAlbumCreator() {
		return photoAlbumCreator;
	}

	public void setPhotoAlbumCreator(User photoAlbumCreator) {
		this.photoAlbumCreator = photoAlbumCreator;
	}
	
}
