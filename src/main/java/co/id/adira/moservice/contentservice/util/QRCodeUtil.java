package co.id.adira.moservice.contentservice.util;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import javax.imageio.ImageIO;
import org.springframework.stereotype.Component;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.Dimension;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import co.id.adira.moservice.contentservice.model.content.QRCode;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class QRCodeUtil {
	
	private final static String MOSERVICE_LOGO = "https://assets.adira.one/sp/default/moservice-logo-qr.png";

	public void generateQRCode(String data) {

		data = "https://mobildev.adira.one/moservice";
		QRCodeWriter qrCodeWriter = new QRCodeWriter();

		try {
			BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, 300, 300);
			Path path = FileSystems.getDefault().getPath("/Users/fatchurrachman/qrcode/dv_qr.png");
			MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);
			log.info("Successfully QR code generated in path.");
		} catch (WriterException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static String[] generateQRCodeWithLogo(QRCode qrcode) {

		Map<EncodeHintType, ErrorCorrectionLevel> hints = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

		QRCodeWriter writer = new QRCodeWriter();
		BitMatrix bitMatrix = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		try {
		    // Create a qr code with the url as content and a size of 250x250 px
		    bitMatrix = writer.encode(qrcode.getData(), BarcodeFormat.QR_CODE, 300, 300, hints);

		    MatrixToImageConfig config = new MatrixToImageConfig(MatrixToImageConfig.BLACK, MatrixToImageConfig.WHITE);

		    // Load QR image
		    BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix, config);

		    // Load logo image
		    //BufferedImage logoImage = resize(new URL(MOSERVICE_LOGO), new Dimension(50, 50));
		    BufferedImage logoImage = resize(new URL(MOSERVICE_LOGO), null);

		    // Calculate the delta height and width between QR code and logo
		    int deltaHeight = qrImage.getHeight() - logoImage.getHeight();
		    int deltaWidth = qrImage.getWidth() - logoImage.getWidth();

		    // Initialize combined image
		    BufferedImage combined = new BufferedImage(qrImage.getHeight(), qrImage.getWidth(), BufferedImage.TYPE_INT_ARGB);
		    Graphics2D g = (Graphics2D) combined.getGraphics();

		    // Write QR code to new image at position 0/0
		    g.drawImage(qrImage, 0, 0, null);
		    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

		    // Write logo into combine image at position (deltaWidth / 2) and
		    // (deltaHeight / 2). Background: Left/Right and Top/Bottom must be
		    // the same space for the logo to be centered
		    g.drawImage(logoImage, (int) Math.round(deltaWidth / 2), (int) Math.round(deltaHeight / 2), null);

		    // Write combined image as PNG to OutputStream
		    ImageIO.write(combined, "png", baos);
		    byte[] bytes = baos.toByteArray();

		    String base64bytes = Base64.getEncoder().encodeToString(bytes);
		    String base64 = "data:image/png;base64," + base64bytes;
		    
		    String uuid = UUID.randomUUID().toString();
		    String path = qrcode.getQrcodePath() + uuid + ".png";
		    
		    ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
		    BufferedImage bufferedImage  = ImageIO.read(bais);
		    
		    log.info("Generated QRCode in path :: " + path);
		    ImageIO.write(bufferedImage, "png", new File(path));
		    
		    log.info(base64);
		    return new String[] { uuid, base64 };
		} catch (WriterException e) {
		    log.error("WriterException occured", e);
		} catch (IOException e) {
		    log.error("IOException occured", e);
		}
		return null;
	}
	
	public static BufferedImage resize(final URL url, final Dimension size) throws IOException{
	    final BufferedImage image = ImageIO.read(url);
	    if (size == null) {
	    	return image;
	    }
	    final BufferedImage resized = new BufferedImage(size.getWidth(), size.getHeight(), BufferedImage.TYPE_INT_ARGB);
	    final Graphics2D g = resized.createGraphics();
	    g.drawImage(image, 0, 0, size.getWidth(), size.getHeight(), null);
	    g.dispose();
	    return resized;
	}

}
