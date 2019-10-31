package com.songpeng.sparchetype.system.controller.client;

import com.songpeng.sparchetype.common.Result;
import com.songpeng.sparchetype.system.config.shiro.SpUsernamePasswordToken;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 *
 *
 * @author SongPeng
 * @date 2019/9/27 16:05
 */
@Controller("clientSysLoginController")
@Slf4j
public class SysLoginController {

	/**
	 * 首页默认跳转到博客主页
	 *
	 * @param model
	 * @return
	 */
	@GetMapping({"/", ""})
	public String welcomeUI(Model model) {
		return "redirect:/blog";
	}

	/**
	 * 登录页面
	 *
	 * @param model
	 * @return
	 */
	@GetMapping("/login-ui")
	public String loginUI(Model model) {
		return "login";
	}

	@PostMapping("/login")
	@ResponseBody
	public Result login(String username, String password, String captcha, String rememberMe, HttpServletRequest request) {
		// TODO loginType 字段用于后期拓展用
		UsernamePasswordToken token = new SpUsernamePasswordToken(username, password, "UserLogin");
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.login(token);
			return Result.success();
		} catch (AuthenticationException e) {
			log.error("用户或密码错误", e);
			return Result.failure("用户或密码错误");
		}
	}

	/**
	 * 404页面
	 *
	 * @param model
	 * @return
	 */
	@GetMapping("/404-ui")
	public String error404UI(Model model) {
		return "404";
	}

	public static void main(String[] args) {
		String pwd = new Md5Hash("123", "admin", 3).toString();
		System.out.println(pwd);
	}
}