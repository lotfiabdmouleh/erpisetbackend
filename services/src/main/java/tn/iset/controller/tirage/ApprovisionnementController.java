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

import tn.iset.model.Agent;
import tn.iset.model.tirage.Approvisionnement;
import tn.iset.repository.tirage.ApprovisionnementRepository;

@RestController
@CrossOrigin("*")
@RequestMapping("/approvisionnement")
public class ApprovisionnementController {

	@Autowired
	private ApprovisionnementRepository approvisionnementRepository;


	@GetMapping
	@PreAuthorize("hasRole('AGENT')")
	public List<Approvisionnement> getAll() {
		
		return approvisionnementRepository.findAll();
	}
	@GetMapping("/valid")
	@PreAuthorize("hasRole('ADMIN')")
	public List<Approvisionnement> getAllapp() {
		
		return approvisionnementRepository.getval();
	}
	@GetMapping("/{id}")
	public Approvisionnement get(@PathVariable Long id) {
		
		return approvisionnementRepository.findById(id).get();
	}
	
	
	
	  @PutMapping("/{id}")
	    public ResponseEntity<Agent> put(@PathVariable Long id, @RequestBody Approvisionnement approvisionnement ) {
	       Optional<Approvisionnement> appOptional = approvisionnementRepository.findById(id);

		if (!appOptional.isPresent())
			return ResponseEntity.notFound().build();

		approvisionnement.setId(id);
		
		approvisionnementRepository.save(approvisionnement);
		 
		return ResponseEntity.noContent().build();
	    }
	  
	    @PostMapping
	    public void post(@Valid @RequestBody Approvisionnement approvisionnement) {
	    		approvisionnementRepository.save(approvisionnement);

	    }
	    
	    @DeleteMapping("/{id}")
	    public void delete(@PathVariable Long id) {
	        approvisionnementRepository.deleteById(id);
	    }
	    

	
}
