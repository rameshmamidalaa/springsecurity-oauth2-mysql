/**
 * 
 */
package com.example.spring.security.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Ramesh_Mamidala
 *
 */
@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInformation implements Serializable {
	
	private static final long serialVersionUID = 2864448420531749091L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "user_seq_gen")
	@SequenceGenerator(name = "user_seq_gen", sequenceName = "user_id_seq", allocationSize = 1)
	@Column(name = "userid", updatable = false, nullable = false, length = 25)
	private Long id;
	
	@Column(name = "username", nullable = false, length = 50)
	private String userName;
	@Column(name = "password", nullable = false, length = 800)
	private String password;
	@Column(name = "role", nullable = false, length = 50)
	private String role;
	@Column(name = "enabled")
	private Boolean enabled;

}
