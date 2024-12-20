package controller;

import dao.UserDAO;
import exception.AuthException;
import exception.FormException;
import model.User;
import util.RouteManager;
import util.SessionManager;
import util.StringHelper;

public class AuthController {
	private UserDAO dao;

	public AuthController() {
		this.dao = new UserDAO();
	}

	public void login(String email, String password) throws AuthException, FormException {
		if (email.isBlank())
			throw new FormException("Email cannot be empty");
		if (password.isBlank())
			throw new FormException("Password cannot be empty");

		User user = dao.getByEmail(email);

		if (user != null && user.getPassword().equals(password)) {
			SessionManager.login(user);

			if (user.getRole().equalsIgnoreCase("admin")) {
				RouteManager.navigate("admin_home");
			} else {
				RouteManager.navigate("customer_home");
			}

			return;
		}

		throw new AuthException("Wrong credentials");
	}

	public void register(User user) throws AuthException, FormException {
		if (user.getUsername().isBlank()) {
			throw new FormException("Username cannot be empty");
		}

		if (user.getUsername().length() <= 3 || user.getUsername().length() >= 15) {
			throw new FormException("Username must be between 3 - 15 characters");
		}

		if (user.getEmail().isBlank()) {
			throw new FormException("Email cannot be empty");
		}

		if (!user.getEmail().endsWith("@gmail.com")) {
			throw new FormException("Email must end with '@gmail.com'");
		}

		if (user.getPassword().isBlank()) {
			throw new FormException("Password cannot be empty");
		}

		if (!StringHelper.isAlphanumeric(user.getPassword())) {
			throw new FormException("Password must be alphanumeric");
		}

		if (user.getAge() <= 13) {
			throw new FormException("Age must be older than 13 years");
		}

		if (user.getPhoneNumber().isBlank()) {
			throw new FormException("Phone Number cannot be empty");
		}

		if (user.getPhoneNumber().length() >= 15) {
			throw new FormException("Phone Number must be less than 15 characters long");
		}

		if (!StringHelper.isNumeric(user.getPhoneNumber())) {
			throw new FormException("Phone Number must be numeric");
		}

		if (dao.create(user) != null) {
			RouteManager.navigate("login");
			return;
		}

		throw new AuthException("Error creating new user");
	}

	public void logout() {
		SessionManager.logout();
		RouteManager.navigate("login");
	}

}
