package todoapp.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import todoapp.core.user.application.ProfilePictureChanger;
import todoapp.core.user.domain.ProfilePicture;
import todoapp.core.user.domain.ProfilePictureStorage;
import todoapp.core.user.domain.User;
import todoapp.security.UserSession;
import todoapp.security.UserSessionRepository;
import todoapp.web.model.UserProfile;

import javax.annotation.security.RolesAllowed;
import java.net.URI;

@RolesAllowed({"ROLE_USER"})
@RestController
public class UserRestController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private ProfilePictureChanger profilePictureChanger;
    private ProfilePictureStorage profilePictureStorage;
    private UserSessionRepository sessionRepository;

    public UserRestController(ProfilePictureChanger profilePictureChanger, ProfilePictureStorage profilePictureStorage, UserSessionRepository sessionRepository) {
        this.profilePictureChanger = profilePictureChanger;
        this.profilePictureStorage = profilePictureStorage;
        this.sessionRepository = sessionRepository;
    }

    @GetMapping("api/user/profile")
    public UserProfile userProfile(UserSession userSession) {
        return new UserProfile(userSession.getUser());
    }

    @PostMapping("/api/user/profile-picture")
    public UserProfile changeProfilePicture(MultipartFile profilePicture, UserSession userSession) {
        log.debug("profilePicture: {}", profilePicture);

        //MultipartFile은 resource형태로 변경 가능
        URI profilePictureUri = profilePictureStorage.save(profilePicture.getResource());
        User changedUser = profilePictureChanger.change(userSession.getName(), new ProfilePicture(profilePictureUri));

        userSession = new UserSession(changedUser);
        sessionRepository.set(userSession);

        return new UserProfile(userSession.getUser());
    }

}
