package co.id.adira.moservice.contentservice.controller;

import org.springframework.web.bind.annotation.RestController;

import co.id.adira.moservice.contentservice.model.Promo;
import co.id.adira.moservice.contentservice.repository.PromoRepository;
import co.id.adira.moservice.contentservice.util.BaseResponse;
import co.id.adira.moservice.contentservice.util.OffsetBasedPageRequest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api")
public class PromoController {

  @Autowired
  private PromoRepository promoRepository;

  
  @GetMapping(path="/promo")
  public ResponseEntity<Object> getPromos(
    @RequestParam(required = false, defaultValue = "") final String type,
    @RequestParam(required = false, defaultValue = "0") Integer offset,
    @RequestParam(required = false, defaultValue = "10") Integer limit
  ){
    Pageable pageable = new OffsetBasedPageRequest(offset, limit);
    Page<Promo> contents;
    
    switch(type){
      case "landing-page":
        contents = (Page<Promo>) promoRepository.findAllByZoneId(pageable, 1L);
      break;
      default: 
        contents = (Page<Promo>) promoRepository.findAll(pageable);
      break;
    }

    return BaseResponse.jsonResponse(HttpStatus.OK, false, HttpStatus.OK.toString(), contents);
  }

  @GetMapping(path="/promo/{id}")
  public ResponseEntity<Object> getPromoById(@PathVariable Long id){
    Optional<Promo> content = (Optional<Promo>) promoRepository.findById(id);
    return BaseResponse.jsonResponse(HttpStatus.OK, false, HttpStatus.OK.toString(), content);
  }
}