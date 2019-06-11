package tn.iset.repository.tirage;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.iset.model.tirage.Enseignant;

public interface EnseignantRepository extends JpaRepository<Enseignant, Long> {
	
}
