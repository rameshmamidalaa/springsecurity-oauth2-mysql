/**
 * 
 */
package com.example.spring.security.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.example.spring.security.domain.UserInformation;
import com.example.spring.security.exception.UserInformationNotFoundException;
import com.example.spring.security.model.UserData;
import com.example.spring.security.repository.UserInformationRepository;

/**
 * @author Ramesh_Mamidala
 *
 */
@Service
public class UserInformationService {

	private UserInformationRepository userInformationRepository;

	@Autowired
	public UserInformationService(UserInformationRepository userInformationRepository) {
		this.userInformationRepository = userInformationRepository;
	}

	/**
	 * @param userName
	 * @return It returns the required user details.
	 */
	public UserData getUserDetailsByUserName(String userName) {
		Boolean enabled = true;
		UserInformation userInformation = userInformationRepository.findByUserNameAndEnabled(userName, enabled);
		
		if(ObjectUtils.isEmpty(userInformation)) {
			throw new UserInformationNotFoundException("User Name not found.");
		}
		return convertToUserDataModel(userInformation);
	}

	/**
	 * @return It returns all the active users information.
	 */
	public List<UserData> getAllActiveUsersInformation() {
		Boolean enabled = true;
		return userInformationRepository.findAllByEnabled(enabled).stream().map(userInformation -> {
			UserData userData = new UserData(userInformation.getId(), userInformation.getUserName(),
					userInformation.getPassword(), userInformation.getRole(), userInformation.getEnabled());
			return userData;
		}).collect(Collectors.toList());
	}

	/**
	 * @param id
	 * @return It returns the user information for a specific id of the user.
	 */
	public UserData getUserInformationById(Long id) {

		return userInformationRepository.findById(id).map(userInformation -> {
			return convertToUserDataModel(userInformation);
		}).orElseThrow(() -> new UserInformationNotFoundException("User Information is not found with this id::" + id));
	}

	/**
	 * Used for persisting the new user information into the database.
	 * 
	 * @param userData
	 */
	public void addUserInformation(UserData userData) {

		UserInformation userInformation = new UserInformation();
		userInformation.setPassword(encryptPassword(userData.getPassword()));
		userInformation.setUserName(userData.getUserName());
		userInformation.setRole(userData.getRole());
		userInformation.setEnabled(userData.getEnabled());
		userInformationRepository.save(userInformation);
	}

	/**
	 * Used for updating the user information.
	 * 
	 * @param id
	 * @param userData
	 */
	public void updateUserInformation(Long id, UserData userData) {

		UserInformation userInformation = userInformationRepository.findById(id).orElseThrow(
				() -> new UserInformationNotFoundException("User Information is not found with this id::" + id));
		userInformation.setUserName(userData.getUserName());
		userInformation.setPassword(encryptPassword(userData.getPassword()));
		userInformation.setRole(userData.getRole());
		userInformation.setEnabled(userData.getEnabled());

		userInformationRepository.save(userInformation);

	}

	/**
	 * Used for deleting the user information if required.
	 * 
	 * @param id
	 */
	public void deleteUserInformation(Long id) {
		UserInformation userInformation = userInformationRepository.findById(id).orElseThrow(
				() -> new UserInformationNotFoundException("User Information is not found with this id::" + id));
		userInformationRepository.delete(userInformation);
	}

	/**
	 * Used for updating the user's password.
	 * 
	 * @param id
	 * @param userData
	 */
	public void updateUserPassword(Long id, UserData userData) {
		UserInformation userInformation = userInformationRepository.findById(id).orElseThrow(
				() -> new UserInformationNotFoundException("User Information is not found with this id::" + id));
		userInformation.setPassword(encryptPassword(userData.getPassword()));
		userInformationRepository.save(userInformation);

	}
	
	/**
	 * Used for updating the user's role.
	 * 
	 * @param id
	 * @param userData
	 */
	public void updateUserRole(Long id, UserData userData) {
		UserInformation userInformation = userInformationRepository.findById(id).orElseThrow(
				() -> new UserInformationNotFoundException("User Information is not found with this id::" + id));
		userInformation.setRole(userData.getRole());
		userInformationRepository.save(userInformation);

	}

	/**
	 * Used for converting the user details from domain to model.
	 * 
	 * @param userInformation
	 * @return
	 */
	private UserData convertToUserDataModel(UserInformation userInformation) {

		UserData userData = new UserData(userInformation.getId(), userInformation.getUserName(),
				userInformation.getPassword(), userInformation.getRole(), userInformation.getEnabled());
		return userData;
	}

	/**
	 * Used for encrypting the password before persisting the user information into
	 * the database.
	 * 
	 * @param password
	 * @return
	 */
	private String encryptPassword(String password) {
		return new BCryptPasswordEncoder().encode(password);

	}

}
