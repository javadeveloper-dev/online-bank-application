package com.banking_app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.banking_app.dto.LoginDTO;
import com.banking_app.entity.Admin;
@Repository
public interface IAdminRegisterDAO extends JpaRepository<Admin, Integer> {

	@Query("Select case when count(adminAlias)>0 then true else false End from Admin as adminAlias where adminAlias.email =:email")
	Boolean existsByEmail(@Param("email") String email);

	@Query("Select case when count(adminAlias)>0 then true else false End from Admin as adminAlias where adminAlias.accountNumber =:accountNo")
	Boolean existsByAccountNo(@Param("accountNo") String accountNo);

	@Query("Select case when count(adminAlias)>0 then true else false End from Admin as adminAlias where adminAlias.mobileNo =:mobileNo")
	Boolean existsByMobileNo(@Param("mobileNo") String mobileNo);

	
}
