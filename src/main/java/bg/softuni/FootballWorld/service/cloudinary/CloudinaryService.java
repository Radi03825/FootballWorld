package bg.softuni.FootballWorld.service.cloudinary;

import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryService {

    private static final String TEMP_FILE = "temp-file";
    private static final String URL = "url";
    private static final String PUBLIC_ID = "public_id";

    private final Cloudinary cloudinary;

    public CloudinaryService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public CloudinaryImage upload(MultipartFile multipartFile) throws IOException {

        File tempFile = File.createTempFile(TEMP_FILE, multipartFile.getOriginalFilename());
        multipartFile.transferTo(tempFile);

        try {

            @SuppressWarnings("unchecked")
            Map<String, String> uploadResult = cloudinary.uploader().upload(tempFile, Map.of());

            String url = uploadResult.getOrDefault(URL, "default");
            String publicId = uploadResult.getOrDefault(PUBLIC_ID, "default");

            CloudinaryImage cloudinaryImage = new CloudinaryImage();
            cloudinaryImage.setUrl(url);
            cloudinaryImage.setPublicId(publicId);

            return cloudinaryImage;
        } finally {
            tempFile.delete();
        }
    }

    public boolean delete(String publicId) throws IOException {
       try {
           this.cloudinary.uploader()
                   .destroy(publicId, Map.of());
       } catch (IOException e) {
           return false;
       }
        return true;
    }
}
