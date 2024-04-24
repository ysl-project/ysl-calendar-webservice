package com.ysl.calendar.user.controller;

import com.ysl.calendar.domain.users.CalendarUserRepository;
import com.ysl.calendar.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController()
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    CalendarUserRepository calUser = new CalendarUserRepository();

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



}
