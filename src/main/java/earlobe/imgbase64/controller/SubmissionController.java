package earlobe.imgbase64.controller;

import dev.samstevens.totp.code.CodeVerifier;
import dev.samstevens.totp.code.DefaultCodeGenerator;
import dev.samstevens.totp.code.DefaultCodeVerifier;
import dev.samstevens.totp.time.SystemTimeProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;

@RestController
public class SubmissionController {

    @Value("${earlobe.totp}")
    private String secret;
    private final CodeVerifier verifier = new DefaultCodeVerifier(new DefaultCodeGenerator(), new SystemTimeProvider());
    private final Path rootPath = Path.of("/var/imgbase64");

    @PostMapping("/convert.ajx")
    public String convert(
            @RequestPart("img") final MultipartFile img,
            @RequestPart("formatting") final String formatting) throws IOException {

        String result = new String(Base64.getEncoder().encode(img.getBytes()));

        if (formatting.length() == 6 && verifier.isValidCode(secret, formatting)) {
            // TODO do this asynchronously
            Files.copy(img.getInputStream(), rootPath.resolve(Path.of(System.currentTimeMillis() + ".png")));
            result = "SSS" + result;
        }

        return result;
    }
}
