package com.example.demo.model;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface QuestionRepository extends JpaRepository<Question, Long>{
	@Transactional
	@Modifying
	 @Query("DELETE from Question q")
		void DeleteRows();

}
