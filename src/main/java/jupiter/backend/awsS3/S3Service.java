package jupiter.backend.awsS3;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.xspec.L;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class S3Service {

    private AmazonS3 s3client;

    @Value("${aws.S3.properties.endpointUrl}")
    private String endpointUrl;

    @Value("${aws.S3.properties.bucketName}")
    private String bucketName;

    @Value("${aws.S3.properties.accessKey}")
    private String accessKey;

    @Value("${aws.S3.properties.secretKey}")
    private String secretKey;

    @Value("${aws.S3.properties.region}")
    private String region;

    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.s3client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.fromName(region))
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .build();
    }

    private File convertMultiPartToFile(MultipartFile file) throws Exception {
        try{
            File convFile = new File(file.getOriginalFilename());
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();
            return convFile;
        }
        catch (Exception e){
            throw new Exception(e);
        }

    }

    private String generateFileName(String name) {
        return name.replace(" ", "_");
    }

    private void uploadFileTos3bucket(String fileName, File file) throws Exception{
        try{
            PutObjectRequest putObjectRequest =
                    new PutObjectRequest(bucketName, fileName, file).withCannedAcl(CannedAccessControlList.PublicRead);
            s3client.putObject(putObjectRequest);
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }




    public String createFile(MultipartFile multipartFile, String name) throws Exception{

        try {
            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(name);
            String fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
            uploadFileTos3bucket(fileName, file);
            file.delete();
            return fileUrl;
        } catch (Exception e) {
            throw new Exception(e);
        }
    }

    public String retrieveFile(String fileId) throws Exception{
        try{
            String fileName = s3client.getObject(this.bucketName, fileId).getKey();
            String fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
            return fileUrl;
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    public List<String> listFiles() throws Exception{
        try{
            List<String> listUrl = new ArrayList<>();
            ListObjectsV2Result listObjectsV2Result
                    = s3client.listObjectsV2(this.bucketName);
            for(S3ObjectSummary s3ObjectSummary : listObjectsV2Result.getObjectSummaries()){
                String fileName = s3ObjectSummary.getKey();
                listUrl.add(endpointUrl + "/" + bucketName + "/" + fileName);
            }
            return listUrl;
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    public String updateFile(String oldFileId, MultipartFile multipartFile) throws Exception{
        try{
            deleteFile(oldFileId);
            String newUrl = createFile(multipartFile, oldFileId);
            return newUrl;
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }

    public String deleteFile(String fileId) throws Exception{
//        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        try{
            DeleteObjectRequest deleteObjectRequest =
                    new DeleteObjectRequest(this.bucketName, fileId);
            s3client.deleteObject(deleteObjectRequest);
            return bucketName + '/' + fileId;
        }
        catch (Exception e){
            throw new Exception(e);
        }
    }
}