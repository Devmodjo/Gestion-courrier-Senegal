package SceneController;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;



public class MainSceneController {
	
	@FXML
    private Button BtnEntrant;

    @FXML
    private Button BtnSortant;
    
    @FXML
    private Button btnTableauBord;

    @FXML
    private AnchorPane mainBoxPane;

    @FXML
    private Label welcomelabel;
    
	@SuppressWarnings("unused")
	private LoginSceneController.User user;

    public void setUser(LoginSceneController.User user) {
        this.user = user;
        welcomelabel.setText("Bienvenue " + user.getUsername());
        
    }
    
    @FXML
    private void initialize() {
    	/* charger le contenu par defaut de la mainBox */
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("./../View/gestEntrantBox.fxml"));
    	try {
			Parent root = loader.load();
			mainBoxPane.getChildren().add(root);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    @FXML
    public void switchToSortantScene(ActionEvent event) {
    	
    	/* 
    	 * charger la scene du gestion de courrier
    	 *  sortant dans le conteneur de gestion 
    	 *  de courrier entrant */
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("./../View/gestSortantBox.fxml"));
    	try {
			Parent root = loader.load();
			mainBoxPane.getChildren().clear();
			mainBoxPane.getChildren().add(root);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
     
    @FXML
    public void switchToEntrantBox(ActionEvent event) {
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("./../View/gestEntrantBox.fxml"));
    	try {
			Parent root = loader.load();
			mainBoxPane.getChildren().clear();
			mainBoxPane.getChildren().add(root);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    public void switchToDashBoard(ActionEvent event) {
    	
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("./../View/TableauBord.fxml"));
    	try {
			Parent root = loader.load();
			mainBoxPane.getChildren().clear();
			mainBoxPane.getChildren().add(root);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

}
