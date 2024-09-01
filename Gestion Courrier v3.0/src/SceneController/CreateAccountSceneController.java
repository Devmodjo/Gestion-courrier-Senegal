package SceneController;

import java.io.IOException;

import DBManager.DBManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateAccountSceneController {
	
	@FXML
	private TextField usernameInput;
	@FXML
	private PasswordField passwordInput;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	private Connection con = DBManager.connect();
	
	
	public void CreateAccount(ActionEvent event) {
		
		String name_user = usernameInput.getText().toString();
		String password_user = passwordInput.getText().toString();
		
		if(name_user.isEmpty() || password_user.isEmpty()) 
		{
			System.out.println("les champs sont vides");
			errorAlertBox("InputEmpty", "Veuillez remplir tous les champs");
			
		} else {
			
			if(password_user.length() < 8) {
				errorAlertBox("Badpassword", "mot de passe requiert au moins 8 caractère");
			}
			else {
				
				String req = "INSERT INTO user(name_user, password_user) VALUES(?,?)";
				
				try {
					
					PreparedStatement ps = con.prepareStatement(req);
					ps.setString(1, name_user);
					ps.setString(2, password_user);
					ps.execute();
					
					ps.close();
					con.close();
					infoAlertBox("account create", "nouveaux compte creee succès");
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		
	}
	
	public void switchtoLoginScene(ActionEvent event) throws IOException {
		
		root = FXMLLoader.load(getClass().getResource("./../View/LoginScene.fxml"));
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	public void errorAlertBox(String title, String contain) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(contain);
		alert.showAndWait();
	}
	
	public void infoAlertBox(String title, String contain) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(contain);
		alert.showAndWait();
	}
	
}
