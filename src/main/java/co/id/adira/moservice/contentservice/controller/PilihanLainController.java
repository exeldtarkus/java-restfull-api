package co.id.adira.moservice.contentservice.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.id.adira.moservice.contentservice.model.content.PilihanLain;
import co.id.adira.moservice.contentservice.repository.content.PilihanLainRepository;
import co.id.adira.moservice.contentservice.util.BaseResponse;

@RestController
@RequestMapping("/api")
public class PilihanLainController {
	
	@Autowired
	private PilihanLainRepository pilihanLainRepository;
	
	@GetMapping(path = "/pilihan-lain")
	public ResponseEntity<Object> getPilihanLain() {
		List<PilihanLain> pilihanLain = pilihanLainRepository.findTop15ByActiveOrderByPositionAsc(true);
		return BaseResponse.jsonResponse(HttpStatus.OK, true, HttpStatus.OK.toString(), pilihanLain);
	}
	
	@PostMapping(path = "/pilihan-lain")
	public ResponseEntity<Object> changePassword(@RequestBody PilihanLain pilihanLain) {
		pilihanLainRepository.save(pilihanLain);
		return BaseResponse.jsonResponse(HttpStatus.OK, true, "Kata sandi berhasil diubah", null);
	}

}
