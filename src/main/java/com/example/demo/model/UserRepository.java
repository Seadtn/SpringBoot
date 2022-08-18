package com.example.demo.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends JpaRepository<Utilisateur, Long>{
	Optional<Utilisateur>findByCin(int cin);
	@Transactional
	 @Query("SELECT u from Utilisateur u " +
	         "WHERE u.email = ?1 ")	 
 Optional<Utilisateur>findByEmail(String email);
 @Transactional
 @Query("SELECT u from Utilisateur u " +
         "WHERE u.email = ?1 "
         + "AND u.password= ?2")
	Optional<Utilisateur> findLoginUser(String email ,String password);
 @Transactional
 @Modifying
 @Query("UPDATE Utilisateur u " +
         "SET u.password = ?2 " +
         "WHERE u.cin = ?1")
 int updatePassword( int cin,
                       String Password);
 @Transactional
	@Modifying
	 @Query("DELETE from Utilisateur u"
	 		+ " WHERE userRole='USER'")
		void deleteUsers();
}
