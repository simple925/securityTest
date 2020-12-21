package com.securityTest.controller;

import com.securityTest.dto.UserInfoDto;
import com.securityTest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;

    @PostMapping("/user")
    public String signup(UserInfoDto infoDto) { // 회원추가
        userService.save(infoDto);
        return "redirect:/login";
    }

    @GetMapping(value = "/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response){
        new SecurityContextLogoutHandler().logout(request,response, SecurityContextHolder.getContext().getAuthentication());;
        return "redirect:/login";
    }
    /*
    기본으로 제공해주는 SecurityContextLogoutHandler() 의 logout() 을 사용해서 로그아웃 처리를 해주었습니다.
    이제 로그아웃까지 구현했으니, 나머지를 구현해보도록 하겠습니다.
     */
}
