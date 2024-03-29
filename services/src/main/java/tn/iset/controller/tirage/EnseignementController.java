package tn.iset.controller.tirage;

import java.util.Calendar;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.iset.model.tirage.Annee;
import tn.iset.model.tirage.Departement;
import tn.iset.model.tirage.Enseignant;
import tn.iset.model.tirage.Enseignement;
import tn.iset.model.tirage.Groupe;
import tn.iset.model.tirage.Matiere;
import tn.iset.model.tirage.Semestre;
import tn.iset.repository.tirage.AnneeRepository;
import tn.iset.repository.tirage.DepartementRepository;
import tn.iset.repository.tirage.EnseignantRepository;
import tn.iset.repository.tirage.EnseignementRepository;
import tn.iset.repository.tirage.GroupeRepository;
import tn.iset.repository.tirage.MatiereRepository;
import tn.iset.repository.tirage.SemestreRepository;



@CrossOrigin("*")

@RestController
@RequestMapping("/enseignemant")

public class EnseignementController  {

	@Autowired
	private EnseignementRepository enseignementRepository ;
	

	@Autowired
	private DepartementRepository departementRepository;
	@Autowired
	private EnseignantRepository enseignantRepository;
	@Autowired
	private GroupeRepository groupeRepository;
	@Autowired
	private MatiereRepository matiereRepository;
	
	@Autowired
	private SemestreRepository semestreRepository;
	
	@Autowired
	private AnneeRepository anneeRepository;
	public EnseignementController ( EnseignementRepository enseignementRepository) {
		super();
		this.enseignementRepository = enseignementRepository;
	}
	
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')or hasRole('ENSEIGNANT')or hasRole('AGENT')")
	public List<Enseignement> getAll() {
		
		return enseignementRepository.findAll();
	}
   
	@GetMapping("/{id}")
	public Enseignement get(@PathVariable Long id) {
		
		return enseignementRepository.findById(id).get();
	}
	
	  @PutMapping("/{id}/{dep}/{ens}/{grp}/{mat}/{sem}/{ann}")
	    public ResponseEntity<Enseignement> put(@PathVariable Long id,  @PathVariable Long dep, @PathVariable Long ens,
	    		@PathVariable Long grp,@PathVariable Long mat,@PathVariable Long sem,@PathVariable Long ann,
	    		@RequestBody Enseignement e) {
	       Optional<Enseignement> EnseignementOptional = enseignementRepository.findById(id);

		if (!EnseignementOptional.isPresent())
			return ResponseEntity.notFound().build();
		Semestre s=semestreRepository.findById(sem).get();
		Annee a=anneeRepository.findById(ann).get();
		Departement d = departementRepository.findById(dep).get();
    	Enseignant en = enseignantRepository.findById(ens).get();
    	Matiere m=matiereRepository.findById(mat).get();
    	Groupe g =groupeRepository.findById(grp).get();
    	e.setSemestre(s);
    	e.setAnnee(a);
    	e.setDepartement(d);
    	e.setEnseignant(en);
    	e.setGroupe(g);
    	e.setMatiere(m);
		e.setId(id);
		
		enseignementRepository.save(e);
		 
		return ResponseEntity.noContent().build();
	    }
	  
	    @PostMapping("/{dep}/{ens}/{grp}/{mat}/{sem}/{ann}")
	    public void post(@Valid @PathVariable Long dep, @PathVariable Long ens,@PathVariable Long grp,
	    		@PathVariable Long mat,@PathVariable Long sem,@PathVariable Long ann,
	    		@RequestBody Enseignement enseignement) {
	    	Enseignement e =new Enseignement();
	    	Departement d = departementRepository.findById(dep).get();
	    	Enseignant en = enseignantRepository.findById(ens).get();
	    	Matiere m=matiereRepository.findById(mat).get();
	    	Groupe g =groupeRepository.findById(grp).get();
	    	Semestre s=semestreRepository.findById(sem).get();
			Annee a=anneeRepository.findById(ann).get();
	    	e.setDepartement(d);
	    	e.setEnseignant(en);
	    	e.setGroupe(g);
	    	e.setMatiere(m);
	    	e.setSemestre(s);
	    	e.setAnnee(a);
	    	enseignementRepository.save(e);

	    }
	    
	    @DeleteMapping("/{id}")
	    public void delete(@PathVariable Long id) {
	    	enseignementRepository.deleteById(id);
	    }
	    
@GetMapping("/user/{username}")
@ResponseBody
public List getUser(@Valid @PathVariable String username) {
	int s=0;
	String a="";
	if((Calendar.getInstance().get(Calendar.MONTH)+1) >8 && (Calendar.getInstance().get(Calendar.MONTH)+1)<=12) {
		 s =1;
		 a=Calendar.getInstance().get(Calendar.YEAR)+"/"+Calendar.getInstance().get(Calendar.YEAR);
	}
	if((Calendar.getInstance().get(Calendar.MONTH)+1)>=1 && (Calendar.getInstance().get(Calendar.MONTH)+1)<=6) {
		 s =2;
		 a=Calendar.getInstance().get(Calendar.YEAR)-1+"/"+Calendar.getInstance().get(Calendar.YEAR);
	}
	return enseignementRepository.getEns(username,s,a);
}
	    
}
	