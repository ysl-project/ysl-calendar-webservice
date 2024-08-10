package com.ysl.calendar.user.controller;

import com.ysl.calendar.domain.users.CalendarUser;
import com.ysl.calendar.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@RestController()
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /* 인증 이메일 전송 메소드 */
    @PostMapping ("/check-email")
    public ResponseEntity sendEmailToVerification(@RequestParam("email") String email) throws Exception {
        log.debug("== 인증 이메일 전송 로직 ==");
        log.debug("이메일 [" + email + "]");
        userService.sendCodeToEmail(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/check-email")
    public boolean verificationEmail(@RequestParam("email") String email, @RequestParam("code") String code) throws Exception {
        log.debug("== 이메일 인증코드 검증 로직 ==");
        return userService.verifiedCode(email, code);
    }

    /*회원가입 */
    @PostMapping("/add")
    public CalendarUser addItem(@ModelAttribute CalendarUser calendarUser, RedirectAttributes redirectAttributes) {
        return userService.save(calendarUser);
    }

    /* 로그인 */
    @PostMapping("/login")
    public String login(@ModelAttribute CalendarUser calendarUser, RedirectAttributes redirectAttributes) {
        boolean loginSucc = userService.login(calendarUser);
        redirectAttributes.addAttribute("userId", calendarUser.getId());
        redirectAttributes.addAttribute("status", loginSucc);
        return redirectAttributes.toString();
    }

    /* 닉네임 체크 */
    @GetMapping("/check-nickname")
    public String checkNickname(@RequestParam("nickname") String nickname, RedirectAttributes redirectAttributes){
        boolean result = userService.checkNickname(nickname);
        log.debug("조회 문자열 [" + nickname + "] 결과 [" + result + "]");
        redirectAttributes.addAttribute("result", result);
        return redirectAttributes.toString();
    }
    /* 회원정보 조회 */
    @GetMapping("/{id}")
    public CalendarUser getUser(@PathVariable("id") String id) {
        return userService.getUserById(id);
    }

    /* 회원정보 수정 */
    @PostMapping("/{id}")
    public void updateUser(@PathVariable("id") String id, @ModelAttribute CalendarUser calendarUser) {
        userService.updateUser(id, calendarUser);
    }

}
