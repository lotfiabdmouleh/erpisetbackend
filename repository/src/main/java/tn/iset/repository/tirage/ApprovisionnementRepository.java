package tn.iset.repository.tirage;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import tn.iset.model.tirage.Approvisionnement;

public interface ApprovisionnementRepository extends JpaRepository<Approvisionnement, Long> {
	@Query(value=" SELECT e FROM Approvisionnement e WHERE e.etat = false")
	List getval();
}
