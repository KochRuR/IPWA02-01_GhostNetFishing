package org.sheasepherd.ghostnet.ghostnetfishing.controller;

import org.sheasepherd.ghostnet.ghostnetfishing.entity.BergendePerson;
import org.sheasepherd.ghostnet.ghostnetfishing.entity.MeldendePerson;
import org.sheasepherd.ghostnet.ghostnetfishing.service.PersonService;
import org.sheasepherd.ghostnet.ghostnetfishing.service.SessionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AnmeldungController {

	private final SessionService sessionService;
	private final PersonService personService;

	public AnmeldungController(SessionService sessionService, PersonService personService) {
		this.sessionService = sessionService;
		this.personService = personService;
	}

	@GetMapping("/anmeldung/meldender")
	public String zeigeMeldenderAnmeldung() {
		return "anmeldung-meldender";
	}

	@GetMapping("/anmeldung/bergender")
	public String zeigeBergenderAnmeldung() {
		return "anmeldung-bergender";
	}
	
	// Unterstützt anonyme Anmeldung ohne Name/Telefonnummer
	@PostMapping("/anmeldung/meldender")
	public String meldeMeldender(@RequestParam(required = false) String name,
			@RequestParam(required = false) String telefonnummer,
			@RequestParam(required = false, defaultValue = "false") boolean anonym, Model model) {

		try {
			MeldendePerson person = personService.speichereOderFindeMeldendePerson(name, telefonnummer, anonym);
			sessionService.setzeMeldender(person);
			return "redirect:/meldender/uebersicht";

		} catch (IllegalArgumentException e) {
			model.addAttribute("error", e.getMessage());
			return "anmeldung-meldender";
		}
	}

	@PostMapping("/anmeldung/bergender")
	public String meldeBergender(@RequestParam String name, @RequestParam String telefonnummer, Model model) {

		try {
			BergendePerson person = personService.speichereOderFindeBergendePerson(name, telefonnummer);
			sessionService.setzeBergender(person);
			return "redirect:/bergender/uebersicht";

		} catch (IllegalArgumentException e) {
			model.addAttribute("error", e.getMessage());
			return "anmeldung-bergender";
		}
	}

	@PostMapping("/abmelden")
	public String abmelden() {
		sessionService.abmelden();
		return "redirect:/";
	}
}