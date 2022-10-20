package PorkCutlet.master.controller;

import PorkCutlet.master.UserPatternUtil;
import PorkCutlet.master.controller.dto.ThumbNailReviewDto;
import PorkCutlet.master.controller.dto.UserInfoDto;
import PorkCutlet.master.controller.dto.UserUpdateDto;
import PorkCutlet.master.controller.auth.Login;
import PorkCutlet.master.domain.User;
import PorkCutlet.master.service.LikeService;
import PorkCutlet.master.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final LikeService likeService;
    private final UserPatternUtil userPatternUtil;

    @GetMapping("/{userId}")
    public String myPage(@Login UserInfoDto user, @PathVariable Long userId, UserUpdateDto userUpdateDto, Model model) {
        List<ThumbNailReviewDto> likeList = likeService.getLikeList(user.getId())
                .stream().map(ThumbNailReviewDto::from).collect(Collectors.toList());
        model.addAttribute("userUpdateDto", userUpdateDto);
        model.addAttribute("likeList", likeList);
        return "myPage";
    }

    @PutMapping("/{userId}")
    public String updateUser(@Login UserInfoDto user, @PathVariable Long userId, @Valid UserUpdateDto userUpdateDto,
                             BindingResult bindingResult, HttpServletRequest request) {
        User findUser = userService.findById(user.getId()).orElseThrow();
        String currentPassword = findUser.getPassword();
        String currentNickName = findUser.getNickName();

        validatePassword(userUpdateDto.getPassword(), bindingResult, currentPassword);
        currentPassword = checkPassword(userUpdateDto, bindingResult, currentPassword);
        currentNickName = checkNickName(userUpdateDto, bindingResult, currentNickName);
        validateNoChange(bindingResult, findUser, currentPassword, currentNickName);

        if (bindingResult.hasErrors()) {
            return "myPage";
        }

        userService.updateUser(findUser, currentPassword, currentNickName);
        logout(request);
        return "redirect:/";
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@Login UserInfoDto user, @PathVariable Long userId, @Valid UserUpdateDto userUpdateDto,
                             BindingResult bindingResult, HttpServletRequest request) {
        User findUser = userService.findById(user.getId()).orElseThrow();
        String currentPassword = findUser.getPassword();
        validatePassword(userUpdateDto.getPassword(), bindingResult, currentPassword);

        if (bindingResult.hasErrors()) {
            return "myPage";
        }

        userService.deleteUser(findUser);
        logout(request);
        return "redirect:/";
    }

    @DeleteMapping("/{userId}/likes/reviews/{reviewId}")
    public ResponseEntity deleteLikeReview(@Login UserInfoDto user, @PathVariable Long userId, @PathVariable Long reviewId) {
        likeService.deleteLike(user.getId(), reviewId);
        return new ResponseEntity(HttpStatus.OK);
    }

    private void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    private void validatePassword(String password, BindingResult bindingResult, String currentPassword) {
        if(!currentPassword.equals(password)){
            bindingResult.reject("passwordNotEquals", "현재 비밀번호가 일치하지 않습니다.");
        }
    }

    private void validateNoChange(BindingResult bindingResult, User findUser, String currentPassword, String currentNickName) {
        if (currentNickName.equals(findUser.getNickName()) && currentPassword.equals(findUser.getPassword())) {
            bindingResult.reject("noChange", "새로운 비밀번호 또는 닉네임을 입력해주세요.");
        }
    }

    private String checkNickName(UserUpdateDto userUpdateDto, BindingResult bindingResult, String currentNickName) {
        if (validateNickName(userUpdateDto.getNickName(), bindingResult)) {
            currentNickName = userUpdateDto.getNickName();
        }
        return currentNickName;
    }

    private String checkPassword(UserUpdateDto userUpdateDto, BindingResult bindingResult, String currentPassword) {
        if(validateNewPassword(userUpdateDto.getNewPassword(), userUpdateDto.getNewPasswordCheck(), bindingResult)){
            currentPassword = userUpdateDto.getNewPassword();
        }
        return currentPassword;
    }

    private boolean validateNewPassword(String newPassword, String newPasswordCheck, BindingResult bindingResult) {
        if(newPassword.isEmpty()) return false;

        if(!newPassword.equals(newPasswordCheck)){
            bindingResult.reject("newPasswordNotEquals", "비밀번호가 서로 일치하지 않습니다.");
            return false;
        }

        if(userPatternUtil.passwordMatches(newPassword, bindingResult)) return true;
        return false;
    }

    private boolean validateNickName(String newNickName, BindingResult bindingResult) {
        if(newNickName.isEmpty()) return false;
        if(userPatternUtil.nickNameMatches(newNickName, bindingResult)) return true;
        return false;
    }
}
