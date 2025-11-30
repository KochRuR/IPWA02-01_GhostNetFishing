package org.sheasepherd.ghostnet.ghostnetfishing.service;

import java.util.List;

import org.sheasepherd.ghostnet.ghostnetfishing.entity.BergendePerson;
import org.sheasepherd.ghostnet.ghostnetfishing.entity.Geisternetz;
import org.sheasepherd.ghostnet.ghostnetfishing.entity.MeldendePerson;
import org.sheasepherd.ghostnet.ghostnetfishing.enums.GeisternetzStatus;
import org.sheasepherd.ghostnet.ghostnetfishing.repository.GeisternetzRepository;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

@Service
public class GeisternetzService {

	private final GeisternetzRepository geisternetzRepository;

	public GeisternetzService(GeisternetzRepository geisternetzRepository) {
		this.geisternetzRepository = geisternetzRepository;
	}

	@Transactional
	public void meldeNeuesNetz(Double latitude, Double longitude, int groesse, MeldendePerson meldendePerson) {

		
		if (latitude < -90 || latitude > 90) {
			throw new IllegalArgumentException("Ungültige Latitude: " + latitude);
		}
		if (longitude < -180 || longitude > 180) {
			throw new IllegalArgumentException("Ungültige Longitude: " + longitude);
		}
		if (groesse <= 0) {
			throw new IllegalArgumentException("Größe muss positiv sein: " + groesse);
		}
		

		Geisternetz geisternetz = new Geisternetz();
		geisternetz.setLatitude(latitude);
		geisternetz.setLongitude(longitude);
		geisternetz.setGroesse(groesse);
		geisternetz.setStatus(GeisternetzStatus.GEMELDET);
		geisternetz.setMeldendePerson(meldendePerson);
		
		
		geisternetzRepository.save(geisternetz);
	}

	public List<Geisternetz> findeAlleGeisternetze() {
		
		
		return geisternetzRepository.findAll();
	}

	public Geisternetz findeNetzNachId(Long id) {
		return geisternetzRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Geisternetz mit ID " + id + " nicht gefunden"));
	}

	@Transactional
	public void bereiteBergungVor(Geisternetz netz, BergendePerson bergendePerson) {

		
		if (netz.getStatus() != GeisternetzStatus.GEMELDET) {
			throw new IllegalStateException("Nur gemeldete Netze können zur Bergung vorbereitet werden. "
					+ "Aktueller Status: " + netz.getStatus());
		}
		if (netz.getBergendePerson() != null) {
			throw new IllegalStateException(
					"Dieses Netz wurde bereits von " + netz.getBergendePerson().getName() + " übernommen!");
		}

		
		netz.setStatus(GeisternetzStatus.BERGUNG_BEVORSTEHEND);
		netz.setBergendePerson(bergendePerson);
		
		
		geisternetzRepository.save(netz);
	}

	@Transactional
	public void setzeNetzGeborgen(Geisternetz netz, BergendePerson bergendePerson) {

	
		if (netz.getStatus() != GeisternetzStatus.BERGUNG_BEVORSTEHEND) {
			throw new IllegalStateException(
					"Nur Netze mit bevorstehender Bergung können als geborgen markiert werden.");
		}
		if (netz.getBergendePerson() == null || !netz.getBergendePerson().getId().equals(bergendePerson.getId())) {
			throw new IllegalStateException(
					"Nur die Person, die die Bergung angekündigt hat, kann das Netz als geborgen melden!");
		}
		
		
		netz.setStatus(GeisternetzStatus.GEBORGEN);
		geisternetzRepository.save(netz);
	}

	@Transactional
	public void setzeNetzVerschollen(Geisternetz netz) {

		
		if (netz.getStatus() == GeisternetzStatus.GEBORGEN) {
			throw new IllegalStateException("Geborgene Netze können nicht als verschollen gemeldet werden!");
		}

		
		netz.setStatus(GeisternetzStatus.VERSCHOLLEN);
		geisternetzRepository.save(netz);
	}
}
