package cc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/secret")
public class SecretController {

	@GetMapping
	public String get() {
		return "secret";
	}

}
