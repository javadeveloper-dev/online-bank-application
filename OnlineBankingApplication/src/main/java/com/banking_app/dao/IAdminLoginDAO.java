package com.banking_app.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.banking_app.entity.Admin;

@Repository
public interface IAdminLoginDAO extends JpaRepository<Admin, Integer> {
	@Query("Select case when count(adminAlias)>0 then true else false END from Admin as adminAlias where adminAlias.email =:email and adminAlias.password =:password")
	Boolean existsByEmailAndPassword(String email, String password);

	Optional<Admin> findByEmail(String email);
	
	@Modifying
	@Query("UPDATE Admin SET password =:password WHERE email =:email")
	void updateAdminPassword(String email, String password);
}
