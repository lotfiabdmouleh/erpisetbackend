package tn.iset.repository.tirage;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tn.iset.model.tirage.Enseignement;

public interface EnseignementRepository extends JpaRepository<Enseignement, Long> {
	@Query(value=" SELECT e FROM Enseignement e WHERE e.enseignant.username= :username AND e.semestre.semestre= :s AND e.annee.annee= :a")
	List getEns(
	  @Param("username") String username, @Param("s") int s,@Param("a") String a)
	;
}
