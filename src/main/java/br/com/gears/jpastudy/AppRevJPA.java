package br.com.gears.jpastudy;

import java.util.List;

import br.com.gears.jpastudy.dao.UserDAO;
import br.com.gears.jpastudy.entity.User;

public class AppRevJPA {
	
	
	public static void main(String[] args) {
		insertUser();
		findUserById();
		findAll();
		countUsers();
	}
	
	private static void findUserById(){
		User user = new UserDAO().findById(5L);
		User anotherUser = new UserDAO().findById(6L);
		System.out.println(user);
		System.out.println(anotherUser);		
	}
	
	private static void findAll(){
		List<User> users = new UserDAO().findAll();
		for(User user : users){
			System.out.println(user.toString());
		}
	}
	
	private static void countUsers(){
		Long total = new UserDAO().count();
		System.out.println("there is " + total + " users in the database");
	}

	private static void insertUser() {
		User user = new User();
		user.setName("John");
		user.setLastName("Doe");
		user.setLogin("john_doe");
		user.setPass("123456");
		
		UserDAO dao = new UserDAO();
		dao.save(user);
		System.out.println(user.toString());
		
		user = new User();
		user.setName("Johnata");
		user.setLastName("Doew");
		user.setLogin("john_doew");
		user.setPass("123456");
		
		dao = new UserDAO();
		dao.save(user);
		System.out.println(user.toString());
		
		user = new User();
		user.setName("poe");
		user.setLastName("Doe");
		user.setLogin("poe_doe");
		user.setPass("123456");
		
		dao = new UserDAO();
		dao.save(user);
		System.out.println(user.toString());

	}

}
