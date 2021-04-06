package saturn.backend.career;

import saturn.backend.core.AuthenticationService;
import saturn.backend.exception.NoSuchDocument;
import saturn.backend.payload.response.ResponseBody;
import saturn.backend.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("v1/saturn")
@RestController
@CrossOrigin(origins="*", maxAge = 3600)
public class CareerController {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    CareerService careerService;

    @Autowired
    UserService userService;

    @PostMapping("/careers")
    @PreAuthorize("hasRole('ROLE_CAREER_WRITE')")
    public ResponseEntity<?> createCareer(
            @RequestBody Career newCareer,
            Authentication authentication
    ){
        String userId = authenticationService.parseAuthenticationGetId(authentication);
        String userName = userService.findUserName(userId);
        Career savedCareer = careerService.createCareer(newCareer, userName);
        ResponseBody responseBody = new ResponseBody(savedCareer,
                "create career successfully", null);
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/careers/{id}")
    @PreAuthorize("hasRole('ROLE_CAREER_READ')")
    public ResponseEntity<?> retrieveCareer(
            @PathVariable("id") String careerId
    ){
        Career retrievedCareer = careerService.retrieveCareer(careerId);
        if(retrievedCareer == null){
            return NoSuchDocument.ok("shop");
        }
        ResponseBody responseBody
                = new ResponseBody(retrievedCareer,
                String.format("retrieve career id: %s", careerId),
                null);
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/careers")
//    @PreAuthorize("hasRole('ROLE_CAREER_READ')")
    public ResponseEntity<?> listCareer(
            @RequestHeader("Authorization") String jwt
    ){
        List<Career> listCareer = careerService.listCareer(jwt);
        ResponseBody responseBody
                = new ResponseBody(listCareer, "list career", null);
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/careers/{careerId}")
    @PreAuthorize("hasRole('ROLE_CAREER_WRITE')")
    public ResponseEntity<?> updateCareer(
            @PathVariable("careerId") String careerId,
            @RequestBody Career career,
            Authentication authentication
    ){
        String userId = authenticationService.parseAuthenticationGetId(authentication);
        String userName = userService.findUserName(userId);
        career.setId(careerId);
        Career updatedCareer = careerService.updateCareer(career, userName);
        if(updatedCareer == null){
            return NoSuchDocument.ok("no such career, can't be updated");
        }
        ResponseBody responseBody = new ResponseBody(updatedCareer, "update it", null);
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/careers/deactivate/{careerId}")
    @PreAuthorize("hasRole('ROLE_CAREER_MANAGE')")
    public ResponseEntity<?> deactivateCareer(
            @PathVariable("careerId") String careerId
    ){
        Career deActiveCareer = careerService.deactivateCareer(careerId);
        if(deActiveCareer == null){
            return NoSuchDocument.ok("can't deactivate this career, no such career");
        }
        ResponseBody responseBody = new ResponseBody(deActiveCareer, "deactivate it", null);
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/careers/activate/{careerId}")
    @PreAuthorize("hasRole('ROLE_CAREER_MANAGE')")
    public ResponseEntity<?> activateCareer(
            @PathVariable("careerId") String careerId
    ){
        Career deActiveCareer = careerService.activateCareer(careerId);
        if(deActiveCareer == null){
            return NoSuchDocument.ok("can't activate this career, no such career");
        }
        ResponseBody responseBody = new ResponseBody(deActiveCareer, "activate it", null);
        return ResponseEntity.ok(responseBody);
    }

    @PostMapping("/careers/apply/{careerId}")
    @PreAuthorize("hasRole('ROLE_CAREER_MANAGE')")
    public ResponseEntity<?> applyCareer(
            @PathVariable("careerId") String careerId
    ){
        Career deActiveCareer = careerService.appliedCareer(careerId);
        if(deActiveCareer == null){
            return NoSuchDocument.ok("can't apply for this career, no such career");
        }
        ResponseBody responseBody = new ResponseBody(deActiveCareer, "applied for it", null);
        return ResponseEntity.ok(responseBody);
    }

}
