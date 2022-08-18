package com.example.demo.model;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = true)
public interface ConcoursRepository extends JpaRepository<Concours, Long> {
	@Transactional
	@Modifying
	 @Query("DELETE from Concours c")
		void deleteCr();
}
