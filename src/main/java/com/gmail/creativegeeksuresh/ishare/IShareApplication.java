package com.gmail.creativegeeksuresh.ishare;

import com.gmail.creativegeeksuresh.ishare.model.Role;
import com.gmail.creativegeeksuresh.ishare.service.RoleService;
import com.gmail.creativegeeksuresh.ishare.service.util.AppConstants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class IShareApplication implements CommandLineRunner {

	@Autowired
	RoleService roleService;

	public static void main(String[] args) {
		SpringApplication.run(IShareApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// checking if all role exists otherwise adding them
		if (roleService.getAllRoles().size() == 0) {
			AppConstants.DEFAULT_ROLE_SET.forEach(roleName -> {
				try {
					Role role = new Role();
					role.setRoleName(roleName);
					roleService.createRole(role);
				} catch (Exception e) {
					System.err.println(e.getLocalizedMessage());
				}
			});
		}
	}
}
