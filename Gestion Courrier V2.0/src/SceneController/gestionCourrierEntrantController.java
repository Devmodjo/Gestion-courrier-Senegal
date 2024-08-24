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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

public class gestionCourrierEntrantController{

	
	public gestionCourrierEntrantController() {
		// contructeur sans info
	}
	
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
    private TableView<courrierEntrant> TableViewCourrier;
    @FXML
    private TableColumn<courrierEntrant, String> columAdresseDestinataire;
    @FXML
    private TableColumn<courrierEntrant, String> columAdresseExpediteur;
    @FXML
    private TableColumn<courrierEntrant, String> columDescripition;
    @FXML
    private TableColumn<courrierEntrant, String> columNatureCourrier;
    @FXML
    private TableColumn<courrierEntrant, String> columNomCourrier;
    @FXML
    private TableColumn<courrierEntrant, String> columNomDestinataire;
    @FXML
    private TableColumn<courrierEntrant, String> columNomExpediteur;
    @FXML
    private TableColumn<courrierEntrant, Double> columPoid;
    @FXML
    private TableColumn<courrierEntrant, String> columTypeCourrier;
    @FXML
    private TableColumn<courrierEntrant, String> columnDate;
    @FXML
    private TableColumn<courrierEntrant, Integer> columnID;
    
    // datepicker
    @FXML
    private DatePicker dateInput;
   
    @FXML
    private ComboBox<String> typeCourrier = new ComboBox<String>();
    
    // connection a la base de donnée
    private Connection con = null;
    
    @SuppressWarnings("unused")
	private courrierEntrant courrierEntrant;
    
	
    @FXML
    private void initialize() {
    	
    	// table name
    	TableViewCourrier.setPlaceholder(new Label("aucun Courrier Entrant enregistrer"));
    	
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
        			String sql = "INSERT INTO courrier_entrant(nom_courrier, nature_courrier, nom_expediteur, adresse_expediteur, nom_destinataire, adresse_destinataire, poids, description_courrier, date_expedition, type_courrier) VALUES(?,?,?,?,?,?,?,?,?,?)";
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
    		
			ObservableList<courrierEntrant> list = getCourrierEntrantList();
			
			// affectation des valeur e chaque colone
			columnID.setCellValueFactory(new PropertyValueFactory<courrierEntrant, Integer>("id"));
			columNomCourrier.setCellValueFactory(new PropertyValueFactory<courrierEntrant, String>("nomCourrier")); 
			columNatureCourrier.setCellValueFactory(new PropertyValueFactory<courrierEntrant, String>("natureCourrier")); 
			columNomExpediteur.setCellValueFactory(new PropertyValueFactory<courrierEntrant, String>("nomExpediteur")); 
			columAdresseExpediteur.setCellValueFactory(new PropertyValueFactory<courrierEntrant, String>("adresseExpediteur")); 
			columNomDestinataire.setCellValueFactory(new PropertyValueFactory<courrierEntrant, String>("nomDestinataire")); 
			columAdresseDestinataire.setCellValueFactory(new PropertyValueFactory<courrierEntrant, String>("adresseDestinataire")); 
			columPoid.setCellValueFactory(new PropertyValueFactory<courrierEntrant, Double>("poid")); 
			columDescripition.setCellValueFactory(new PropertyValueFactory<courrierEntrant, String>("descriptionCourrier"));
			columTypeCourrier.setCellValueFactory(new PropertyValueFactory<courrierEntrant, String>("typeCourrier"));
			columnDate.setCellValueFactory(new PropertyValueFactory<courrierEntrant, String>("date"));
			// ajout des element dans le tableaux
			TableViewCourrier.setItems(list);
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    public void mouseClicked() {
    	try {
    		
    		courrierEntrant courrierEntrant = TableViewCourrier.getSelectionModel().getSelectedItem();
    		
    		courrierEntrant = new courrierEntrant(
    				
    				courrierEntrant.getId(), 
    				courrierEntrant.getNomCourrier(), 
    				courrierEntrant.getNatureCourrier(),
    				courrierEntrant.getNomExpediteur(), 
    				courrierEntrant.getAdresseExpediteur(), 
    				courrierEntrant.getNomDestinataire(),
    				courrierEntrant.getAdresseDestinataire(),
    				courrierEntrant.getPoid(),
    				courrierEntrant.getDescriptionCourrier(), 
    				courrierEntrant.getDate(), 
    				courrierEntrant.getTypeCourrier()
    				);
    		this.courrierEntrant = courrierEntrant;
    		
    		nomCourrierInput.setText(courrierEntrant.getNomCourrier());
    		natureCourrierInput.setText(courrierEntrant.getNatureCourrier());
    		nomExpediteurInput.setText(courrierEntrant.getNomExpediteur());
    		adresseExpediteurInput.setText(courrierEntrant.getAdresseExpediteur());
    		nomDestinataireInput.setText(courrierEntrant.getNomDestinataire());
    		adresseDestinataireInput.setText(courrierEntrant.getAdresseDestinataire());
    		// piodInput.(courrierEntrant.getPoid());
    		descriptionInput.setText(courrierEntrant.getDescriptionCourrier());
    		dateInput.setValue(LocalDate.now());
    		
    		ModifierBtn.setDisable(false);
    		SupprimerBtn.setDisable(false);
    		
    	} catch(Exception err) {
    		
    	}
    }
    
    
    /* les chose comme ca c'est une classe externe */
    public ObservableList<courrierEntrant> getCourrierEntrantList() throws SQLException{
    	/**
    	 * une ObeservableList (liste observable)  est une extension de la classe
    	 * java.util.List  mais permetant de notifier les ecouteur
    	 * (mettre a jour une liste lorsque elle est modifier)
    	 * 
    	 * */
    	Connection con = DBManager.connect(); 
    	ObservableList<courrierEntrant> listCourrierEntrant = FXCollections.observableArrayList();
    	
    	
    	try {
    		
    		// selectionnner tous les courrier entrant dans la base de donnée
    		String sql2 = "SELECT * FROM courrier_entrant";
    		Statement stmt2 = con.createStatement();
    		ResultSet rs2 = stmt2.executeQuery(sql2);
    		
			while(rs2.next()) {
				
				
    			courrierEntrant courrier2 = new courrierEntrant(
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
    			listCourrierEntrant.add(courrier2);
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
    	
    	
		return listCourrierEntrant;
    	
    }
    
    
    @FXML
    public void modifyCourrierEntrant(ActionEvent event) {
    	/* Gestion des mpdification sur un courrier */
    	try {
    		con = DBManager.connect();
    		PreparedStatement ps = con.prepareStatement("UPDATE courrier_entrant SET "
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
            				
            				courrierEntrant courrierEntrant = new courrierEntrant(
               					 this.courrierEntrant.getId(), 
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
            		
            				System.out.println("ID : " + this.courrierEntrant.getId());
            				
            				ps.setString(1, courrierEntrant.getNomCourrier());
                    		ps.setString(2, courrierEntrant.getNatureCourrier());
                    		ps.setString(3, courrierEntrant.getNomExpediteur());
                    		ps.setString(4, courrierEntrant.getAdresseExpediteur());
                    		ps.setString(5, courrierEntrant.getNomDestinataire());
                    		ps.setString(6, courrierEntrant.getAdresseDestinataire());
            				ps.setDouble(7, courrierEntrant.getPoid());
            				ps.setString(8, courrierEntrant.getDescriptionCourrier());
            				ps.setString(9, courrierEntrant.getDate());
            				ps.setString(10, courrierEntrant.getTypeCourrier());
            				ps.setInt(11, courrierEntrant.getId());
            				
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

    		
        	String sql = "DELETE FROM courrier_entrant WHERE id_courrier = ?";
        	
        	PreparedStatement ps = con.prepareStatement(sql);
        	
        	ps.setInt(1, this.courrierEntrant.getId());
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
    }
    
    
    // gestion de la barre de recherche
    public void filterData(String searchName) throws SQLException {
    
    	try {
    		
    		ObservableList<courrierEntrant> filterData = FXCollections.observableArrayList(); 
			ObservableList<courrierEntrant> list  =  getCourrierEntrantList();
			
			for(courrierEntrant courrierEntrant : list) {
				if(courrierEntrant.getNomCourrier().contains(searchName) 
						||courrierEntrant.getNatureCourrier().contains(searchName)
						||courrierEntrant.getNomExpediteur().contains(searchName)
						||courrierEntrant.getAdresseExpediteur().contains(searchName)
						||courrierEntrant.getNomDestinataire().contains(searchName)
						||courrierEntrant.getAdresseDestinataire().contains(searchName)
						||courrierEntrant.getDescriptionCourrier().contains(searchName)
						||courrierEntrant.getDate().contains(searchName)
						||courrierEntrant.getTypeCourrier().contains(searchName)
						)
					filterData.add(courrierEntrant);
			}
			
			TableViewCourrier.setItems(filterData);
			
		} catch (Exception e) {
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
