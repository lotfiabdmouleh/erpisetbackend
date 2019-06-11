package tn.iset.model;

import java.util.Date;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class Auditable<String> {
	
	 
	@CreatedBy
	    protected String createdBy;
	 
	    @LastModifiedDate
		private Date DateModification;
	    
		@CreatedDate

		private Date DateAjout;
	    @LastModifiedBy
	    protected String lastModifiedBy;
		public String getCreatedBy() {
			return createdBy;
		}
		public void setCreatedBy(String createdBy) {
			this.createdBy = createdBy;
		}
		public Date getDateModification() {
			return DateModification;
		}
		public void setDateModification(Date dateModification) {
			DateModification = dateModification;
		}
		public Date getDateAjout() {
			return DateAjout;
		}
		public void setDateAjout(Date dateAjout) {
			DateAjout = dateAjout;
		}
		public String getLastModifiedBy() {
			return lastModifiedBy;
		}
		public void setLastModifiedBy(String lastModifiedBy) {
			this.lastModifiedBy = lastModifiedBy;
		}
	    
}
