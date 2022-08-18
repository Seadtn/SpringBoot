package com.example.demo.model;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ReponseRepository extends JpaRepository<Reponse, Long> {
	 @Transactional
	 @Query("SELECT r from Reponse r " +
	         "WHERE r.Userapp.id = ?1 ")
		Optional<Reponse> findReponseByIdUser(long id);
	 @Transactional
	 @Query("SELECT r.cv from Reponse r " +
	         "WHERE r.Userapp.id = ?1 ")
		Optional<Reponse> findCvByIdUser(long id);
	 @Transactional
		@Modifying
		 @Query("DELETE from Reponse r")
			void DeleteReponse();
	 @Transactional
	 @Modifying
	 @Query("DELETE from Reponse r"
		 		+ " WHERE r.Userapp.id = ?1 ")
	 int deleteReponseById( long id);
	 @Transactional
	 @Modifying
	 @Query("UPDATE Reponse r " +
	         "SET r.reponse1 = ?2 ," +
			 "r.reponse2 = ?3 ,"+
			 "r.reponse3 = ?4 ,"+
			 "r.reponse4 = ?5 ,"+
			 "r.reponse5 = ?6 ,"+
			 "r.reponse6 = ?7 ,"+
			 "r.reponse7 = ?8 ,"+
			 "r.reponse8 = ?9 ,"+
			 "r.reponse9 = ?10 ,"+
			 "r.reponse10 = ?11 "+
	         "WHERE r.Userapp.id = ?1")
	 void updateReponse( long id,
	                       String reponse1,String reponse2,String reponse3,String reponse4,String reponse5
	                       ,String reponse6,String reponse7,String reponse8,String reponse9,String reponse10);
}
