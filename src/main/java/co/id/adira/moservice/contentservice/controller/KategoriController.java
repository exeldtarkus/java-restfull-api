package co.id.adira.moservice.contentservice.controller;

import org.springframework.web.bind.annotation.RestController;
import co.id.adira.moservice.contentservice.model.content.Kategori;
import co.id.adira.moservice.contentservice.repository.content.KategoriRepository;
import co.id.adira.moservice.contentservice.util.BaseResponse;
import co.id.adira.moservice.contentservice.dto.content.EligibilityDTO;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;

@SuppressWarnings("unchecked")
@RestController
@RequestMapping("/api")
public class KategoriController {

    @Autowired
	private KategoriRepository kategoriRepository;

    @GetMapping(path = "/promo/check/{promoId}")
	public ResponseEntity<Object> checkifEligible(
			@PathVariable Long promoId,
			@RequestParam(required = false) String email,
			@RequestParam(required = false) Long uid) {

		EligibilityDTO isEligible = kategoriRepository.checkifEligible(promoId, email);;

		return BaseResponse.jsonResponse(HttpStatus.OK, false, HttpStatus.OK.toString(), isEligible);
	}
}

