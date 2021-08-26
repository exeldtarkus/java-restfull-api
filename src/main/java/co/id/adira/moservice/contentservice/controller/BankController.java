package co.id.adira.moservice.contentservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import co.id.adira.moservice.contentservice.model.content.Bank;
import co.id.adira.moservice.contentservice.repository.content.BankRepository;
import co.id.adira.moservice.contentservice.util.BaseResponse;

@RestController
@RequestMapping("/api")
public class BankController {
	
	@Autowired
	private BankRepository bankRepository;
	
	@GetMapping(path = "/bank")
	public ResponseEntity<Object> getBank() {
		List<Bank> banks = bankRepository.findTop20ByActiveOrderByPositionAsc(true);
		return BaseResponse.jsonResponse(HttpStatus.OK, true, HttpStatus.OK.toString(), banks);
	}

}
