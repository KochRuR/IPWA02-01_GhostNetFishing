package org.sheasepherd.ghostnet.ghostnetfishing.service;

import org.sheasepherd.ghostnet.ghostnetfishing.entity.BergendePerson;
import org.sheasepherd.ghostnet.ghostnetfishing.entity.MeldendePerson;
import org.sheasepherd.ghostnet.ghostnetfishing.enums.PersonenRolle;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.SessionScope;

@Service
@SessionScope
public class SessionService {

	private PersonenRolle aktuelleRolle;
	private MeldendePerson meldendePerson;
	private BergendePerson bergendePerson;

	public SessionService() {
		
	}

	public void setzeMeldender(MeldendePerson person) {
		this.aktuelleRolle = PersonenRolle.MELDENDER;
		this.meldendePerson = person;
	}

	public void setzeBergender(BergendePerson person) {
		this.aktuelleRolle = PersonenRolle.BERGENDER;
		this.bergendePerson = person;
	}
	
	public void abmelden() {
		this.aktuelleRolle = null;
		this.meldendePerson = null;
		this.bergendePerson = null;
	}
	
	public PersonenRolle getAktuelleRolle() {
		return aktuelleRolle;
	}

	public MeldendePerson getMeldendePerson() {
		return meldendePerson;
	}

	public BergendePerson getBergendePerson() {
		return bergendePerson;
	}

	public boolean isAngemeldet() {
		return aktuelleRolle != null;
	}

	public boolean isBergender() {
		return aktuelleRolle == PersonenRolle.BERGENDER;
	}

	public boolean isMeldender() {
		return aktuelleRolle == PersonenRolle.MELDENDER;
	}

	
}