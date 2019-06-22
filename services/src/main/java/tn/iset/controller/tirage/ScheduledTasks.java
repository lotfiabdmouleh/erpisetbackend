package tn.iset.controller.tirage;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import tn.iset.model.tirage.DemandeTirage;
import tn.iset.repository.tirage.DemandeTirageRepository;
import tn.iset.repository.tirage.TirageRepository;

@Component
public class ScheduledTasks {
	List<DemandeTirage> l=new ArrayList<DemandeTirage>();
		@Autowired
		private TirageRepository tirageRepository;
		@Autowired
		private DemandeTirageRepository demandeTirageRepository;
	
		@Autowired
		private JdbcTemplate jdbcTemplate;
		
		@Scheduled(fixedRate = 30000)
	    public void reportCurrentTime() {
	    	l =tirageRepository.getdem("en attente");

	    	if(l!=null) {
	    for(int i=0;i<l.size();i++) {
	    	DemandeTirage demandeTirage=demandeTirageRepository.findById(l.get(i).getId()).get();
	    	if((new Date().getTime()-demandeTirage.getDateAjout().getTime())/1000/60/60 >= 24) {
		   		demandeTirage.setEtat("Votre document est en cours d impression");
		    	demandeTirage.setId(demandeTirage.getId());   	 
		    	jdbcTemplate.update("UPDATE demande_tirage SET etat= ? WHERE id=?","Votre document est en cours d impression",demandeTirage.getId());   	 
		    	 
	    	}
	    }
	    	
	    }
	    }
}
