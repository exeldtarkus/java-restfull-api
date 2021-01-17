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
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.RenderingHints;
import java.text.SimpleDateFormat;
import java.util.Locale;
import co.id.adira.moservice.contentservice.model.content.Voucher;

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

	public static String[] generateQRCodeWithLogo(QRCode qrcode, Voucher voucher) {

		Map<EncodeHintType, ErrorCorrectionLevel> hints = new HashMap<EncodeHintType, ErrorCorrectionLevel>();
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);

		QRCodeWriter writer = new QRCodeWriter();
		BitMatrix bitMatrix = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", new Locale("id", "ID"));
		String dateUntil = sdf.format(voucher.getPromo().getAvailableUntil());
		String bengkelName = voucher.getBengkel_name();

		try {
			// Create a qr code with the url as content and a size of 250x250 px
			bitMatrix = writer.encode(qrcode.getData(), BarcodeFormat.QR_CODE, 300, 300, hints);

			MatrixToImageConfig config = new MatrixToImageConfig(MatrixToImageConfig.BLACK, MatrixToImageConfig.WHITE);

			// Load QR image
			BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(bitMatrix, config);

			// Load logo image
			// BufferedImage logoImage = resize(new URL(MOSERVICE_LOGO), new Dimension(50,
			// 50));
			BufferedImage logoImage = resize(new URL(MOSERVICE_LOGO), null);

			// // Calculate the delta height and width between QR code and logo
			// int canvasWidth =qrImage.getWidth();
			// int canvasHeight= qrImage.getHeight()*2;
			// int deltaHeight = canvasHeight - logoImage.getHeight();
			// int deltaWidth = qrImage.getWidth() - logoImage.getWidth();
			// //calculate height of QR code and the texts.
			// int qrY = (int) Math.round(canvasHeight*0.25);
			// int bottomText = (int) Math.round(canvasHeight*0.75)+30;
			// int centerXTitle = (int) Math.round(deltaWidth/4);

			// Calculate the delta height and width between QR code and logo
			int canvasWidth = qrImage.getWidth();
			int canvasHeight = (int) Math.round(qrImage.getHeight() * 1.5);
			int deltaHeight = canvasHeight - logoImage.getHeight();
			int deltaWidth = qrImage.getWidth() - logoImage.getWidth();

			// calculate height of QR code and the texts.
			int qrY = (int) Math.round(canvasHeight * 0.17);
			int bottomTextY = (int) Math.round(canvasHeight * 0.88); // 8/9 of 450 = 400
			int centerXTitle = 15;
			int titleY = (int) Math.round(canvasHeight * 0.11); // 1/9 of 450 = 50
			int dateUntilY = (int) Math.round(canvasHeight * 0.17); // 1/6 of 450 = 75

			// Initialize combined image
			BufferedImage combined = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = (Graphics2D) combined.getGraphics();

			// Set font
			Font font1 = new Font("DM Sans", Font.PLAIN, 20);
			Font font3 = new Font("Poppins", Font.BOLD, 20);
			g.setFont(font1);

			// RenderingHints to make text smoother.
			g.setRenderingHint(RenderingHints.KEY_ALPHA_INTERPOLATION,
			RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setRenderingHint(RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
			g.setRenderingHint(RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
			g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

			// Fill Background
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, canvasWidth, canvasHeight);
			g.setColor(Color.BLACK);

			// Write QR code to new image at position 0/0
			g.drawImage(qrImage, 0, qrY, null);
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

			// Write logo into combine image at position (deltaWidth / 2) and
			// (deltaHeight / 2). Background: Left/Right and Top/Bottom must be
			// the same space for the logo to be centered
			g.drawImage(logoImage, (int) Math.round(deltaWidth / 2), (int) Math.round(deltaHeight / 2), null);

			// Check String

			// Load String
			g.drawString("Gunakan sebelum", centerXTitle, titleY);
			g.drawString(dateUntil, centerXTitle, dateUntilY);
			g.setFont(font3);
			drawString(g, bengkelName, centerXTitle, bottomTextY, canvasWidth - centerXTitle);

			// Write combined image as PNG to OutputStream
			ImageIO.write(combined, "png", baos);
			byte[] bytes = baos.toByteArray();

			String base64bytes = Base64.getEncoder().encodeToString(bytes);
			String base64 = "data:image/png;base64," + base64bytes;

			String uuid = UUID.randomUUID().toString();
			String path = qrcode.getQrcodePath() + uuid + ".png";

			ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
			BufferedImage bufferedImage = ImageIO.read(bais);

			log.info("Generated QRCode in path :: " + path);
			ImageIO.write(bufferedImage, "png", new File(path));

			return new String[] { uuid, base64 };
		} catch (WriterException e) {
			log.error("WriterException occured", e);
		} catch (IOException e) {
			log.error("IOException occured", e);
		}
		return null;
	}

	public static BufferedImage resize(final URL url, final Dimension size) throws IOException {
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

	// method to split string if longer than width
	// source:
	// https://stackoverflow.com/questions/18800717/convert-text-content-to-image/18800845#18800845
	public static void drawString(Graphics2D g, String s, int x, int y, int width) {
		// FontMetrics gives us information about the width,
		// height, etc. of the current Graphics object's Font.
		FontMetrics fm = g.getFontMetrics();

		int lineHeight = fm.getHeight();

		int curX = x;
		int curY = y;

		String[] words = s.split(" ");

		for (String word : words) {
			// Find out thw width of the word.
			int wordWidth = fm.stringWidth(word + " ");

			// If text exceeds the width, then move to next line.
			if (curX + wordWidth >= x + width) {
				curY += lineHeight;
				curX = x;
			}

			g.drawString(word, curX, curY);

			// Move over to the right for next word.
			curX += wordWidth;
		}
	}

}
