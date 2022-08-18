package com.example.demo.controller;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {
	private String nom;
    private String prenom;
    private int cin;
    private String titre;
    private String date_naissance;
    private String email;
    private String password;
    private int inscrir;
    private String ville;
    
	public String getVille() {
		return ville;
	}
	public int getInscrir() {
		return inscrir;
	}
	public String getNom() {
		return nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public int getCin() {
		return cin;
	}
	public String getTitre() {
		return titre;
	}
	public String getDate_naissance() {
		return date_naissance;
	}
	public String getEmail() {
		return email;
	}
	public String getPassword() {
		return password;
	}
    

}
