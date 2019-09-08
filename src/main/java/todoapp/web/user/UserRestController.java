package todoapp.web.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import todoapp.security.UserSession;
import todoapp.security.UserSessionRepository;
import todoapp.web.model.UserProfile;

import java.util.Objects;

@RestController
public class UserRestController {

    private UserSessionRepository sessionRepository;

    public UserRestController(UserSessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @GetMapping("api/user/profile")
    public ResponseEntity<UserProfile> userProfile() {
        UserSession userSession = sessionRepository.get();

        if(Objects.nonNull(userSession)) {
//            return new UserProfile(userSession.getUser());
            return ResponseEntity.ok(new UserProfile(userSession.getUser()));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();  //로그인된 사용자의 프로필
    }

}
