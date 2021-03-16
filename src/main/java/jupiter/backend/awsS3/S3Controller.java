package jupiter.backend.awsS3;

import jupiter.backend.payload.response.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/v1/jupiter")
public class S3Controller {

    @Autowired
    private S3Service amazonClient;

//    @Autowired
//    BucketController(AmazonClient amazonClient) {
//        this.amazonClient = amazonClient;
//    }

    @PostMapping("/files")
    @PreAuthorize("hasRole('ROLE_OWNER')")
    public ResponseEntity<ResponseBody> createFile(@RequestParam(value = "file") MultipartFile file,
                                                   @RequestParam(value = "name") String name) {
        try{
            String fileUrl = amazonClient.createFile(file, name);
            ResponseBody responseBody =
                    new ResponseBody(fileUrl, "upload the file successfully", null);
            return ResponseEntity.ok(responseBody);
        }
        catch (Exception e){
            ResponseBody responseBody =
                    new ResponseBody(null, e.getMessage(), null);
            return ResponseEntity.ok(responseBody);
        }
    }

    @GetMapping("/files/{Id}")
    @PreAuthorize("hasRole('ROLE_OWNER')")
    public ResponseEntity<ResponseBody> retrieveFile(@PathVariable("Id") String fileId){
        try{
            String fileUrl = amazonClient.retrieveFile(fileId);
            ResponseBody responseBody =
                    new ResponseBody(fileUrl, "get url", null);
            return ResponseEntity.ok(responseBody);
        }
        catch (Exception e){
            ResponseBody responseBody =
                    new ResponseBody(null, e.getMessage(), e);
            return ResponseEntity.ok(responseBody);
        }
    }

    @GetMapping("/files")
    @PreAuthorize("hasRole('ROLE_OWNER')")
    public ResponseEntity<ResponseBody> listFiles(){
        try{
            List<String> filesUrl = amazonClient.listFiles();
            ResponseBody responseBody =
                    new ResponseBody(filesUrl, "get url list", null);
            return ResponseEntity.ok(responseBody);
        }
        catch (Exception e){
            ResponseBody responseBody =
                    new ResponseBody(null, e.getMessage(), e);
            return ResponseEntity.ok(responseBody);
        }
    }

    @PostMapping("/files/{Id}")
    @PreAuthorize("hasRole('ROLE_OWNER')")
    public ResponseEntity<ResponseBody> updateFile(@PathVariable("Id") String fileId,
                                                   @RequestBody MultipartFile file){
        try{
            String updatedUrl = amazonClient.updateFile(fileId, file);
            ResponseBody responseBody =
                    new ResponseBody(updatedUrl, "updated it", null);
            return ResponseEntity.ok(responseBody);
        }
        catch(Exception e){
            ResponseBody responseBody =
                    new ResponseBody(null, e.getMessage(), e);
            return ResponseEntity.ok(responseBody);
        }
    }

    @DeleteMapping("/files/{Id}")
    @PreAuthorize("hasRole('ROLE_OWNER')")
    public ResponseEntity<ResponseBody> deleteFile(@PathVariable("Id") String fileId) {
        try{
            String bucketWithId = amazonClient.deleteFile(fileId);
            ResponseBody responseBody =
                    new ResponseBody(bucketWithId, "deleted successfully", null);
            return ResponseEntity.ok(responseBody);
        }
        catch (Exception e){
            ResponseBody responseBody =
                    new ResponseBody(null, e.getMessage(), e);
            return ResponseEntity.ok(responseBody);
        }
    }
}