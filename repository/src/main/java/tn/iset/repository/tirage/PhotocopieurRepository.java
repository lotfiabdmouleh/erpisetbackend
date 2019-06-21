package tn.iset.repository.tirage;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tn.iset.model.tirage.Photocopieur;

public interface PhotocopieurRepository extends JpaRepository<Photocopieur, Long> {
	@Query(value=" SELECT SUM(d.nb_copie*d.nbPages) FROM  Tirage t join t.demandeTirages as  d WHERE t.photocopieur.id= :ph")
	Long getSUMCopie(
			@Param("ph")Long ph);
	
	@Query(value=" SELECT MONTH(t.DateAjout)  , SUM(d.nb_copie*d.nbPages) FROM  Tirage t join t.demandeTirages as  d WHERE t.photocopieur.id= :ph GROUP BY MONTH(t.DateAjout) ORDER BY MONTH(t.DateAjout) ")
	List getPhLine(
			@Param("ph")Long ph);
}
