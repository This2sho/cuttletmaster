package PorkCutlet.master.controller;

import PorkCutlet.master.domain.User;
import PorkCutlet.master.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@Slf4j
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/auth/join")
    public String joinForm(UserDto userDto) {
        return "join/joinForm";
    }

    @PostMapping("/auth/join")
    public String join(@Valid UserDto userDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "join/joinForm";
        }
        userService.join(new User(userDto.getLoginId(), userDto.getPassword(), userDto.getNickName()));
        return "redirect:/";
    }




}
