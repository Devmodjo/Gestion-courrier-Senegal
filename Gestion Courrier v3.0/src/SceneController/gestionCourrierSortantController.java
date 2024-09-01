package SceneController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.DateTimeException;
import java.time.LocalDate;

import DBManager.DBManager;
import Model.courrierEntrant;
import Model.courrierSortant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class gestionCourrierSortantController {

	public gestionCourrierSortantController() {
		// contructeur sans info
	}
	
	// apparaitre la liste des categorie de courrier sortant
	@FXML
	private Button spawnCategorieCourrierSortant;
	
	/* action principale */
	@FXML
    private Button EnregistrerBtn;
    @FXML
    private Button ModifierBtn;
    @FXML
    private Button SupprimerBtn;
    
    /* champs de saisie */
    @FXML
    private TextField adresseDestinataireInput;
    @FXML
    private TextField adresseExpediteurInput;
    @FXML
    private TextArea descriptionInput;
    @FXML
    private TextField natureCourrierInput;
    @FXML
    private TextField nomCourrierInput;
    @FXML
    private TextField nomDestinataireInput;
    @FXML
    private TextField nomExpediteurInput;
    @FXML
    private TextField piodInput;
    @FXML
    private TextField searchBar;
    
    /* variable de gestion de la TableView */
    @FXML
    private TableView<courrierSortant> TableViewCourrier;
    @FXML
    private TableColumn<courrierSortant, String> columAdresseDestinataire;
    @FXML
    private TableColumn<courrierSortant, String> columAdresseExpediteur;
    @FXML
    private TableColumn<courrierSortant, String> columDescripition;
    @FXML
    private TableColumn<courrierSortant, String> columNatureCourrier;
    @FXML
    private TableColumn<courrierSortant, String> columNomCourrier;
    @FXML
    private TableColumn<courrierSortant, String> columNomDestinataire;
    @FXML
    private TableColumn<courrierSortant, String> columNomExpediteur;
    @FXML
    private TableColumn<courrierSortant, Double> columPoid;
    @FXML
    private TableColumn<courrierSortant, String> columTypeCourrier;
    @FXML
    private TableColumn<courrierSortant, String> columnDate;
    @FXML
    private TableColumn<courrierSortant, Integer> columnID;
    
    // datepicker
    @FXML
    private DatePicker dateInput;
   
    @FXML
    private ComboBox<String> typeCourrier = new ComboBox<String>();
    
    // connection a la base de donnée
    private Connection con = null;
    
    @SuppressWarnings("unused")
	private courrierSortant courrierSortant;
    
	
    @FXML
    private void initialize() {
    	
    	// table name
    	TableViewCourrier.setPlaceholder(new Label("aucun Courrier sortant enregistrer"));
    	
    	// initialisation de la methide d'ajout des composant dans le tableaux
    	updateTableView();
    	
    	// initialisation des valeur de la comboBox
    	typeCourrier.getItems().addAll("courrier ordinaire", "courrier confidentiel", "courrier administratif");
    	typeCourrier.setValue("courrier ordinaire");
    	
    	ModifierBtn.setDisable(true);
		SupprimerBtn.setDisable(true);
		
		searchBar.textProperty().addListener((ObservableList, oldValue, newValue)->{
		try {
			filterData(newValue);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		});
    }
    
    @FXML
    public void insertData(ActionEvent event) {
    	
    	// connection a la DB
    	con = DBManager.connect();
    	
    	// recuperation des champ
        String nomExpediteur = nomExpediteurInput.getText().toString();
        String adresseExpediteur = adresseExpediteurInput.getText().toString();
        String nomDestinataire = nomDestinataireInput.getText().toString();
        String adresseDestinataire = adresseDestinataireInput.getText().toString();
        String natureCourrier = natureCourrierInput.getText().toString();
        String typCourrierBox = typeCourrier.getValue().toString();
    	String nomCourrier = nomCourrierInput.getText().toString();
    	String description = descriptionInput.getText().toString();
    	String poidStr = piodInput.getText();
    	LocalDate mydate = dateInput.getValue();
    	

    	if (mydate == null) {
    		errorAlertBox("DateFieldEmpty", "vous la date est obligatoire");
    	}else {
    		String date = mydate.toString();
    		
    		String fieldInputs[] = {
        			nomCourrier,
        			nomExpediteur,
        			adresseExpediteur,
        			nomDestinataire,
        			adresseDestinataire,
        			natureCourrier,
        			typCourrierBox,
        			poidStr,
        			date,
        			description
        	};
    		
    		if(fieldInputs[0].isEmpty() || fieldInputs[1].isEmpty() || fieldInputs[2].isEmpty() ||  fieldInputs[4].isEmpty() || fieldInputs[5].isEmpty() || fieldInputs[6].isEmpty() || fieldInputs[7].isEmpty() || fieldInputs[8].isEmpty() || fieldInputs[9].isEmpty()) 
        	{
        		errorAlertBox("EmptyValue", "veuillez remplir tout les champs");
        		
        	} else {
        		try 
        		{
        			double poidDouble = Double.parseDouble(poidStr);
        			
        			// instance d'un nouveaux courrier entrant // 
        			courrierEntrant newCourrierEntrant = new courrierEntrant
        					(
	        					nomCourrier, 
	        					natureCourrier, 
	        					nomExpediteur, 
	        					adresseExpediteur, 
	        					nomDestinataire, 
	        					adresseDestinataire, 
	        					poidDouble, 
	        					description, 
	        					date, 
	        					typCourrierBox
        					);
        			
        			// preparation de la requete
        			String sql = "INSERT INTO courrier_sortant(nom_courrier, nature_courrier, nom_expediteur, adresse_expediteur, nom_destinataire, adresse_destinataire, poids, description_courrier, date_expedition, type_courrier) VALUES(?,?,?,?,?,?,?,?,?,?)";
        			PreparedStatement ps = con.prepareStatement(sql);
        			
        			ps.setString(1, newCourrierEntrant.getNomCourrier());
        			ps.setString(2, newCourrierEntrant.getNatureCourrier());
        			ps.setString(3, newCourrierEntrant.getNomExpediteur());
        			ps.setString(4, newCourrierEntrant.getAdresseExpediteur());
        			ps.setString(5, newCourrierEntrant.getNomDestinataire());
        			ps.setString(6, newCourrierEntrant.getAdresseDestinataire());
        			ps.setDouble(7, newCourrierEntrant.getPoid());
        			ps.setString(8, newCourrierEntrant.getDescriptionCourrier());
        			ps.setString(9, newCourrierEntrant.getDate());
        			ps.setString(10, newCourrierEntrant.getTypeCourrier());
        			
        			// execution de la requete
        			ps.executeUpdate();
        			
        			// mise a jour de la table de donnée
        			updateTableView();
        			
        			System.out.println("entite " + nomCourrier + " enregistrer avec succes");
        			infoAlertBox("DataRegistred", "nouveau courrier enregistrer !");
        			
        			// fermeture de la connection
        			ps.close();
        			con.close();
        		}
        		catch(NumberFormatException e) {
        			errorAlertBox("ValueError", "Valeur du poid incorrect");
        		} catch (SQLException e) {
					errorAlertBox("QueryError", "ERREUR : " + e.getMessage());
					e.printStackTrace();
				}
        		
        	}
    	}
    	
    }
    
    
    
    @FXML
    public void updateTableView() {
    	
    	/* methode permettant de faire des mise a jour sur mon tableau de donnée */
    	try {
    		
			ObservableList<courrierSortant> list = getCourrierSortantList();
			
			// affectation des valeur e chaque colone
			columnID.setCellValueFactory(new PropertyValueFactory<courrierSortant, Integer>("id"));
			columNomCourrier.setCellValueFactory(new PropertyValueFactory<courrierSortant, String>("nomCourrier")); 
			columNatureCourrier.setCellValueFactory(new PropertyValueFactory<courrierSortant, String>("natureCourrier")); 
			columNomExpediteur.setCellValueFactory(new PropertyValueFactory<courrierSortant, String>("nomExpediteur")); 
			columAdresseExpediteur.setCellValueFactory(new PropertyValueFactory<courrierSortant, String>("adresseExpediteur")); 
			columNomDestinataire.setCellValueFactory(new PropertyValueFactory<courrierSortant, String>("nomDestinataire")); 
			columAdresseDestinataire.setCellValueFactory(new PropertyValueFactory<courrierSortant, String>("adresseDestinataire")); 
			columPoid.setCellValueFactory(new PropertyValueFactory<courrierSortant, Double>("poid")); 
			columDescripition.setCellValueFactory(new PropertyValueFactory<courrierSortant, String>("descriptionCourrier"));
			columTypeCourrier.setCellValueFactory(new PropertyValueFactory<courrierSortant, String>("typeCourrier"));
			columnDate.setCellValueFactory(new PropertyValueFactory<courrierSortant, String>("date"));
			// ajout des element dans le tableaux
			TableViewCourrier.setItems(list);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    public void mouseClicked() {
    	try {
    		
    		courrierSortant courrierSortant = TableViewCourrier.getSelectionModel().getSelectedItem();
    		
    		courrierSortant = new courrierSortant(
    				
    				courrierSortant.getId(), 
    				courrierSortant.getNomCourrier(), 
    				courrierSortant.getNatureCourrier(),
    				courrierSortant.getNomExpediteur(), 
    				courrierSortant.getAdresseExpediteur(), 
    				courrierSortant.getNomDestinataire(),
    				courrierSortant.getAdresseDestinataire(),
    				courrierSortant.getPoid(),
    				courrierSortant.getDescriptionCourrier(), 
    				courrierSortant.getDate(), 
    				courrierSortant.getTypeCourrier()
    				);
    		this.courrierSortant = courrierSortant;
    		
    		nomCourrierInput.setText(courrierSortant.getNomCourrier());
    		natureCourrierInput.setText(courrierSortant.getNatureCourrier());
    		nomExpediteurInput.setText(courrierSortant.getNomExpediteur());
    		adresseExpediteurInput.setText(courrierSortant.getAdresseExpediteur());
    		nomDestinataireInput.setText(courrierSortant.getNomDestinataire());
    		adresseDestinataireInput.setText(courrierSortant.getAdresseDestinataire());
    		piodInput.setText(Double.toString(courrierSortant.getPoid()));
    		descriptionInput.setText(courrierSortant.getDescriptionCourrier());
    		dateInput.setValue(LocalDate.now());
    		typeCourrier.setValue(courrierSortant.getTypeCourrier());
    		
    		ModifierBtn.setDisable(false);
    		SupprimerBtn.setDisable(false);
    		
    	} catch(Exception err) {
    		
    	}
    }
    
    
    /* les chose comme ca c'est une classe externe */
    public ObservableList<courrierSortant> getCourrierSortantList() throws SQLException{
    	/**
    	 * une ObeservableList (liste observable)  est une extension de la classe
    	 * java.util.List  mais permetant de notifier les ecouteur
    	 * (mettre a jour une liste lorsque elle est modifier)
    	 * 
    	 * */
    	Connection con = DBManager.connect(); 
    	ObservableList<courrierSortant> listCourrierSortant = FXCollections.observableArrayList();
    	
    	
    	try {
    		
    		// selectionnner tous les courrier entrant dans la base de donnée
    		String sql2 = "SELECT * FROM courrier_sortant";
    		Statement stmt2 = con.createStatement();
    		ResultSet rs2 = stmt2.executeQuery(sql2);
    		
			while(rs2.next()) {
				
				
				courrierSortant courrier2 = new courrierSortant(
    					rs2.getInt("id_courrier"), 
    					rs2.getString("nom_courrier"), 
    					rs2.getString("nature_courrier"), 
    					rs2.getString("nom_expediteur"),
    					rs2.getString("adresse_expediteur"),
    					rs2.getString("nom_destinataire"),
    					rs2.getString("adresse_destinataire"),
    					rs2.getDouble("poids"),
    					rs2.getString("description_courrier"),
    					rs2.getString("date_expedition"),
    					rs2.getString("type_courrier")
    					);
    			// ajout des element dans le tableau
    			listCourrierSortant.add(courrier2);
			}
    			
			// fermer les outils
			//stmt1.close();
			//rs.close();
			stmt2.close();
			rs2.close();
			con.close();
    		
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
    	
    	
		return listCourrierSortant;
    	
    }
    
    
    @FXML
    public void modifyCourrierEntrant(ActionEvent event) {
    	/* Gestion des mpdification sur un courrier */
    	try {
    		con = DBManager.connect();
    		PreparedStatement ps = con.prepareStatement("UPDATE courrier_sortant SET "
    				+ "nom_courrier = ?, "
    				+ "nature_courrier = ?, "
    				+ "nom_expediteur = ?, "
    				+ "adresse_expediteur = ?, "
    				+ "nom_destinataire = ?, "
    				+ "adresse_destinataire = ?, "
    				+ "poids = ?, "
    				+ "description_courrier = ?, "
    				+ "date_expedition = ?, "
    				+ "type_courrier = ? "
    				+ "WHERE id_courrier = ?");
    		
    		// reVerification des champs
    		try {
    			
    			try { 
    				
    				Double poidDbl = Double.parseDouble(piodInput.getText());
    				
    				try { 
            			
            			if(dateInput.getValue().toString() == null) 
            			{
            				errorAlertBox("EmptyField", "la date est obligatoire");
            				
            			} else if (piodInput.getText().isEmpty()) 
            			{
            				errorAlertBox("EmptyField", "veuillez renseigner un poid");
            			}
            			else {
            				
            				courrierSortant courrierSortant = new courrierSortant(
               					 this.courrierSortant.getId(), 
               					 nomCourrierInput.getText(), 
               					 natureCourrierInput.getText(),
               					 nomExpediteurInput.getText(), 
               					 adresseExpediteurInput.getText(), 
               					 nomDestinataireInput.getText(), 
               					 adresseDestinataireInput.getText(),
               					 poidDbl,
               					 descriptionInput.getText(),
               					 dateInput.getValue().toString(),
               					 typeCourrier.getValue()
               					);
            		
            				System.out.println("ID : " + this.courrierSortant.getId());
            				
            				ps.setString(1, courrierSortant.getNomCourrier());
                    		ps.setString(2, courrierSortant.getNatureCourrier());
                    		ps.setString(3, courrierSortant.getNomExpediteur());
                    		ps.setString(4, courrierSortant.getAdresseExpediteur());
                    		ps.setString(5, courrierSortant.getNomDestinataire());
                    		ps.setString(6, courrierSortant.getAdresseDestinataire());
            				ps.setDouble(7, courrierSortant.getPoid());
            				ps.setString(8, courrierSortant.getDescriptionCourrier());
            				ps.setString(9, courrierSortant.getDate());
            				ps.setString(10, courrierSortant.getTypeCourrier());
            				ps.setInt(11, courrierSortant.getId());
            				
            				ps.executeUpdate();
            				updateTableView();
            				ClearField();
            				infoAlertBox("ModicationOk", "Modification effectuer avec succes");
            				
            				ModifierBtn.setDisable(true);
            				SupprimerBtn.setDisable(true);
            			}
            				 
            			} catch ( DateTimeException e) { errorAlertBox("DateFormatExeption", "format de la date non valide");}
            		
    				
    			}catch(NumberFormatException  e) { 
    				errorAlertBox("NumberFormatError", "valeur du poid incorrect"); 
    				
    			}
        		
    			ps.close();
        		con.close();
        		
    		}catch(SQLException e) {
    			e.printStackTrace();
    		}
    		
    	}catch(Exception  e) {
    		e.printStackTrace();
    	} 
    	
    }

    @FXML 
    public void DeleteCourrier(ActionEvent event) {
    	
    	try {
    		
    		con = DBManager.connect();

    		
        	String sql = "DELETE FROM courrier_sortant WHERE id_courrier = ?";
        	
        	PreparedStatement ps = con.prepareStatement(sql);
        	
        	ps.setInt(1, this.courrierSortant.getId());
        	ps.execute();
        	
        	infoAlertBox("DeleteOk", "suppression de l'element effectuer avec succès");
        	updateTableView();
        	ClearField();
        	
        	ModifierBtn.setDisable(true);
    		SupprimerBtn.setDisable(true);
    		
        	ps.close();
			con.close();
			
		} catch (SQLException e) {
			 
			e.printStackTrace();
		}
    }
    
    public void ClearField() {
    	
    	nomCourrierInput.setText("");
		natureCourrierInput.setText("");
		nomExpediteurInput.setText("");
		adresseExpediteurInput.setText("");
		nomDestinataireInput.setText("");
		adresseDestinataireInput.setText("");
		descriptionInput.setText("");
		dateInput.setValue(null);
		piodInput.setText("");
		typeCourrier.setValue("courrier ordinaire");
    }
    
    
    // gestion de la barre de recherche
    public void filterData(String searchName) throws SQLException {
    
    	try {
    		
    		ObservableList<courrierSortant> filterData = FXCollections.observableArrayList(); 
			ObservableList<Model.courrierSortant> list  =  getCourrierSortantList();
			
			for(Model.courrierSortant courrierSortant : list) {
				if(courrierSortant.getNomCourrier().contains(searchName) 
						||courrierSortant.getNatureCourrier().contains(searchName)
						||courrierSortant.getNomExpediteur().contains(searchName)
						||courrierSortant.getAdresseExpediteur().contains(searchName)
						||courrierSortant.getNomDestinataire().contains(searchName)
						||courrierSortant.getAdresseDestinataire().contains(searchName)
						||courrierSortant.getDescriptionCourrier().contains(searchName)
						||courrierSortant.getDate().contains(searchName)
						||courrierSortant.getTypeCourrier().contains(searchName)
						)
					filterData.add(courrierSortant);
			}
			
			TableViewCourrier.setItems(filterData);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    private void spawnListCategorie(ActionEvent event) {
    	
    	try {
    		Parent root = FXMLLoader.load(getClass().getResource("./../View/ListDashCourrierSortant.fxml"));
    		Scene scene = new Scene(root);
    		Stage stage = new Stage();
    		stage.setScene(scene);
    		stage.setTitle("Liste categorie");
    		stage.setResizable(false);
    		stage.initModality(Modality.APPLICATION_MODAL);
    		stage.showAndWait();
    		
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
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
