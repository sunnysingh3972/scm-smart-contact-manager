package com.smartcontactupgrade.smartcontact.services.servicesimpl;

import java.io.IOException;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.smartcontactupgrade.smartcontact.helper.AppConstraints;
import com.smartcontactupgrade.smartcontact.services.ImageService;

@Service
public class ImageServiceImpl implements ImageService {

    private Cloudinary cloudinary;

    @Override
    public String uploadImage(MultipartFile contactImage) {
        String filename = UUID.randomUUID().toString();
        try {
            byte[] data = new byte[contactImage.getInputStream().available()];
            contactImage.getInputStream().read(data);
            cloudinary.uploader().upload(data, ObjectUtils.asMap("public_id", filename));
            return this.getUrlFromPublicId(filename);

        } catch (IOException e) {

            e.printStackTrace();
            return null;
        }

    }

    public ImageServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override
    public String getUrlFromPublicId(String publicId) {
        return cloudinary.url()
                .transformation(new Transformation<>().width(AppConstraints.CONTACT_IMAGE_WIDTH)
                        .height(AppConstraints.CONTACT_IMAGE_HEIGHT).crop(AppConstraints.CONTACT_IMAGE_CROP))
                .generate(publicId);
    }

}
