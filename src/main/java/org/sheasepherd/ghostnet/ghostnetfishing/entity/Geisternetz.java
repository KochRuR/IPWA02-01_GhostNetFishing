package org.sheasepherd.ghostnet.ghostnetfishing.entity;

import org.sheasepherd.ghostnet.ghostnetfishing.enums.GeisternetzStatus;

import jakarta.persistence.*;

@Entity
public class Geisternetz {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private Double latitude;

	@Column(nullable = false)
	private Double longitude;

	@Column(nullable = false)
	private int groesse;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private GeisternetzStatus status;

	@ManyToOne
	@JoinColumn(name = "meldende_person_id")
	private MeldendePerson meldendePerson;

	@ManyToOne
	@JoinColumn(name = "bergende_person_id")
	private BergendePerson bergendePerson;


	public Geisternetz() {
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public int getGroesse() {
		return groesse;
	}

	public void setGroesse(int groesse) {
		this.groesse = groesse;
	}

	public GeisternetzStatus getStatus() {
		return status;
	}

	public void setStatus(GeisternetzStatus status) {
		this.status = status;
	}

	public MeldendePerson getMeldendePerson() {
		return meldendePerson;
	}

	public void setMeldendePerson(MeldendePerson meldendePerson) {
		this.meldendePerson = meldendePerson;
	}

	public BergendePerson getBergendePerson() {
		return bergendePerson;
	}

	public void setBergendePerson(BergendePerson bergendePerson) {
		this.bergendePerson = bergendePerson;
	}

}
