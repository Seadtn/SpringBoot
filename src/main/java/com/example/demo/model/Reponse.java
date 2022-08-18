package com.example.demo.model;


import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name="reponses")
public class Reponse {
	@Id
	@SequenceGenerator(
			name="reponse_sequence",
			sequenceName="reponse_sequence",
			allocationSize = 1
			)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "reponse_sequence"
			)
    private Long id;
	private String reponse1;
	private String reponse2;
	private String reponse3;
	private String reponse4;
	private String reponse5;
	private String reponse6;
	private String reponse7;
	private String reponse8;
	private String reponse9;
	private String reponse10;
	private String cv;
	private LocalDate time= LocalDate.now();
	 @ManyToOne(cascade=CascadeType.ALL)
	    @JoinColumn(
	    		nullable = false,
	            name = "app_user_id"
	    )
	private Utilisateur  Userapp;
	
	public Reponse() {
		super();
	}



	public Reponse(String reponse1, String reponse2, String reponse3, String reponse4, String reponse5, String reponse6,
			String reponse7, String reponse8, String reponse9, String reponse10, String cv,
			Utilisateur userapp) {
		super();
		this.reponse1 = reponse1;
		this.reponse2 = reponse2;
		this.reponse3 = reponse3;
		this.reponse4 = reponse4;
		this.reponse5 = reponse5;
		this.reponse6 = reponse6;
		this.reponse7 = reponse7;
		this.reponse8 = reponse8;
		this.reponse9 = reponse9;
		this.reponse10 = reponse10;
		this.cv = cv;
		Userapp = userapp;
	}



	public String getReponse1() {
		return reponse1;
	}

	public void setReponse1(String reponse1) {
		this.reponse1 = reponse1;
	}

	public String getReponse2() {
		return reponse2;
	}

	public void setReponse2(String reponse2) {
		this.reponse2 = reponse2;
	}

	public String getReponse3() {
		return reponse3;
	}

	public void setReponse3(String reponse3) {
		this.reponse3 = reponse3;
	}

	public String getReponse4() {
		return reponse4;
	}

	public void setReponse4(String reponse4) {
		this.reponse4 = reponse4;
	}

	public String getReponse5() {
		return reponse5;
	}

	public void setReponse5(String reponse5) {
		this.reponse5 = reponse5;
	}

	public String getReponse6() {
		return reponse6;
	}

	public void setReponse6(String reponse6) {
		this.reponse6 = reponse6;
	}

	public String getReponse7() {
		return reponse7;
	}

	public void setReponse7(String reponse7) {
		this.reponse7 = reponse7;
	}

	public String getReponse8() {
		return reponse8;
	}

	public void setReponse8(String reponse8) {
		this.reponse8 = reponse8;
	}

	public String getReponse9() {
		return reponse9;
	}

	public void setReponse9(String reponse9) {
		this.reponse9 = reponse9;
	}

	public String getReponse10() {
		return reponse10;
	}

	public void setReponse10(String reponse10) {
		this.reponse10 = reponse10;
	}

	public String getCv() {
		return cv;
	}

	public void setCv(String cv) {
		this.cv = cv;
	}

	public Utilisateur getUserapp() {
		return Userapp;
	}

	public void setUserapp(Utilisateur userapp) {
		Userapp = userapp;
	}

	public Long getId() {
		return id;
	}



	public LocalDate getTime() {
		return time;
	}



	public void setTime(LocalDate time) {
		this.time = time;
	}	
	
}
