package it.uniroma3.siw.r3tour.spring.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.UUID;


/**
 * Questa classe viene utilizzata per gestire l'upload su un bucket S3.
 */
@Service
public class AWSS3Service implements FileService{

    /**
     * amazonS3Client rappresenta l'oggetto con le nostre chiavi per l'accesso al bucket.
     */
    @Autowired
    protected AmazonS3Client amazonS3Client;

    @Override
    public String uploadFile(MultipartFile multipartFile){
        String extension = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
        String key = UUID.randomUUID() + "." + extension;

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());

        try {
            amazonS3Client.putObject("r3tour-bucket", key, multipartFile.getInputStream(), metadata);
        } catch (IOException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error while uploading the file");
        }

        amazonS3Client.setObjectAcl("r3tour-bucket", key, CannedAccessControlList.PublicRead);

        return amazonS3Client.getResourceUrl("r3tour-bucket", key);
    }
}
