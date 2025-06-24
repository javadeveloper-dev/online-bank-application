package com.banking_app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.banking_app.entity.Admin;

public interface ILoginDAO extends JpaRepository<Admin, Integer> {
	@Query("Select case when count(adminAlias)>0 then true else false END from Admin as adminAlias where adminAlias.email =:email and adminAlias.password =:password")
	Boolean existsByEmailAndPassword(String email, String password);
}
