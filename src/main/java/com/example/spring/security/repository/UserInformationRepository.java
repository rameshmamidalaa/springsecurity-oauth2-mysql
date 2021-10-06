/**
 * 
 */
package com.example.spring.security.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.spring.security.domain.UserInformation;

/**
 * @author Ramesh_Mamidala
 *
 */
@Repository
public interface UserInformationRepository extends JpaRepository<UserInformation, Long> {
	
	public UserInformation findByUserNameAndEnabled(String userName, Boolean enabled);
	
	public List<UserInformation> findAllByEnabled(Boolean enabled);

}
