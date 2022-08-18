package com.example.demo.controller.token;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;



@Repository
@Transactional(readOnly = true)
public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, Long>{
	Optional<ConfirmationToken> findByToken(String token);
	 @Transactional
	    @Query("SELECT c from ConfirmationToken c " +
	            "WHERE c.appUser.id = ?1")
	 Optional<ConfirmationToken> findByIdUser(long id);
    
    @Transactional
    @Query("SELECT c from ConfirmationToken c " +
            "WHERE c.appUser.cin = ?1 "
            +
            "AND c.token = ?2")
	Optional<ConfirmationToken> findTokenByCin(int cin, String token);
    @Transactional
   	@Modifying
   	 @Query("DELETE from ConfirmationToken ct")
   		void deleteToken();
}

