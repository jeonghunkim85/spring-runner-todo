package todoapp.web.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import todoapp.security.UserSession;
import todoapp.web.model.UserProfile;

import javax.annotation.security.RolesAllowed;

@RestController
public class UserRestController {

    @RolesAllowed({"ROLE_USER"})
    @GetMapping("api/user/profile")
    public UserProfile userProfile(UserSession userSession) {
        return new UserProfile(userSession.getUser());
    }

}
