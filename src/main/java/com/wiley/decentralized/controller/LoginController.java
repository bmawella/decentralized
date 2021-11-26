package com.wiley.decentralized.controller;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.util.Map;

import com.wiley.decentralized.model.User;
import com.wiley.decentralized.service.Connector;
import com.wiley.decentralized.service.UserService;
import org.hyperledger.fabric.gateway.Contract;
import org.hyperledger.fabric.gateway.Gateway;
import org.hyperledger.fabric.gateway.Network;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@Controller
public class LoginController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String login(Map<String, Object> model, HttpServletRequest request) {
        model.put("message", "");
        return "login";
    }

    @RequestMapping(value = "/login-action", method = RequestMethod.POST)
    public String loginAction(Map<String, Object> model, HttpServletRequest request,
                              HttpServletResponse response, HttpSession httpSession) {
        final String username = request.getParameter("username");
        final String password = request.getParameter("password");
        User user;
        try {
            final Gateway gateway = Connector.connect();
            // get the network and contract
            Network network = gateway.getNetwork("mychannel");
            Contract contract = network.getContract("basic");
            byte[] result = contract.evaluateTransaction("authenticate", username,password);
            user = (User)getObject(result);


        } catch (Exception e) {
            e.printStackTrace();
        }

        httpSession.setAttribute("ledgerId", "buddhika");
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

        final String leadgerId = request.getParameter("ledgerId");

        byte[] result = new byte[0];

        // connect to the network and invoke the smart contract
        try  {
            final Gateway gateway = Connector.connect();
            Network network = gateway.getNetwork("mychannel");
            Contract contract = network.getContract("basic");

            result = contract.submitTransaction("logout", leadgerId);

        }catch(Exception e){
            System.err.println(e);
        }


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

    private static Object getObject(byte[] byteArr) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(byteArr);
        ObjectInput in = new ObjectInputStream(bis);
        return in.readObject();
    }
}