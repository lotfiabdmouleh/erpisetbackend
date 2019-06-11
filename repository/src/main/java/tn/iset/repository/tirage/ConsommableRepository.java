package tn.iset.repository.tirage;

import org.springframework.data.jpa.repository.JpaRepository;

import tn.iset.model.tirage.Consommable;

public interface ConsommableRepository extends JpaRepository<Consommable, Long> {

}
