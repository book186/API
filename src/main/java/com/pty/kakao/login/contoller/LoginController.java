package com.pty.kakao.login.contoller;

import java.util.HashMap;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pty.kakao.login.service.KaKaoAPI;

@Controller
public class LoginController {

	@Autowired
	private KaKaoAPI kakao;

	@RequestMapping(value = "/")
	public String index() {

		return "index";
	}

	@RequestMapping(value = "/login")
	public String login(@RequestParam("code") String code, HttpSession session) {
		String access_Token = kakao.getAccessToken(code);
		HashMap<String, Object> userInfo = kakao.getUserInfo(access_Token);

		System.out.println("login Controller : " + userInfo);

		if (userInfo.get("email") != null) {
			session.setAttribute("userId", userInfo.get("email"));
			session.setAttribute("access_Token", access_Token);
		}

		return "index";
	}

	@RequestMapping(value = "/logout")
	public String logout(HttpSession session) {
		kakao.kakaoLogout((String) session.getAttribute("access_Token"));

		session.removeAttribute("access_Token");
		session.removeAttribute("userId");

		return "index";
	}
}
