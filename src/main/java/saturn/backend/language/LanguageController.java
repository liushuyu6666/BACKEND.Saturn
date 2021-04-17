package saturn.backend.language;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import saturn.backend.core.AuthenticationService;
import saturn.backend.payload.response.ResponseBody;
import saturn.backend.user.UserService;

@RequestMapping("v1/saturn")
@RestController
@CrossOrigin(origins="*", maxAge = 3600)
public class LanguageController {

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    LanguageService languageService;

    @Autowired
    UserService userService;

    @PostMapping("/languages")
    @PreAuthorize("hasRole('ROLE_LANGUAGE_WRITE')")
    public ResponseEntity<?> createLanguage(
            @RequestBody Language newLanguage,
            Authentication authentication
    ){
        String userId = authenticationService.parseAuthenticationGetId(authentication);
        Language savedLanguage = languageService.createLanguage(newLanguage, userId);
        ResponseBody responseBody = new ResponseBody(savedLanguage,
                "create language successfully", null);
        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/languages/{id}")
    @PreAuthorize("hasRole('ROLE_LANGUAGE_READ')")
    public ResponseEntity<?> retrieveLanguage(
            @PathVariable("id") String languageId,
            Authentication authentication
    ){
        String userId = authenticationService.parseAuthenticationGetId(authentication);
        Language retrievedLanguage = languageService.retrieveLanguage(languageId, userId);
        ResponseBody responseBody = new ResponseBody(retrievedLanguage,
                "retrieve language successfully", null);
        return ResponseEntity.ok(responseBody);
    }
}
