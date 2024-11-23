package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Cart;

public class CartDAO extends DatabaseConfig {
	DonutDAO donutDAO = new DonutDAO();
	
	public void create(Cart cart) {
		String sql = "INSERT INTO cart (UserID, DonutID, Quantity) VALUES (?, ?, ?)";
		try {
			Connection conn = getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, cart.getUserID());
			stmt.setString(2, cart.getDonutID());
			stmt.setInt(3, cart.getQuantity());
			
			stmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public List<Cart> read(String userID) {
		List<Cart> carts = new ArrayList<>();
		String sql = "SELECT * FROM cart WHERE UserID = ?";
		
		try {
			Connection conn = getConnection();
			PreparedStatement stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, userID);
			ResultSet rs = stmt.executeQuery();
			
			while (rs.next()) {
				carts.add(new Cart(
					rs.getString("UserID"), 
					rs.getInt("Quantity"),
					donutDAO.getDonutByID(rs.getString("DonutID"))
				));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	      return carts;
	}

}
