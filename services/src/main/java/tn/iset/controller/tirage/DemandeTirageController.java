package tn.iset.controller.tirage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;

import javax.persistence.EntityManager;
import javax.validation.Valid;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.InvalidPasswordException;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import tn.iset.model.tirage.DemandeTirage;
import tn.iset.model.tirage.Enseignement;
import tn.iset.repository.tirage.DemandeTirageRepository;
import tn.iset.repository.tirage.EnseignementRepository;


@CrossOrigin("*")

@RestController
@RequestMapping("/demandeTirage")

public class DemandeTirageController  {

	@Autowired
	private DemandeTirageRepository demandeTirageRepository;
	@Autowired
	private EntityManager entityManager;

	@Autowired
	private EnseignementRepository enseignementRepository;
	public DemandeTirageController (DemandeTirageRepository demandeTirageRepository) {
		super();
		this.demandeTirageRepository = demandeTirageRepository;
	}
	@GetMapping
	@PreAuthorize("hasRole('ADMIN')or hasRole('PM')or hasRole('AGENT')")
	public List<DemandeTirage> getAll() {
		
		return demandeTirageRepository.findAll();
	}
	@PreAuthorize("hasRole('ADMIN')")
	 @GetMapping("/nbrcopie/{id}")
	    @ResponseBody
	    public Long getnbr(@PathVariable Long id){
	    	return demandeTirageRepository.getCopieParDep("document imprim", id);
	    }
	
	@PreAuthorize("hasRole('ADMIN')")
	 @GetMapping("/nbrcopiedep")
	    @ResponseBody
	    public List getnbrdep(){
	    	return demandeTirageRepository.getCopieDep("document imprim");
	    }
	@PreAuthorize("hasRole('ADMIN')")
	 @GetMapping("/nbrcopiemat")
	    @ResponseBody
	    public List getnbrmat(){
	    	return demandeTirageRepository.getCopieMat("document imprim");
	    }
	@PreAuthorize("hasRole('ADMIN')")
	 @GetMapping("/nbrcopieens")
	    @ResponseBody
	    public List getnbrens(){
	    	return demandeTirageRepository.getCopieEns("document imprim"); 
	    }
	@GetMapping("/{id}")
	public DemandeTirage get(@PathVariable Long id) {
		
		return demandeTirageRepository.findById(id).get();
	}
	@PreAuthorize("hasRole('ADMIN')or hasRole('PM')")

	    @PostMapping("/{file}")
	    public void post(@Valid @PathVariable String file,@RequestBody Enseignement enseignement) throws InvalidPasswordException, IOException {
		File filename;
	    	DemandeTirage demandeTirage=new DemandeTirage();
	    	filename = Paths.get("C:/upload-dir/"+file).toFile();
	    	PDDocument doc =PDDocument.load(filename); 
	    	demandeTirage.setNbPages(doc.getNumberOfPages());
	   
	    	demandeTirage.setNb_copie(enseignement.getGroupe().getNb_etd());
	    	demandeTirage.setFile(file);
	    	demandeTirage.setEnseignement(enseignement);
	    	if(doc.getNumberOfPages()>10)
	    	{
	    		demandeTirage.setEtat("Document en attente");
	    	}
	    	else {
	    			demandeTirage.setEtat("Votre document est en cours d impression");
	    	}
	    
	    	demandeTirageRepository.save(demandeTirage);
	    	enseignement.addDemandeTirages(demandeTirage);
	    	enseignement.setId(enseignement.getId());
	    	enseignementRepository.save(enseignement);

	    }
	    
	    
	    @GetMapping("/user/{user}")
	    @ResponseBody
	    public List getdemande(@PathVariable String user) {
	    	return demandeTirageRepository.getEns(user);
	    }
	    
		@PreAuthorize("hasRole('ADMIN')or hasRole('PM')")

	    @DeleteMapping("/{id}")
	    public void delete(@PathVariable Long id) {
	    	demandeTirageRepository.deleteById(id);
	    }
	  
	    
@GetMapping("/history")
@ResponseBody
public List gethistory(){
	List revisions = AuditReaderFactory.get(entityManager)
           .createQuery()
           .forRevisionsOfEntity(DemandeTirage.class, false, true)
           //.addProjection(AuditEntity.id())
           .addProjection( AuditEntity.revisionProperty("timestamp"))
           .addProjection(AuditEntity.revisionProperty("modifiedBy"))
           .addProjection(AuditEntity.revisionType())
           .getResultList();
	
	return revisions;
}
	

}

