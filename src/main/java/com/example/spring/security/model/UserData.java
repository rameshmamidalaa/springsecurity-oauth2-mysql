/**
 * 
 */
package com.example.spring.security.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ramesh_Mamidala
 *
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserData {
	
	private Long id;
	private String userName;
	private String password;
	private String role;
	private Boolean enabled;

}
