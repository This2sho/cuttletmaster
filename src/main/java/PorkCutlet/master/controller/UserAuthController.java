package PorkCutlet.master.controller;

import PorkCutlet.master.controller.dto.UserAuthDto;
import PorkCutlet.master.controller.dto.UserInfoDto;
import PorkCutlet.master.domain.User;
import PorkCutlet.master.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/auth")
public class UserAuthController {
    private final UserService userService;
    @GetMapping("/join")
    public String joinForm(UserAuthDto userAuthDto) {
        return "auth/joinForm";
    }

    @PostMapping("/join")
    public String join(@Valid UserAuthDto userAuthDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "auth/joinForm";
        }
        userService.join(new User(userAuthDto.getLoginId(), userAuthDto.getPassword(), userAuthDto.getNickName()));
        return "redirect:/";
    }

    @GetMapping("/login")
    public String loginForm(UserAuthDto userAuthDto) {
        return "auth/loginForm";
    }

    @PostMapping("/login")
    public String login(@Valid UserAuthDto userAuthDto, BindingResult bindingResult,
                        HttpServletRequest request) {
        if (bindingResult.hasErrors() && errorIsNotNickName(bindingResult)) {
            return "auth/loginForm";
        }

        User loginUser = userService.login(userAuthDto.getLoginId(), userAuthDto.getPassword());

        if (loginUser == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "auth/loginForm";
        }

        HttpSession session = request.getSession();

        session.setAttribute(SessionConst.LOGIN_USER, UserInfoDto.from(loginUser));
        return "redirect:/";
    }

    private boolean errorIsNotNickName(BindingResult bindingResult) {
        return !(bindingResult.getAllErrors().size() == 1 && bindingResult.getFieldError().getField().equals("nickName"));
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
