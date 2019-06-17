package tn.iset.repository.tirage;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import tn.iset.model.tirage.DemandeTirage;

public interface DemandeTirageRepository extends JpaRepository<DemandeTirage, Long> {

	@Query(value=" SELECT e FROM DemandeTirage e WHERE e.enseignement.enseignant.username= :username")
	List getEns(
	  @Param("username") String username);
	
	@Query(value=" SELECT SUM(d.nb_copie*d.nbPages) FROM  DemandeTirage d  WHERE d.etat like %:type% and d.enseignement.departement.id= :id")
	Long getCopieParDep(@Param("type")String type,@Param("id")Long id);
	
	@Query(value=" SELECT d.enseignement.departement.nom_dep, SUM(d.nb_copie*d.nbPages) as somme FROM  DemandeTirage d  WHERE d.etat like %:type% GROUP BY d.enseignement.departement.nom_dep ORDER BY somme DESC ")
	List getCopieDep(@Param("type")String type);
	
	@Query(value=" SELECT    d.enseignement.enseignant.name,  SUM(d.nb_copie*d.nbPages) as somme FROM  DemandeTirage d  WHERE d.etat like %:type% GROUP BY d.enseignement.enseignant.name ORDER BY somme DESC ")
	List getCopieEns(@Param("type")String type);
	
	@Query(value=" SELECT    d.enseignement.matiere.nom_mat,  SUM(d.nb_copie*d.nbPages) as somme FROM  DemandeTirage d  WHERE d.etat like %:type% GROUP BY d.enseignement.matiere.nom_mat ORDER BY somme DESC ")
	List getCopieMat(@Param("type")String type);

}
