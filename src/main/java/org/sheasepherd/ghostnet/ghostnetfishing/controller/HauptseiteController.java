package org.sheasepherd.ghostnet.ghostnetfishing.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HauptseiteController {

	@GetMapping("/")
	public String zeigeIndex() {
		return "index";
	}
}