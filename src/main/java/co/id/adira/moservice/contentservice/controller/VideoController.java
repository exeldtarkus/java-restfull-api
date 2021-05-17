package co.id.adira.moservice.contentservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.id.adira.moservice.contentservice.model.content.Video;
import co.id.adira.moservice.contentservice.repository.content.VideoRepository;
import co.id.adira.moservice.contentservice.util.BaseResponse;

@RestController
@RequestMapping("/api")
public class VideoController {
	
	@Autowired
	private VideoRepository videoRepository;
	
	@GetMapping(path = "/videos")
	public ResponseEntity<Object> getVideos(){
		List<Video> videos =  videoRepository.findTop8ByActiveOrderByIdDesc(true);
		return BaseResponse.jsonResponse(HttpStatus.OK, true, HttpStatus.OK.toString(), videos);
	}
}
