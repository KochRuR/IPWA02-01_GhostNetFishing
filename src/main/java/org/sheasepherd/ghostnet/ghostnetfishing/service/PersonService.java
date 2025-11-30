package org.sheasepherd.ghostnet.ghostnetfishing.service;

import org.sheasepherd.ghostnet.ghostnetfishing.entity.BergendePerson;
import org.sheasepherd.ghostnet.ghostnetfishing.entity.MeldendePerson;
import org.sheasepherd.ghostnet.ghostnetfishing.repository.BergendePersonRepository;
import org.sheasepherd.ghostnet.ghostnetfishing.repository.MeldendePersonRepository;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

	private final MeldendePersonRepository meldendePersonRepository;
	private final BergendePersonRepository bergendePersonRepository;

	public PersonService(MeldendePersonRepository meldendePersonRepository,
			BergendePersonRepository bergendePersonRepository) {
		this.meldendePersonRepository = meldendePersonRepository;
		this.bergendePersonRepository = bergendePersonRepository;
	}

	public MeldendePerson speichereOderFindeMeldendePerson(String name, String telefonnummer, boolean anonym) {
		
		// Anonyme Meldende werden bei jeder Anmeldung neu erstellt, nicht wiederverwendet
		if (anonym) {
			MeldendePerson neu = new MeldendePerson();
			neu.setAnonym(true);
			neu.setName(null);
			neu.setTelefonnummer(null);
			return meldendePersonRepository.save(neu);
		} else {
			if (name == null || name.isBlank()) {
				throw new IllegalArgumentException("Name darf bei nicht-anonymer Anmeldung nicht leer sein!");
			}
			if (telefonnummer == null || telefonnummer.isBlank()) {
				throw new IllegalArgumentException("Telefonnummer darf bei nicht-anonymer Anmeldung nicht leer sein!");
			}
			
			return meldendePersonRepository.findByNameAndTelefonnummer(name, telefonnummer).orElseGet(() -> {
				MeldendePerson neu = new MeldendePerson();
				neu.setName(name);
				neu.setTelefonnummer(telefonnummer);
				neu.setAnonym(false);
				return meldendePersonRepository.save(neu);
			});
		}
	}

	
	public BergendePerson speichereOderFindeBergendePerson(String name, String telefonnummer) {

		if (name == null || name.isBlank()) {
			throw new IllegalArgumentException("Name darf nicht leer sein!");
		}
		if (telefonnummer == null || telefonnummer.isBlank()) {
			throw new IllegalArgumentException("Telefonnummer darf nicht leer sein!");
		}

		
		return bergendePersonRepository.findByNameAndTelefonnummer(name, telefonnummer).orElseGet(() -> {
			BergendePerson neu = new BergendePerson();
			neu.setName(name);
			neu.setTelefonnummer(telefonnummer);
			return bergendePersonRepository.save(neu);
		});
	}
}
