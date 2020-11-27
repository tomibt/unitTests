package com.example.unitTest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.unitTest.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
	User findByName(String name);
	
	@Query("select u from User u where u.name = :name")
	User findByNameCustomQuery(@Param("name") String name);
	
	@Query(value = "select * from user as u where u.name = :name", nativeQuery = true)
	User findByNameNativeQuery(@Param("name") String name);
	

}
