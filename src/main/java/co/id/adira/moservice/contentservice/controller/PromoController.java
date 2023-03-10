package co.id.adira.moservice.contentservice.controller;

import org.apache.maven.artifact.versioning.ComparableVersion;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import co.id.adira.moservice.contentservice.dto.bengkel.ProvinceCityDTO;
import co.id.adira.moservice.contentservice.model.bengkel.Bengkel;
import co.id.adira.moservice.contentservice.model.bengkel.GrupBengkel;
import co.id.adira.moservice.contentservice.model.content.Promo;
import co.id.adira.moservice.contentservice.model.content.PromoBengkelMapping;
import co.id.adira.moservice.contentservice.model.servis.ServiceType;
import co.id.adira.moservice.contentservice.repository.bengkel.BengkelRepository;
import co.id.adira.moservice.contentservice.repository.bengkel.CityRepository;
import co.id.adira.moservice.contentservice.repository.bengkel.GrupBengkelRepository;
import co.id.adira.moservice.contentservice.repository.content.PromoRepository;
import co.id.adira.moservice.contentservice.repository.servis.ServiceTypeRepository;
import co.id.adira.moservice.contentservice.util.BaseResponse;
import co.id.adira.moservice.contentservice.util.CloudinaryUtil;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.JpaSort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SuppressWarnings("unchecked")
@RestController
@RequestMapping("/api")
public class PromoController {

  @Value("${cloudinary.main-folder}")
	public String cloudinaryMainPath;

	@Autowired
	private PromoRepository promoRepository;

	@Autowired
	private BengkelRepository bengkelRepository;
	
	@Autowired
	private ServiceTypeRepository serviceTypeRepository;

	@Autowired
	private GrupBengkelRepository grupBengkelRepository;

	@Autowired
	private CityRepository cityRepository;

  @Autowired
	private CloudinaryUtil cloudinaryUtil;

	private final String[] acceptedOrder = { "desc", "asc" };
	private final String[] acceptedSort = { "g.km", "id", "name" };

	private Boolean ArrayIncludes(String[] arr, String i) {
		return Arrays.stream(arr).anyMatch(i::equals);
	}
	
	private String setCloudinaryPath = "https://res.cloudinary.com/adiramoservice/fl_attachment/v1/";

	@GetMapping(path = "/promo")
	public ResponseEntity<Object> getPromos(
			@RequestParam(required = false, defaultValue = "") final String origin,
			@RequestParam(required = false, defaultValue = "1") final Integer page,
			@RequestParam(required = false, defaultValue = "10") final Integer size,
			@RequestParam(required = false, defaultValue = "desc") String order,
			@RequestParam(required = false, defaultValue = "g.km") String sort,
			@RequestParam(required = false, defaultValue = "") String q,
			@RequestParam(required = false) List<Long> service_type,
			@RequestParam(required = false) String promo_type,
			@RequestParam(required = false) Long bengkel_id,
			@RequestParam(required = false, name = "cid") Long cityId,
			@RequestParam(required = false, name = "lat") Double latitude,
			@RequestParam(required = false, name = "lng") Double longitude,
			@RequestParam(required = false, name = "eid") Long eventId,
			@RequestParam(required = false, name = "vehicleTypes", defaultValue = "1") List<Long> vehicleTypes,
			@RequestHeader(name = "App-Version", required = false) final String appVersion
	) {

		Boolean freeOnly = null;
		if (!StringUtils.isEmpty(origin) && !StringUtils.isEmpty(appVersion)) {
			if (
					(origin.equals("adiraku")) ||
					(origin.equals("adirakunasabah"))
			) {
				ComparableVersion adiraku280C = new ComparableVersion("2.8.0");
				String[] appVersionSplitted = appVersion.split("-");
				ComparableVersion appVersionC = new ComparableVersion(appVersionSplitted[0]);
				if (appVersionC.compareTo(adiraku280C) < 0) {
					freeOnly = true;
				}
			}
		}

		Date currentDate = new Date();
		String serviceIdsList = null;
		String cloudinaryPath = cloudinaryUtil.getCloudinaryUrlPath() + cloudinaryUtil.getCloudinaryMainFolder();

		if (null == service_type) {
			List<ServiceType> serviceType = serviceTypeRepository.findAll();
			service_type = serviceType.stream().map(e -> e.getId()).collect(Collectors.toList());
		} else {
			serviceIdsList = service_type.get(0).toString();
		}
		// Get all promo type if promo_type is null
		List<Integer> promoTypeList = new ArrayList<Integer>();
		if (promo_type == null) {
			promoTypeList.add(0);
			promoTypeList.add(1);
			promoTypeList.add(2);
		} else {
			switch (promo_type) {
				case "0":
					promoTypeList.add(0);
					break;
				case "1":
					promoTypeList.add(1);
					break;
				case "2":
					promoTypeList.add(2);
					break;
				default:
					promoTypeList.add(0);
					promoTypeList.add(1);
					promoTypeList.add(2);
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
		if (!this.ArrayIncludes(acceptedSort, sort)){
			sort = (latitude != null && longitude != null) ? "g.km" : "id";
		}

		Pageable pageable = PageRequest.of((page-1), size, new Sort(promoSort, sort));
		List<Promo> promos;
		switch (origin) {
			case "home":
				promos = (List<Promo>) promoRepository.findAllByZoneIdAndMore(currentDate);
				break;
			case "promo":
				promos = (List<Promo>) promoRepository.findAllByZoneIdAndMore(2L, currentDate, pageable);
				break;
			case "adiraku": 
				promos = (List<Promo>) promoRepository.findAllAdirakuProspect(currentDate,latitude,longitude, pageable, service_type, serviceIdsList, vehicleTypes, freeOnly);
				break;
			case "adirakunasabah": 
				promos = (List<Promo>) promoRepository.findAllAdirakuNasabah(currentDate,latitude,longitude, pageable, service_type, serviceIdsList, vehicleTypes, freeOnly);
				break;
			default:
				if (bengkel_id != null) {
					promos = (List<Promo>) promoRepository.findByBengkelIdAndMore(bengkel_id, currentDate, pageable);
				} else {

					List<ServiceType> serviceTypesSearch;
					List<Long> searchServiceTypeIds = null;

					if ((q != null) && (!q.isEmpty())) {
						serviceTypesSearch = serviceTypeRepository.getServiceTypeByQuery(q);
						if (!serviceTypesSearch.isEmpty()) {
							searchServiceTypeIds = serviceTypesSearch
									.stream()
									.map(ServiceType::getId)
									.collect(Collectors.toList());
						}

					}

					if (eventId != null) {
						promos = (List<Promo>) promoRepository.findAllAndMoreEventId(
								q,
								service_type,
								promoTypeList,
								currentDate,
								serviceIdsList,
								searchServiceTypeIds,
								cityId,
								pageable,
								latitude,
								longitude,
								eventId
						);
					} else {
						promos = (List<Promo>) promoRepository.findAllAndMore(
								q,
								service_type,
								promoTypeList,
								currentDate,
								serviceIdsList,
								searchServiceTypeIds,
								cityId,
								pageable,
								latitude,
								longitude,
								vehicleTypes
						);
					}
				}
				break;
		}
		// = new ArrayList<ProvinceCityDTO>();
		for (Promo promo : promos) {
			if (origin.equals("adiraku") || origin.equals("adirakunasabah")) {
				promo.setImagePath(cloudinaryPath + promo.getImagePath2());
				promo.setImagePathMobile(cloudinaryPath + promo.getImagePath2());
			}
			List<ProvinceCityDTO> cities   = cityRepository.findAllCitiesByPromoId(promo.getId());
			promo.setProvinceCities(cities);

			Pageable pageableBengkels = PageRequest.of((page-1), size, new Sort(Sort.Direction.ASC, "km"));
			List<Long> bengkelIds = promo.getBengkels().stream().map(PromoBengkelMapping::getBengkelId).collect(Collectors.toList());
			List<Bengkel> listBengkel = bengkelRepository.findAllBengkelsByBengkelId(bengkelIds, pageableBengkels, latitude, longitude);
			promo.setLstBengkel(listBengkel);
		}

		Integer start = Math.min(Math.max(size * (page - 1), 0), promos.size());
		Integer end = Math.min(Math.max(size * page, start), promos.size());
		Page<Promo> pages = new PageImpl<Promo>(promos.subList(start, end), pageable, promos.size());
		System.out.println(promos.size());
		return BaseResponse.jsonResponse(HttpStatus.OK, false, HttpStatus.OK.toString(), pages);
	}

	@GetMapping(path = "/promo/servis")
	public ResponseEntity<Object> getPromoByServisId(
			@RequestParam(required = false, defaultValue = "1") final Integer page,
			@RequestParam(required = false, defaultValue = "10") final Integer size,
			@RequestParam(required = false, defaultValue = "desc") String order,
			@RequestParam(required = false, defaultValue = "id") String sort, 
			@RequestParam(required = false) Long id) {

		Date currentDate = new Date();
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

		Pageable pageable = PageRequest.of((page-1), size, new Sort(promoSort, sort));
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
	public ResponseEntity<Object> getPromoById(
			@PathVariable Long id,
			@RequestParam(required = false, defaultValue = "asc") String order,
			@RequestParam(required = false, defaultValue = "bengkel_id") String sort,
			@RequestParam(required = false, name = "lat") Double latitude,
			@RequestParam(required = false, name = "lng") Double longitude
	) {

		String cloudinaryPath = cloudinaryUtil.getCloudinaryUrlPath() + cloudinaryUtil.getCloudinaryMainFolder();
		Date currentDate = new Date();
		Optional<Promo> promo = (Optional<Promo>) promoRepository.findByIdAndMore(id);
		// Optional<Promo> promo = (Optional<Promo>) promoRepository.findByIdAndMore(id, currentDate);
		
		promo.get().setImagePath(cloudinaryPath + promo.get().getImagePath2());
		promo.get().setImagePathMobile(cloudinaryPath + promo.get().getImagePath2());

		if (promo.isPresent()) {

			// calculate total price
			BigDecimal discPercentage = BigDecimal.ZERO;
			try {
				discPercentage = (promo.get().getDiscAmount().multiply(new BigDecimal(100))).divide(promo.get().getOriginalPrice());
			} catch (Exception e) {
				discPercentage = BigDecimal.ZERO;
			}

			promo.get().setDiscPercentage(discPercentage.intValue());

			//BigDecimal discAmount = promo.get().getOriginalPrice().multiply(new BigDecimal(promo.get().getDiscPercentage()/100.0));
			//promo.get().setDiscAmount(new BigDecimal(discAmount.setScale(0, RoundingMode.HALF_DOWN).intValue()));
			promo.get().setDiscAmount(promo.get().getDiscAmount());

			BigDecimal totalPrice = promo.get().getOriginalPrice().subtract(promo.get().getDiscAmount());
			promo.get().setTotalPrice(new BigDecimal(totalPrice.add(promo.get().getServiceFee()).setScale(0, RoundingMode.HALF_DOWN).intValue()));

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
			if (!this.ArrayIncludes(acceptedSort, sort)) {
				sort = (latitude != null && longitude != null) ? "km" : "bengkel_id";
			}

			Pageable pageable = PageRequest.of(1, 99, new Sort(promoSort, sort));

			List<Long> bengkelIds = promo.get().getBengkels().stream().map(PromoBengkelMapping::getBengkelId).collect(Collectors.toList());
			List<Bengkel> bengkels2 = (List<Bengkel>) bengkelRepository.findAllBengkelsByBengkelId(bengkelIds, pageable, latitude, longitude);
			ObjectMapper mapObject = new ObjectMapper();
			Map<String, Object> promoDetail = (Map<String, Object>) mapObject.convertValue(promo.get(), Map.class);
			List<GrupBengkel> grupBengkels = (List<GrupBengkel>) grupBengkelRepository.findAllByBengkelIds(bengkelIds);
			promoDetail.put("availableFrom", new Date((Long) promoDetail.get("availableFrom")));
			promoDetail.put("availableUntil", new Date((Long) promoDetail.get("availableUntil")));
			promoDetail.put("bengkels", bengkels2);
			promoDetail.put("grupBengkels", grupBengkels);
			return BaseResponse.jsonResponse(HttpStatus.OK, true, HttpStatus.OK.toString(), promoDetail);
		} else {
			return BaseResponse.jsonResponse(HttpStatus.NOT_FOUND, false, HttpStatus.NOT_FOUND.toString(), null);
		}
	}
}
