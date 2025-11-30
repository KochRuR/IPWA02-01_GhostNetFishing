package org.sheasepherd.ghostnet.ghostnetfishing.repository;

import org.sheasepherd.ghostnet.ghostnetfishing.entity.Geisternetz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeisternetzRepository extends JpaRepository<Geisternetz, Long> {

}
