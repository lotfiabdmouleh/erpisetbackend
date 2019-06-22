package tn.iset.controller.tirage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
import org.springframework.web.bind.annotation.RequestParam;
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
	@PreAuthorize("hasRole('ADMIN')or hasRole('ENSEIGNANT')or hasRole('AGENT')")
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
	
	@PreAuthorize("hasRole('ADMIN')")
	 @GetMapping("/date")
	    @ResponseBody
	    public List getnbrenspardate(@RequestParam("paramName") String d,@RequestParam("paramName2") String ff) throws ParseException{
		
		SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH);
		Date debut=formatter.parse(d);
		Date fin=formatter.parse(ff);
	
	    	return demandeTirageRepository.getCopieEnsParDate("document imprim",debut,fin); 
	    }
	
	@PreAuthorize("hasRole('ADMIN')")
	 @GetMapping("/date/dep")
	    @ResponseBody
	    public List getnbrdeppardate(@RequestParam("paramName") String d,@RequestParam("paramName2") String ff) throws ParseException{
		
		SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH);
		Date debut=formatter.parse(d);
		Date fin=formatter.parse(ff);
	
	
	    	return demandeTirageRepository.getCopieDepParDate("document imprim",debut,fin); 
	    }
	
	@PreAuthorize("hasRole('ADMIN')")
	 @GetMapping("/date/mat")
	    @ResponseBody
	    public List getnbrmatpardate(@RequestParam("paramName") String d,@RequestParam("paramName2") String ff) throws ParseException{
		
		SimpleDateFormat formatter=new SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH);
		Date debut=formatter.parse(d);
		Date fin=formatter.parse(ff);
	
	    	return demandeTirageRepository.getCopieDepParDate("document imprim",debut,fin); 
	    }
	
	@GetMapping("/{id}")
	public DemandeTirage get(@PathVariable Long id) {
		
		return demandeTirageRepository.findById(id).get();
	}
	@PreAuthorize("hasRole('ADMIN')or hasRole('ENSEIGNANT')")

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
	    
		@PreAuthorize("hasRole('ADMIN')or hasRole('ENSEIGNANT')")

	    @DeleteMapping("/{id}")
	    public void delete(@PathVariable Long id) {
	    	demandeTirageRepository.deleteById(id);
	    }
	  
	    

	

}

