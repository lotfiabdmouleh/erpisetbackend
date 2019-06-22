package tn.iset.controller.tirage;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tn.iset.model.tirage.Groupe;
import tn.iset.repository.tirage.GroupeRepository;


@CrossOrigin("*")

@RestController
@RequestMapping("/groupe")

public class GroupeController  {

	@Autowired
	private GroupeRepository groupeRepository ;
	

	public GroupeController (  GroupeRepository groupeRepository) {
		super();
		this.groupeRepository = groupeRepository;
	}
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')or hasRole('ENSEIGNANT')or hasRole('AGENT')")
	public List<Groupe> getAll() {
		
		return groupeRepository.findAll();
	}
   
	@GetMapping("/{id}")
	public Groupe get(@PathVariable Long id) {
		
		return groupeRepository.findById(id).get();
	}
	
	  @PutMapping("/{id}")
	    public ResponseEntity<Groupe> put(@PathVariable Long id, @RequestBody Groupe groupe) {
	       Optional<Groupe> GroupeOptional = groupeRepository.findById(id);

		if (!GroupeOptional.isPresent())
			return ResponseEntity.notFound().build();

		groupe.setId(id);
		
		groupeRepository.save(groupe);
		 
		return ResponseEntity.noContent().build();
	    }
	  
	    @PostMapping
	    public void post(@Valid @RequestBody Groupe groupe) {
	    	groupeRepository.save(groupe);

	    }
	    
	    @DeleteMapping("/{id}")
	    public void delete(@PathVariable Long id) {
	    	groupeRepository.deleteById(id);
	    }
	    
}
	