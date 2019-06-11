package tn.iset.model.tirage;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import tn.iset.model.Auditable;

@Entity
public class Approvisionnement extends Auditable<String>{
	@Id
	@GeneratedValue
	private Long id;
	private boolean etat;
	private String message;
	@ManyToOne
	private AgentTirage agentTirage;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public AgentTirage getAgentTirage() {
		return agentTirage;
	}
	public void setAgentTirage(AgentTirage agentTirage) {
		this.agentTirage = agentTirage;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public boolean isEtat() {
		return etat;
	}
	public void setEtat(boolean etat) {
		this.etat = etat;
	}
	
	

}
