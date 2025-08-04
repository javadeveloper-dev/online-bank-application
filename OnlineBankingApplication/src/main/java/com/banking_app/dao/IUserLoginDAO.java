package com.banking_app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.banking_app.entity.User;

@Repository
public interface IUserLoginDAO extends JpaRepository<User, Long> {
	@Query("Select case when count(userAlias)>0 then true else false END from User as userAlias where userAlias.email =:email and userAlias.password =:password")
	Boolean existsByEmailAndPassword(String email, String password);

	User findByEmail(String email);
	
	@Modifying
	@Query("UPDATE User SET password =:password WHERE email =:email")
	void updateUserPassword(String email, String password);
}
