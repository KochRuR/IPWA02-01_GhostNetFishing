package org.sheasepherd.ghostnet.ghostnetfishing.repository;

import java.util.Optional;

import org.sheasepherd.ghostnet.ghostnetfishing.entity.BergendePerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BergendePersonRepository extends JpaRepository<BergendePerson, Long> {

	Optional<BergendePerson> findByNameAndTelefonnummer(String name, String telefonnummer);
}