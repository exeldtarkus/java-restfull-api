package co.id.adira.moservice.contentservice.controller;

import org.springframework.web.bind.annotation.RestController;

import co.id.adira.moservice.contentservice.model.bengkel.Bengkel;
import co.id.adira.moservice.contentservice.model.content.Promo;
import co.id.adira.moservice.contentservice.model.content.PromoBengkelMapping;
import co.id.adira.moservice.contentservice.repository.bengkel.BengkelRepository;
import co.id.adira.moservice.contentservice.repository.content.PromoRepository;
import co.id.adira.moservice.contentservice.util.BaseResponse;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

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

@SuppressWarnings("unchecked")
@RestController
@RequestMapping("/api")
public class PromoController {

	@Autowired
	private PromoRepository promoRepository;

	@Autowired
	private BengkelRepository bengkelRepository;

	private final Date currentDate = new Date();

	private final String[] acceptedOrder = { "desc", "asc" };
	private final String[] acceptedSort = { "id", "name" };

	private Boolean ArrayIncludes(String[] arr, String i) {
		return Arrays.stream(arr).anyMatch(i::equals);
	}

	@GetMapping(path = "/promo")
	public ResponseEntity<Object> getPromos(
			@RequestParam(required = false, defaultValue = "") final String origin,
			@RequestParam(required = false, defaultValue = "1") final Integer page,
			@RequestParam(required = false, defaultValue = "10") final Integer size,
			@RequestParam(required = false, defaultValue = "desc") String order,
			@RequestParam(required = false, defaultValue = "id") String sort,
			@RequestParam(required = false, defaultValue = "") String q,
			@RequestParam(required = false, defaultValue = "0") List<Long> service_type,
			@RequestParam(required = false) String promo_type,
			@RequestParam(required = false) Long bengkel_id) {
		List<Promo> promos;

		// Get all service type if ?service_type=0
		if (service_type.get(0) == 0L) {
			service_type = Arrays
					.asList(new Long[] { 1L, 2L, 3L, 4L, 5L, 6L, 7L, 8L, 9L, 10L, 11L, 12L, 13L, 14L, 15L, 16L, 17L, 16L });
		}

		// Get all promo type if promo_type is null
		List<Integer> promoTypeList = new ArrayList<Integer>();
		if (promo_type == null) {
			promoTypeList.add(0);
			promoTypeList.add(1);
		} else {
			switch (promo_type) {
				case "0":
					promoTypeList.add(0);
					break;
				case "1":
					promoTypeList.add(1);
					break;
				default:
					promoTypeList.add(0);
					promoTypeList.add(1);
					break;
			}
		}

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
				promos = (List<Promo>) promoRepository.findAllByZoneIdAndMore(currentDate);
				break;
			case "promo":
				promos = (List<Promo>) promoRepository.findAllByZoneIdAndMore(2L, currentDate, pageable);
				break;
			default:
				promos = (bengkel_id != null) ? (List<Promo>) promoRepository.findByBengkelIdAndMore(bengkel_id, currentDate, pageable) : (List<Promo>) promoRepository.findAllAndMore(q, service_type, promoTypeList, currentDate, pageable);
				break;
		}

		Integer start = Math.min(Math.max(size * (page - 1), 0), promos.size());
		Integer end = Math.min(Math.max(size * page, start), promos.size());
		Page<Promo> pages = new PageImpl<Promo>(promos.subList(start, end), pageable, promos.size());

		return BaseResponse.jsonResponse(HttpStatus.OK, false, HttpStatus.OK.toString(), pages);
	}

	@GetMapping(path = "/promo/servis")
	public ResponseEntity<Object> getPromoByServisId(
			@RequestParam(required = false, defaultValue = "1") final Integer page,
			@RequestParam(required = false, defaultValue = "10") final Integer size,
			@RequestParam(required = false, defaultValue = "desc") String order,
			@RequestParam(required = false, defaultValue = "id") String sort, 
			@RequestParam(required = false) Long id) {

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
		promos = (List<Promo>) promoRepository.findByServisIdAndMore(id, currentDate, pageable);
		
		if (promos.size() != 0) {
			List<Long> bengkelIds = promos.parallelStream().map(Promo::getId).collect(Collectors.toList());
			//List<Bengkel> bengkels = (List<Bengkel>) bengkelRepository.findAllById(bengkelIds);
		}

		Integer start = Math.min(Math.max(size * (page - 1), 0), promos.size());
		Integer end = Math.min(Math.max(size * page, start), promos.size());
		Page<Promo> pages = new PageImpl<Promo>(promos.subList(start, end), pageable, promos.size());

		return BaseResponse.jsonResponse(HttpStatus.OK, false, HttpStatus.OK.toString(), pages);
	}

	@GetMapping(path = "/promo/{id}")
	public ResponseEntity<Object> getPromoById(@PathVariable Long id) {
		Optional<Promo> promo = (Optional<Promo>) promoRepository.findByIdAndMore(id, currentDate);
		if (promo.isPresent()) {
			List<Long> bengkelIds = promo.get().getBengkels().stream().map(PromoBengkelMapping::getBengkelId).collect(Collectors.toList());
			List<Bengkel> bengkels = (List<Bengkel>) bengkelRepository.findAllById(bengkelIds);
			ObjectMapper mapObject = new ObjectMapper();
			Map<String, Object> promoDetail = (Map<String, Object>) mapObject.convertValue(promo.get(), Map.class);
			promoDetail.put("availableFrom", new Date((Long) promoDetail.get("availableFrom")));
			promoDetail.put("availableUntil", new Date((Long) promoDetail.get("availableUntil")));
			promoDetail.put("bengkels", bengkels);
			return BaseResponse.jsonResponse(HttpStatus.OK, true, HttpStatus.OK.toString(), promoDetail);
		} else {
			return BaseResponse.jsonResponse(HttpStatus.NOT_FOUND, false, HttpStatus.NOT_FOUND.toString(), null);
		}
	}
}
