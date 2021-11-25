package com.wiley.decentralized.controller;


import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Controller
public class LoginController {


    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String login(Map<String, Object> model, HttpServletRequest request) {
        //final String name = request.getParameter("name");
        model.put("message", "");
        return "login";
    }

    @RequestMapping(value = "/login-action", method = RequestMethod.POST)
    public String loginAction(Map<String, Object> model, HttpServletRequest request,
                              HttpServletResponse response) {
        final String username = request.getParameter("username");
        final String password = request.getParameter("password");

        if (username.equals("buddhika") && password.equals("123")) {
            Cookie cookie = new Cookie("user-authenticated", "true");
            cookie.setMaxAge(-1);
            response.addCookie(cookie);
            return "redirect:login-success";
        } else {
            deleteCookie(request, response);
            model.put("message", "Invalid credentials.!");
            return "login";
        }


    }

    @RequestMapping(value = "/login-success", method = RequestMethod.GET)
    public String loginSuccess(Map<String, Object> model, HttpServletRequest request) {
        if (!isLoggedIn(request)) {
            model.put("message", "");
            return "redirect:login";
        }
        model.put("success", "Logged in successfully.!<br/> Welcome to home page.!");
        return "login-success";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
        model.put("message", "");

        deleteCookie(request, response);
        return "redirect:/";
    }

    private Boolean isLoggedIn(HttpServletRequest request) {
        String cookieValue = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user-authenticated")) {
                    cookieValue = cookie.getValue();
                }
            }
        }
        return cookieValue != null;
    }

    private void deleteCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if (cookies[i].getName().equals("user-authenticated")) {
                    cookies[i].setMaxAge(0);
                    cookies[i].setValue(null);
                    response.addCookie(cookies[i]);
                }
            }
        }
    }
}