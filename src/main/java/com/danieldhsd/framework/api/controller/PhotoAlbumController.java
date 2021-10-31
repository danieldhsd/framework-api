package com.danieldhsd.framework.api.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.danieldhsd.framework.api.model.PhotoAlbum;
import com.danieldhsd.framework.api.repository.PhotoAlbumRepository;
import com.danieldhsd.framework.api.util.Utils;

@RestController
@RequestMapping("/photoAlbums")
public class PhotoAlbumController {

	@Autowired
	private PhotoAlbumRepository photoAlbumRepository;
	
	@GetMapping
	public List<PhotoAlbum> findAll() {
		return photoAlbumRepository.findAll();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<PhotoAlbum> findById(@PathVariable Long id) {
	    return photoAlbumRepository.findById(id)
	    		.map(photoAlbum -> ResponseEntity.ok().body(photoAlbum))
	    		.orElse(ResponseEntity.notFound().build());
	}
	
	@PostMapping
	public PhotoAlbum create(@RequestBody PhotoAlbum photoAlbum) {
	    return photoAlbumRepository.save(photoAlbum);
	}
	
	@PostMapping("/{idAlbum}/{idUser}")
	public PhotoAlbum addImage(@RequestParam MultipartFile image, @PathVariable Long idAlbum, 
								@PathVariable Long idUser) throws Exception {
		Optional<PhotoAlbum> photoAlbumOptional = photoAlbumRepository.findById(idAlbum);
		
		if(!photoAlbumOptional.isPresent()) {
			throw new Exception("Album não encontrado!");
		}
		
		PhotoAlbum photoAlbum = photoAlbumOptional.get();
		
		if(!idUser.equals(photoAlbum.getPhotoAlbumCreator().getId())) {
			throw new Exception("Você não tem permissão para adicionar fotos neste album!");
		}
		
		String filePath = Utils.saveFile(image);
		
		if(photoAlbum.getPhotos() == null) {
			photoAlbum.setPhotos(new ArrayList<>());
		}
		
		photoAlbum.getPhotos().add(filePath);
		return photoAlbumRepository.save(photoAlbum);
	}
	
	@PostMapping("/delete/{idAlbum}/{idUser}")
	public ResponseEntity<?> deleteAlbum(@PathVariable Long idAlbum, 
										@PathVariable Long idUser) throws Exception {
		Optional<PhotoAlbum> photoAlbumOptional = photoAlbumRepository.findById(idAlbum);
		
		if(!photoAlbumOptional.isPresent()) {
			throw new Exception("Album não encontrado!");
		}
		
		PhotoAlbum photoAlbum = photoAlbumOptional.get();
		
		if(!idUser.equals(photoAlbum.getPhotoAlbumCreator().getId())) {
			throw new Exception("Você não tem permissão para apagar esse album!");
		}
		
		photoAlbumRepository.delete(photoAlbum);
		return ResponseEntity.noContent().build();
	}
	
}
