package com.example.demo.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name="concoursDetails")
public class Concours {
	@Id
	@SequenceGenerator(
			name="concours_sequence",
			sequenceName="concours_sequence",
			allocationSize = 1
			)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "concous_sequence"
			)
	private long id;
	private String titre;
	private String organisme;
	private int nbrQuestion;
    private String logo;
    private String type;
    private String url;
    private String abr;
	private String date_debut;
	private String date_fin;
	
	public Concours() {
		super();
	}
	

	

	public Concours(String titre, String organisme, int nbrQuestion, String logo, String type, String url, String abr,
			String date_debut, String date_fin) {
		super();
		this.titre = titre;
		this.organisme = organisme;
		this.nbrQuestion = nbrQuestion;
		this.logo = logo;
		this.type = type;
		this.url = url;
		this.abr = abr;
		this.date_debut = date_debut;
		this.date_fin = date_fin;
	}




	public String getAbr() {
		return abr;
	}




	public void setAbr(String abr) {
		this.abr = abr;
	}




	public String getTitre() {
		return titre;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public String getOrganisme() {
		return organisme;
	}

	public void setOrganisme(String organisme) {
		this.organisme = organisme;
	}

	public int getNbrQuestion() {
		return nbrQuestion;
	}

	public void setNbrQuestion(int nbrQuestion) {
		this.nbrQuestion = nbrQuestion;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public String getDate_debut() {
		return date_debut;
	}

	public void setDate_debut(String date_debut) {
		this.date_debut = date_debut;
	}

	public String getDate_fin() {
		return date_fin;
	}

	public void setDate_fin(String date_fin) {
		this.date_fin = date_fin;
	}

	public long getId() {
		return id;
	}





	public String getType() {
		return type;
	}





	public void setType(String type) {
		this.type = type;
	}





	public String getUrl() {
		return url;
	}





	public void setUrl(String url) {
		this.url = url;
	}
	
	
	
    
}
