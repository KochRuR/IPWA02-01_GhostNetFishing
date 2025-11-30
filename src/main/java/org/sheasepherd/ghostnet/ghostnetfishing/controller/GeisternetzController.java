package org.sheasepherd.ghostnet.ghostnetfishing.controller;

import java.util.List;

import org.sheasepherd.ghostnet.ghostnetfishing.dto.GeisternetzMeldungDTO;
import org.sheasepherd.ghostnet.ghostnetfishing.entity.BergendePerson;
import org.sheasepherd.ghostnet.ghostnetfishing.entity.Geisternetz;
import org.sheasepherd.ghostnet.ghostnetfishing.entity.MeldendePerson;
import org.sheasepherd.ghostnet.ghostnetfishing.enums.GeisternetzStatus;
import org.sheasepherd.ghostnet.ghostnetfishing.service.GeisternetzService;
import org.sheasepherd.ghostnet.ghostnetfishing.service.SessionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class GeisternetzController {

	private final GeisternetzService geisternetzService;
	private final SessionService sessionService;

	public GeisternetzController(GeisternetzService geisternetzService, SessionService sessionService) {
		this.geisternetzService = geisternetzService;
		this.sessionService = sessionService;
	}

	@GetMapping("/geisternetz/melden")
	public String zeigeMeldenFormular(Model model) {
		model.addAttribute("geisternetzMeldungDTO", new GeisternetzMeldungDTO());
		return "geisternetz-melden";
	}

	@GetMapping("/meldender/uebersicht")
	public String meldenderUebersicht(Model model) {
		List<Geisternetz> geisternetze = geisternetzService.findeAlleGeisternetze();
		model.addAttribute("geisternetze", geisternetze);
		return "meldender-uebersicht";
	}

	@GetMapping("/bergender/uebersicht")
	public String bergenderUebersicht(Model model) {
		
		
		List<Geisternetz> geisternetze = geisternetzService.findeAlleGeisternetze();
		model.addAttribute("geisternetze", geisternetze);
		
		
		return "bergender-uebersicht";
	}

	@GetMapping("/geisternetz/bestaetigung")
	public String bestaetigung() {
		return "geisternetz-bestaetigung";
	}

	@PostMapping("/geisternetz/melden")
	public String meldeGeisternetz(@ModelAttribute GeisternetzMeldungDTO dto) {
		MeldendePerson meldendePerson = sessionService.getMeldendePerson();

		if (meldendePerson == null) {
			return "redirect:/anmeldung/meldender?error=nichtAngemeldet";
		}

		geisternetzService.meldeNeuesNetz(dto.getLatitude(), dto.getLongitude(), dto.getGroesse(), meldendePerson);

		return "redirect:/geisternetz/bestaetigung";
	}

	@GetMapping("/bergung/vorbereiten/{id}")
	public String zeigeBergungVorbereiten(@PathVariable Long id, Model model) {
		Geisternetz netz = geisternetzService.findeNetzNachId(id);
		model.addAttribute("netz", netz);
		return "bergung-vorbereiten";
	}
	
	// Ermöglicht sowohl Meldenden als auch Bergenden, ein Netz als verschollen zu markieren
	// Anonyme Meldende dürfen dies nicht
	@PostMapping("/geisternetz/verschollen")
	public String meldeNetzVerschollen(@RequestParam Long id) {
		
		Geisternetz netz = geisternetzService.findeNetzNachId(id);
		
		MeldendePerson meldendePerson = sessionService.getMeldendePerson();
		if (meldendePerson != null) {
			if (meldendePerson.isAnonym()) {
				return "redirect:/meldender/uebersicht?error=anonym";
			}

			geisternetzService.setzeNetzVerschollen(netz);
			return "redirect:/meldender/uebersicht?success=verschollen";
		}

		BergendePerson bergendePerson = sessionService.getBergendePerson();
		if (bergendePerson != null) {
			geisternetzService.setzeNetzVerschollen(netz);
			return "redirect:/bergender/uebersicht?success=verschollen";
		}

		return "redirect:/";
	}

	@PostMapping("/geisternetz/verschollen/formular")
	public String zeigeVerschollenFormular(@RequestParam Long id, Model model) {
		Geisternetz netz = geisternetzService.findeNetzNachId(id);
		model.addAttribute("netz", netz);
		return "geisternetz-verschollen";
	}

	@PostMapping("/geisternetz/geborgen/formular")
	public String zeigeGeborgenFormular(@RequestParam Long id, Model model) {
		Geisternetz netz = geisternetzService.findeNetzNachId(id);
		model.addAttribute("netz", netz);
		return "geisternetz-geborgen";
	}
	
	// Nur der zugewiesene Bergende darf das Netz als geborgen markieren
	@PostMapping("/geisternetz/geborgen")
	public String meldeNetzAlsGeborgen(@RequestParam Long id) {
		Geisternetz netz = geisternetzService.findeNetzNachId(id);
		BergendePerson bergendePerson = sessionService.getBergendePerson();

		if (bergendePerson == null) {
			return "redirect:/bergender/uebersicht?error=nichtAngemeldet";
		}

		if (netz.getBergendePerson() == null || !netz.getBergendePerson().getId().equals(bergendePerson.getId())) {
			return "redirect:/bergender/uebersicht?error=falschePerson";
		}

		if (netz.getStatus() != GeisternetzStatus.BERGUNG_BEVORSTEHEND) {
			return "redirect:/bergender/uebersicht?error=falscherStatus";
		}

		geisternetzService.setzeNetzGeborgen(netz, bergendePerson);
		return "redirect:/bergender/uebersicht?success=geborgen";
	}

	@PostMapping("/bergung/vorbereiten")
	public String bereiteBergungVor(@RequestParam Long id) {
		Geisternetz netz = geisternetzService.findeNetzNachId(id);
		BergendePerson bergendePerson = sessionService.getBergendePerson();

		if (bergendePerson == null) {
			return "redirect:/bergender/uebersicht?error=nichtAngemeldet";
		}

		if (netz.getBergendePerson() != null) {
			return "redirect:/bergender/uebersicht?error=bereitsVergeben";
		}

		if (netz.getStatus() != GeisternetzStatus.GEMELDET) {
			return "redirect:/bergender/uebersicht?error=nichtVerfuegbar";
		}

		geisternetzService.bereiteBergungVor(netz, bergendePerson);
		return "redirect:/bergender/uebersicht?success=vorbereitet";
	}
}