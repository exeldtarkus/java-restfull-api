package co.id.adira.moservice.contentservice.controller;

import org.springframework.web.bind.annotation.RestController;

import co.id.adira.moservice.contentservice.model.Promo;
import co.id.adira.moservice.contentservice.repository.PromoRepository;
import co.id.adira.moservice.contentservice.util.BaseResponse;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api")
public class PromoController {

	@Autowired
	private PromoRepository promoRepository;

	private final Date currentDate = new Date();

	private final String[] acceptedOrder = { "desc", "asc" };
	private final String[] acceptedSort = { "id", "name" };

	private Boolean ArrayIncludes(String[] arr, String i) {
		return Arrays.stream(arr).anyMatch(i::equals);
	}

	@GetMapping(path = "/promo")
	public ResponseEntity<Object> getPromos(@RequestParam(required = false, defaultValue = "") final String origin,
			@RequestParam(required = false, defaultValue = "1") final Integer page,
			@RequestParam(required = false, defaultValue = "10") final Integer size,
			@RequestParam(required = false, defaultValue = "desc") String order,
			@RequestParam(required = false, defaultValue = "id") String sort) {
		List<Promo> promos;
		Sort.Direction promoSort = Sort.Direction.DESC;
		if (this.ArrayIncludes(acceptedOrder, order)) {
			switch (order) {
			case "asc":
				promoSort = Sort.Direction.ASC;
				break;
			case "desc":
				promoSort = Sort.Direction.DESC;
				break;
			}
		}
		if (!this.ArrayIncludes(acceptedSort, sort))
			sort = "id";

		Pageable pageable = PageRequest.of(page, size, new Sort(promoSort, sort));

		switch (origin) {
		case "home":
			promos = (List<Promo>) promoRepository.findAllByZoneIdAndMore(1L, currentDate, pageable);
			break;
		case "promo":
			promos = (List<Promo>) promoRepository.findAllByZoneIdAndMore(2L, currentDate, pageable);
			break;
		default:
			promos = (List<Promo>) promoRepository.findAllAndMore(currentDate, pageable);
			break;
		}

		Integer start = Math.min(Math.max(size * (page - 1), 0), promos.size());
		Integer end = Math.min(Math.max(size * page, start), promos.size());
		Page<Promo> pages = new PageImpl<Promo>(promos.subList(start, end), pageable, promos.size());

		return BaseResponse.jsonResponse(HttpStatus.OK, false, HttpStatus.OK.toString(), pages);
	}

	@GetMapping(path = "/promo/{id}")
	public ResponseEntity<Object> getPromoById(@PathVariable Long id) {
		Optional<Promo> promo = (Optional<Promo>) promoRepository.findByIdAndMore(id, currentDate);
		if (promo == null) {
			return BaseResponse.jsonResponse(HttpStatus.NOT_FOUND, false, HttpStatus.NOT_FOUND.toString(), null);
		}
		return BaseResponse.jsonResponse(HttpStatus.OK, false, HttpStatus.OK.toString(), promo);
	}
}