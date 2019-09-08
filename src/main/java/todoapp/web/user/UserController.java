package todoapp.web.user;


import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import todoapp.core.user.domain.ProfilePictureStorage;
import todoapp.security.UserSession;

import javax.annotation.security.RolesAllowed;

@Controller
public class UserController {

    private ProfilePictureStorage profilePictureStorage;

    public UserController(ProfilePictureStorage profilePictureStorage) {
        this.profilePictureStorage = profilePictureStorage;
    }

    @RolesAllowed({"ROLE_USER"})
    @RequestMapping("/user/profile-picture")
    public @ResponseBody Resource profilePicture(UserSession userSession) {
        return profilePictureStorage.load(userSession.getUser().getProfilePicture().getUri());
    }
}
