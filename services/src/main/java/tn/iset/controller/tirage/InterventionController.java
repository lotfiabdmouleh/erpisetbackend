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

import tn.iset.model.tirage.Intervention;
import tn.iset.model.tirage.Photocopieur;
import tn.iset.model.tirage.Recharge;
import tn.iset.repository.tirage.InterventionRepository;
import tn.iset.repository.tirage.PhotocopieurRepository;


@CrossOrigin("*")

@RestController
@RequestMapping("/intervention")

public class InterventionController  {

	@Autowired
	private InterventionRepository interventionRepository;

	@Autowired
	private PhotocopieurRepository photocopieurRepository;
	
	

	@GetMapping
	@PreAuthorize("hasRole('ADMIN')")
	public List<Intervention> getAll() {
		
		return interventionRepository.findAll();
	}
   
	@GetMapping("/{id}")
	public Intervention get(@PathVariable Long id) {
		
		return interventionRepository.findById(id).get();
	}
	
	  @PutMapping("/{id}/{ph}")
	    public ResponseEntity<Recharge> put(@PathVariable Long id,@PathVariable Long ph, @RequestBody Intervention  intervention) {
	       Optional<Intervention> InterventionOptional = interventionRepository.findById(id);

		if (!InterventionOptional.isPresent())
			return ResponseEntity.notFound().build();
	
		
    	Photocopieur f=photocopieurRepository.findById(ph).get();
    
    	intervention.setPhotocopieur(f);
    	intervention.setId(id);
    	
    	interventionRepository.save(intervention);
		 
		return ResponseEntity.noContent().build();
	    }
	  
	    @PostMapping("/{ph}")
	    public void post(@Valid @PathVariable Long ph, @RequestBody Intervention intervention) {
	    
	    	Photocopieur f=photocopieurRepository.findById(ph).get();
	    	f.addIntervention(intervention);
	    	intervention.setPhotocopieur(f);
	    		
	    	interventionRepository.save(intervention);
	    }
	    
	    @DeleteMapping("/{id}")
	    public void delete(@PathVariable Long id) {
	    	interventionRepository.deleteById(id);
	    }
	    
		
}