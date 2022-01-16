package co.id.adira.moservice.contentservice.util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CloudinaryUtil {

	@Value("${cloudinary.cloud-name}")
	private String cloudinaryCloudName;

	@Value("${cloudinary.api-key}")
	private String cloudinaryApiKey;

	@Value("${cloudinary.api-secret}")
	private String cloudinaryApiSecret;

	@Value("${cloudinary.main-folder}")
	private String cloudinaryMainFolder;

	public Cloudinary initCloudinary() {
		Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
				"cloud_name", cloudinaryCloudName,
				"api_key", cloudinaryApiKey,
				"api_secret", cloudinaryApiSecret,
				"secure", true));
		return cloudinary;
	}

	public String getCloudinaryMainFolder() {
		return cloudinaryMainFolder;
	}

}