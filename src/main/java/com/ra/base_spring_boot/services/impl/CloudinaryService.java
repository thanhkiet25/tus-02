package com.ra.base_spring_boot.services.impl;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Service
public class CloudinaryService {
    @Autowired
    private Cloudinary cloudinary;

    public String upload(MultipartFile multipartFile){
        try{
            //lay ten file goc tu Multi bo duoi
            String originalFilename = multipartFile.getOriginalFilename();
            if(originalFilename != null && originalFilename.contains(".")){
                originalFilename = originalFilename.substring(0, originalFilename.lastIndexOf("."));
            }
            //lay timestamp
            long timestamp = System.currentTimeMillis()/1000;
            //them tham so upload
            Map<String , Object> map = ObjectUtils.asMap(
                    "public_id", originalFilename, //dat ten file
                    "timestamp" , timestamp //bat buoc co timestamp
            );
            //tao chu ki
            String signature = cloudinary.apiSignRequest(map, cloudinary.config.apiSecret);

            //them chu ki
            map.put("signature" , signature);
            map.put("api_key" , cloudinary.config.apiKey);

            Map uploadResult = cloudinary.uploader().upload(multipartFile.getBytes(), map);
            return uploadResult.get("secure_url").toString();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
