package com.smartcontactupgrade.smartcontact.services;

import org.springframework.web.multipart.MultipartFile;

public interface ImageService {
     String uploadImage(MultipartFile contactImage) ; 
     String getUrlFromPublicId(String publicId);
}
