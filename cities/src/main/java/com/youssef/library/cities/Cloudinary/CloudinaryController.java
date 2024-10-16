package com.youssef.library.cities.Cloudinary;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.youssef.library.cities.ExceptionHandlers.ServerErrorException;
import lombok.AllArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("cloudinary")
@AllArgsConstructor
@CrossOrigin("*")
public class CloudinaryController {

    private Cloudinary cloudinary;
    private Environment env;
    @GetMapping("/getSignature")
    public HashMap<String, Object> getSignature() {
        HashMap<String, Object> response = new HashMap<>();
        long timestamp = System.currentTimeMillis() / 1000;
        Map<String, Object> params = new HashMap<>();
        params.put("timestamp", timestamp);
        String signature = cloudinary.apiSignRequest(params , cloudinary.config.apiSecret);
        response.put("signature", signature);
        response.put("timestamp", timestamp);
        return response;
    }

    @PostMapping("/uploadFile")
    public ResponseEntity<HashMap<String,String>> uploadFile(@RequestParam("file") MultipartFile file) {
        HashMap<String,String> response = new HashMap<>();
        try{
            Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap(
                            "resource_type", "raw" ,
                            "public_id", file.getOriginalFilename()
                    ));
            response.put("secure_url",(String)uploadResult.get("url"));
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }catch (IOException e){
            throw new ServerErrorException("Internal server error , cant upload the file");
        }

    }


}
