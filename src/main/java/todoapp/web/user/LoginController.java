package todoapp.web.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import todoapp.core.user.application.UserJoinder;
import todoapp.core.user.application.UserPasswordVerifier;
import todoapp.core.user.domain.User;
import todoapp.core.user.domain.UserEntityNotFoundException;
import todoapp.core.user.domain.UserPasswordNotMatchedException;
import todoapp.security.UserSession;
import todoapp.security.UserSessionRepository;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Controller
@RequestMapping("/login")
public class LoginController {

    private Logger log = LoggerFactory.getLogger(LoginController.class);

    private UserPasswordVerifier verifier;
    private UserJoinder joinder;
    private UserSessionRepository sessionRepository;

    public LoginController(UserPasswordVerifier verifier, UserJoinder joinder, UserSessionRepository sessionRepository) {
        this.verifier = verifier;
        this.joinder = joinder;
        this.sessionRepository = sessionRepository;
    }

    @GetMapping
    public void loginForm() {
    }

    @PostMapping
    public String loginProcess(@Valid LoginCommand command, BindingResult bindingResult, Model model) {
        log.debug("username: {}, password: {}", command.getUsername(), command.getPassword());

        if(bindingResult.hasErrors()) {
            model.addAttribute("bindingResult", bindingResult);
            model.addAttribute("message", "사용자 입력 값이 올바르지 않습니다.");
            return "login";
        }

          // 사용자 저장소에 사용자가 있을 경우: 비밀번호가 일치하는지 확인
          // 비밀번호가 일치하면: /todos 리다이렉트
          // 비밀번호가 비일치하면: login 페이지로 돌려보내고, 오류 메세지 노출

        try {
            User logined = verifier.verify(command.getUsername(), command.getPassword());
            sessionRepository.set(new UserSession(logined));
        }catch (UserEntityNotFoundException error) {
            //사용자 저장소에 사용자가 없을 경우: 신규 사용자로 가입 처리
            User logined = joinder.join(command.getUsername(), command.getPassword());
            sessionRepository.set(new UserSession(logined));
        }

        return "redirect:/todos";
    }

    //LoginController 안에서만 작동함
    @ExceptionHandler(UserPasswordNotMatchedException.class)
    public String handlerUserPasswordNotMatchedException(UserPasswordNotMatchedException error, Model model) {
        model.addAttribute("message", error.getMessage());
        return "login";
    }





    static class LoginCommand {

        @Size(min = 4, max = 20)
        private String username;

        @NotBlank
        private String password;

        public String getUsername() {
            return username;
        }
        public void setUsername(String username) {
            this.username = username;
        }
        public String getPassword() {
            return password;
        }
        public void setPassword(String password) {
            this.password = password;
        }
    }

}
