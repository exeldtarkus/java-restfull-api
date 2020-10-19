package co.id.adira.moservice.contentservice.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import co.id.adira.moservice.contentservice.model.content.QRCode;
import co.id.adira.moservice.contentservice.repository.content.QRCodeRepository;
import co.id.adira.moservice.contentservice.util.BaseResponse;
import co.id.adira.moservice.contentservice.util.QRCodeUtil;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/api")
public class QRCodeController {
	
	@Value("${spring.base.upload.qrcode.url}")
	private String baseUploadQrcodeUrl;
	
	@Value("${spring.path.upload.qrcode}")
	private String pathUploadQrcode;

	@Autowired
	private QRCodeRepository qrCodeRepository;

	@GetMapping(path = "/qrcode")
	public void generateQRCode(String data) {

		data = "https://mobildev.adira.one/moservice";
		QRCodeWriter qrCodeWriter = new QRCodeWriter();

		try {
			BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 300, 300);
			Path path = FileSystems.getDefault().getPath(pathUploadQrcode);
			MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
			System.out.println("Successfully QR code generated in path.");
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	@PostMapping(path = "/qrcode/generate")
	public ResponseEntity<Object> generateQRCodeWithLogo(@RequestBody QRCode qrcode) {
		qrcode.setQrcodePath(pathUploadQrcode);
		String[] response = QRCodeUtil.generateQRCodeWithLogo(qrcode);
		qrcode.setQrcodePath(baseUploadQrcodeUrl + "/sp/qrcode/" + response[0] + ".png");
		qrcode.setBase64QRCode(response[1]);
		qrcode.setCreatedAt(new Date());
		qrCodeRepository.save(qrcode);
		return BaseResponse.jsonResponse(HttpStatus.OK, true, HttpStatus.OK.toString(), qrcode);
	}
	
	@GetMapping("/qrcode/download")
	public ResponseEntity<Resource> downloadQRCode(
			@RequestParam(name = "url", required = false) String url) {
		
		Path path = Paths.get(url);
		Resource resource = null;
		try {
			resource = new UrlResource(url);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		
		return ResponseEntity.ok()
				.contentType(MediaType.IMAGE_PNG)
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}
	
}
