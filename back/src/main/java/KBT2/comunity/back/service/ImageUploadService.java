package KBT2.comunity.back.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;

@Service
public class ImageUploadService {
    @Value("${imgbb.api.key}")
    private String imgbbApiKey;

    public String uploadToImgbb(MultipartFile file) throws IOException {
        String url = "https://api.imgbb.com/1/upload?key=" + imgbbApiKey;

        String base64Image = Base64.getEncoder().encodeToString(file.getBytes());

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("image", base64Image);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(body, headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Map> response = restTemplate.postForEntity(url, requestEntity, Map.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            Map data = (Map) response.getBody().get("data");
            return (String) data.get("url");
        } else {
            throw new RuntimeException("imgbb 업로드 실패");
        }
    }
}
