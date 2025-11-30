package org.sheasepherd.ghostnet.ghostnetfishing.entity;

import jakarta.persistence.*;

@Entity
public class MeldendePerson {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	// Bei anonymen Meldungen sind Name und Telefonnummer null
	private String name;
	private String telefonnummer;

	@Column(nullable = false)
	private boolean anonym;


	public MeldendePerson() {
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTelefonnummer() {
		return telefonnummer;
	}

	public void setTelefonnummer(String telefonnummer) {
		this.telefonnummer = telefonnummer;
	}

	public boolean isAnonym() {
		return anonym;
	}

	public void setAnonym(boolean anonym) {
		this.anonym = anonym;
	}
}
