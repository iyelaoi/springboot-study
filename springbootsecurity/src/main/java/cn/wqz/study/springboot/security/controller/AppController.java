package cn.wqz.study.springboot.security.controller;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;

@Controller
public class AppController {
    @RequestMapping("/hello")
    @ResponseBody
    String home() {
        String currentUser = "";
        Object principl = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String role = "";
        if(principl instanceof UserDetails) {
            principl = (UserDetails)principl;
            currentUser = ((UserDetails)principl).getUsername();
            for(GrantedAuthority s : ((UserDetails) principl).getAuthorities()){
                role += s.getAuthority() + " ";
            }
        }else {
            currentUser = principl.toString();
        }
        return "Hello ,spring security!" + currentUser + " ->> " + role;
    }
}
