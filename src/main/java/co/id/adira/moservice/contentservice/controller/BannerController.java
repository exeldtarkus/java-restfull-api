package co.id.adira.moservice.contentservice.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.id.adira.moservice.contentservice.model.content.Banner;
import co.id.adira.moservice.contentservice.repository.content.BannerRepository;
import co.id.adira.moservice.contentservice.util.BaseResponse;

@RestController
@RequestMapping("/api")
public class BannerController {
	
	@Autowired
	private BannerRepository bannerRepository;
	
	@GetMapping(path = "/banners")
	public ResponseEntity<Object> getBanners(
		@RequestParam(required = false, defaultValue = "") final String using_on
	) {
		List<Banner> banners = bannerRepository.findBanner(using_on);
		return BaseResponse.jsonResponse(HttpStatus.OK, true, HttpStatus.OK.toString(), banners);
	}

}
