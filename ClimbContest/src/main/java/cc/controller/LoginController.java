package cc.controller;

import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class LoginController {

	@GetMapping("/login")
	public ModelAndView home(Map<String, Object> model) {

		return new ModelAndView("login", model);
	}

	@PostMapping(path = "/loginAction")
	public void addMemberV1(@RequestAttribute String user, @RequestAttribute String pass) {
		// code
		int a = 9;
	}

}
