package saturn.backend.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import saturn.backend.core.AuthenticationService;
import saturn.backend.payload.response.ResponseBody;

@RequestMapping("v1/saturn")
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ManageController {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    ManageService manageService;

    @GetMapping("manage/countCareer")
    public ResponseEntity<?> countCareer(){
        Manage manage = manageService.retrieveManage("career");
        ResponseBody responseBody
                = new ResponseBody(manage.getAmountOfEntries(), "count of careers", null);
        return ResponseEntity.ok(responseBody);
    }
}
