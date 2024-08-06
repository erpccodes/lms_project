package com.lms.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.lms.user.model.User;
import com.lms.user.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	 @Autowired
	    private UserService userService;

	    @GetMapping("/{username}")
	    public ResponseEntity<User> getUserProfile(@PathVariable String username) {
	        User user = userService.getUserByUsername(username);
	        return ResponseEntity.ok(user);
	    }

	    @PutMapping("/{username}")
	    public ResponseEntity<User> updateUserProfile(@PathVariable String username, @RequestBody User user) {
	        User updatedUser = userService.updateUser(username, user);
	        return ResponseEntity.ok(updatedUser);
	    }

	    @GetMapping("/me")
	    public ResponseEntity<User> getCurrentUserProfile() {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String currentUsername = extractUsernameFromAuthentication(authentication);
	        User user = userService.getUserByUsername(currentUsername);
	        return ResponseEntity.ok(user);
	    }

	    @PutMapping("/me")
	    public ResponseEntity<User> updateCurrentUserProfile(@RequestBody User user) {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        String currentUsername = extractUsernameFromAuthentication(authentication);
	        User updatedUser = userService.updateUser(currentUsername, user);
	        return ResponseEntity.ok(updatedUser);
	    }
	    
	    private String extractUsernameFromAuthentication(Authentication authentication) {
	        Object principal = authentication.getPrincipal();
	        if (principal instanceof UserDetails) {
	            return ((UserDetails) principal).getUsername();
	        }
	        return principal.toString();
	    }
}
