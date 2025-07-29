package com.banking_app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.banking_app.entity.User;

@Repository
public interface IUserRegisterDAO extends JpaRepository<User, Long> {
	
	@Query("Select case when count(userAlias)>0 then true else false End from User as userAlias where userAlias.email =:email")
	Boolean existsByEmail(@Param("email") String email);

	@Query("Select case when count(userAlias)>0 then true else false End from User as userAlias where userAlias.accountNumber =:accountNo")
	Boolean existsByAccountNo(@Param("accountNo") String accountNo);

	@Query("Select case when count(userAlias)>0 then true else false End from User as userAlias where userAlias.mobileNo =:mobileNo")
	Boolean existsByMobileNo(@Param("mobileNo") String mobileNo);
	

}
