package com.example.demo.model;

import java.util.Collection;
import java.util.Collections;

import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.*;
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name="users")
public class Utilisateur  implements UserDetails{
	private static final long serialVersionUID = 1L;
	@Id
	@SequenceGenerator(
			name="user_sequence",
			sequenceName="user_sequence",
			allocationSize = 1
			)
	@GeneratedValue(
			strategy = GenerationType.SEQUENCE,
			generator = "user_sequence"
			)
    private Long id;
    private String nom;
    private String prenom;
    private int cin;
    private String titre;
    private String date_naissance;
    private String email;
    private String password;
    private String ville;
    @Enumerated(EnumType.STRING)
    private UserRole userRole;
    private Boolean locked=false;
    private Boolean enabled=false;
    private int Inscrir=0;
    public Utilisateur() {
		super();
	}
    public Utilisateur(String email, String password,UserRole userRole) {
		super();
		this.email = email;
		this.password = password;
		this.userRole=userRole;
	}
	public Utilisateur(String nom, String prenom, int cin, String titre, String date_naissance, String email,
			String password, String ville, UserRole userRole, int inscrir) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.cin = cin;
		this.titre = titre;
		this.date_naissance = date_naissance;
		this.email = email;
		this.password = password;
		this.ville = ville;
		this.userRole = userRole;
		Inscrir = inscrir;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		SimpleGrantedAuthority authority= 
				new SimpleGrantedAuthority(userRole.name());
		return Collections.singletonList(authority);
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !locked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}
	public Long getId() {
		return id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public int getCin() {
		return cin;
	}
	public void setCin(int cin) {
		this.cin = cin;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getDate_naissance() {
		return date_naissance;
	}
	public void setDate_naissance(String date_naissance) {
		this.date_naissance = date_naissance;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public UserRole getUserRole() {
		return userRole;
	}
	public void setUserRole(UserRole userRole) {
		this.userRole = userRole;
	}
	public Boolean getLocked() {
		return locked;
	}
	public void setLocked(Boolean locked) {
		this.locked = locked;
	}
	public Boolean getEnabled() {
		return enabled;
	}
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public int getInscrir() {
		return Inscrir;
	}
	public void setInscrir(int inscrir) {
		Inscrir = inscrir;
	}
	@Override
	public String toString() {
		return "Utilisateur [id=" + id + ", nom=" + nom + ", prenom=" + prenom + ", cin=" + cin + ", titre=" + titre
				+ ", date_naissance=" + date_naissance + ", email=" + email + ", password=" + password + ", userRole="
				+ userRole + "]";
	}


}
