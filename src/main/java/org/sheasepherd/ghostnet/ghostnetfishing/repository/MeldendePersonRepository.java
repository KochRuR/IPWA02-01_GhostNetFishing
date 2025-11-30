package org.sheasepherd.ghostnet.ghostnetfishing.repository;

import java.util.Optional;

import org.sheasepherd.ghostnet.ghostnetfishing.entity.MeldendePerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeldendePersonRepository extends JpaRepository<MeldendePerson, Long> {

	Optional<MeldendePerson> findByNameAndTelefonnummer(String name, String telefonnummer);
}
