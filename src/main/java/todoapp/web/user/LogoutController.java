package todoapp.web.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import todoapp.security.UserSessionRepository;

@Controller
public class LogoutController {

    private UserSessionRepository sessionRepository;

    public LogoutController(UserSessionRepository sessionRepository) {
        this.sessionRepository = sessionRepository;
    }

    @GetMapping("/logout")
    public String logout() {
        sessionRepository.clear();
        return "redirect:/todos";
    }
}
