package SceneController;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import DBManager.DBManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;


public class LoginSceneController {
	
	@FXML
	private TextField usernameInput;
	@FXML
	private PasswordField password_input;
	@FXML
	private Button loginButton;
	//  onMouseClicked="#switchtoCreateaccountScene"
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	private Connection con = DBManager.connect();
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	protected String name_user;
	protected String password_user;
	
	
	@SuppressWarnings("unused")
	public void LoginAccount(ActionEvent event) {
		
		name_user = usernameInput.getText().toString();
		password_user = password_input.getText().toString();
		
		//Model.User userPrimary = new Model.User(name_user, password_user);
		//gestionCourrierEntrantController courrierEntrantController = new gestionCourrierEntrantController(userPrimary);
		
		User user = login(name_user, password_user);
        if (user != null) {
            System.out.println("Login successful! User ID: " + user.getId() + ", Username: " + user.getUsername());

            // Load main application
            try {
                FXMLLoader loader =  new FXMLLoader(getClass().getResource("./../View/MainScene.fxml"));
                AnchorPane root = (AnchorPane) loader.load();

                // Pass user data to MainAppController
                MainSceneController mainSceneController = loader.getController();
                mainSceneController.setUser(user);

                Stage stage = (Stage) usernameInput.getScene().getWindow();
                stage.setTitle("Gestion Courrier");
                stage.setScene(new Scene(root));
                stage.setResizable(false);
                
                
                // Récupération de la résolution de l'écran
                Screen screen = Screen.getPrimary();
                Rectangle2D bounds = screen.getVisualBounds();

                // Calcul de la position pour centrer la fenêtre
                double x = (bounds.getWidth() - stage.getWidth()) / 2;
                double y = (bounds.getHeight() - stage.getHeight()) / 2;

                stage.setX(x);
                stage.setY(y);
                stage.show();
                // verification de la fermeture de la screen
                //stage.setOnCloseRequest(event->logout(stage));
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Login failed!");
            errorAlertBox("LoginError", "identifiant incorect");
        }
	}
	
	
	// backend verification handle login
	//@SuppressWarnings("unused")
	private User login(String username, String password) {
        String sql = "SELECT * FROM user WHERE name_user= ? AND password_user= ?";

        try {
        	
            pstmt = con.prepareStatement(sql);  
            pstmt.setString(1, username);
            pstmt.setString(2, password);

            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return new User(rs.getInt("id_user"), rs.getString("name_user"));
            } else {
                return null;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }finally {
        	
            try {
            	rs.close();
                pstmt.close();
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }
	
	
	public void switchtoCreateaccountScene(MouseEvent event) throws IOException {
		
		root = FXMLLoader.load(getClass().getResource("./../View/CreateaccountScene.fxml"));
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
	
	public String getName_user() {
		
		return usernameInput.getText().toString();
	}


	public void setName_user(String name_user) {
		this.name_user = name_user;
	}

	public String getPassword_user() {
		return password_input.getText().toString();
	}


	public void setPassword_user(String password_user) {
		this.password_user = password_user;
	}

	public class User{
		
		private int id;
        private String username;
        private String password;
        
        // 3
        public User(int id, String username) {
            this.id = id;
            this.username = username;
        }
        // 2
        public User(String username, String password) {
            this.username = username;
            this.password = password;
        }
        // 1
        public User(Integer id, String username, String password) {
            this.username = username;
            this.password = password;
            this.id = id;
        }

        public int getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }
		public String getPassword() {
			return password;
		}
        
	}
}
