package com.lms.user.model;

import java.time.LocalDateTime;
import java.util.Set;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.lms.user.converter.SimpleGrantedAuthorityConverter;

import jakarta.persistence.Convert;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {
	
	    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    private String username;
	    private String password;
	    private String email;
	    private String role;
	    private LocalDateTime createdAt;
	    private LocalDateTime updatedAt;
	    
	    @ElementCollection(fetch = FetchType.EAGER)
	    private Set<String> roles;
	    
	    @ElementCollection(fetch = FetchType.EAGER)
	    @Convert(converter = SimpleGrantedAuthorityConverter.class)
	    private Set<SimpleGrantedAuthority> authorities;

	    
	    
	    
		public Set<SimpleGrantedAuthority> getAuthorities() {
			return authorities;
		}
		public void setAuthorities(Set<SimpleGrantedAuthority> authorities) {
			this.authorities = authorities;
		}
		public Set<String> getRoles() {
			return roles;
		}
		public void setRoles(Set<String> roles) {
			this.roles = roles;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getRole() {
			return role;
		}
		public void setRole(String role) {
			this.role = role;
		}
		public LocalDateTime getCreatedAt() {
			return createdAt;
		}
		public void setCreatedAt(LocalDateTime createdAt) {
			this.createdAt = createdAt;
		}
		public LocalDateTime getUpdatedAt() {
			return updatedAt;
		}
		public void setUpdatedAt(LocalDateTime updatedAt) {
			this.updatedAt = updatedAt;
		}
		
		
		// Default constructor
	    public User() {
	        // This is intentionally left empty
	    }
	    
		public User(Long id, String username, String password, String email, String role, LocalDateTime createdAt,
				LocalDateTime updatedAt) {
			super();
			this.id = id;
			this.username = username;
			this.password = password;
			this.email = email;
			this.role = role;
			this.createdAt = createdAt;
			this.updatedAt = updatedAt;
		}
		@Override
		public String toString() {
			return "User [id=" + id + ", username=" + username + ", password=" + password + ", email=" + email
					+ ", role=" + role + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
		}

		
	    
	}
