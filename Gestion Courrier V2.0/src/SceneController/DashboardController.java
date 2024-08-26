package SceneController;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import DBManager.DBManager;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;

public class DashboardController {
	
	// ---------- container courrier sortant
	
    @FXML
    private AnchorPane containerCourrierAdministratifEntrant;
    @FXML
    private AnchorPane containerCourrierConfidentielEntrant;
    @FXML
    private AnchorPane containerCourrierOrdinaireEntrant;
    
    // ---------- container courrier sortant
    
    @FXML
    private AnchorPane containerCourrierAdministratifSortant;
    @FXML
    private AnchorPane containerCourrierConfidentielSortant;
    @FXML
    private AnchorPane containerCourrierOrdinaireSortant;

    //----------- controller courrier entrant
    
    @FXML
    private Label maxCourrier;
    @FXML
    private Label maxCourrier1;
    @FXML
    private Label maxCourrier2;
    @FXML
    private Label nbrCourrierAdmin;
    @FXML
    private Label nbrCourrierConfidentiel;
    @FXML
    private Label nbreCourrierOrdinaire;
    @FXML
    private ProgressBar progressBarCourrierAdministratif;
    @FXML
    private ProgressBar progressBarCourrierConfidentiel;
    @FXML
    private ProgressBar progressBarCourrierOrdinaire;
    @FXML
    private Label Showpercent; // ordinaire
    @FXML
    private Label Showpercent1; // confidentiel
    @FXML
    private Label Showpercent2; // administratif
    
    // -------------- controller courrier sortant
    
    @FXML
    private Label Showpercent3;
    @FXML
    private Label Showpercent4;
    @FXML
    private Label Showpercent5;
    @FXML
    private Label maxCourrierSortant0;
    @FXML
    private Label maxCourrierSortant1;
    @FXML
    private Label maxCourrierSortant2;
    @FXML
    private Label nbrCourrierAdminSortant;
    @FXML
    private Label nbrCourrierConfidentielSortant;
    @FXML
    private Label nbreCourrierOrdinaireSortant;
    @FXML
    private ProgressBar progressBarCourrierOrdinaireSortant;
    @FXML
    private ProgressBar progressBarCourrierConfidentielSortant;
    @FXML
    private ProgressBar progressBarCourrierAdministratifSortant;
   


	@FXML
	private void initialize() {
		
		// afficher info relatif au courrier entrant 
		
		showMaxCourrierentrant();
		showNbreCourrierOrdinaire();
		showNbreCourrierConfidentiel();
		showNbreCourrierAdministratif();
		
		// afficher info relatif au courrier sortant
		
		showMaxCourrierSortant();
		showCourrierOrdinaireSortant();
		showCourrierConfidentielSortant();
		showCourrierAdminSortant();
	}
	
	public int maxNumberEntrant() throws SQLException {
		/* nombre total de courrier entrant */
		
		Connection con = DBManager.connect();
		String sql = "SELECT COUNT(*) FROM courrier_entrant";
		Statement stmt = con.createStatement();
		ResultSet res = stmt.executeQuery(sql);
		
		int i = res.getInt(1);
		
		res.close();
		stmt.close();
		con.close();
		
		return i;
		
	}
	
	public int maxNumberSortant() throws SQLException{
		/* nombre total de courrier sortant */
		
		Connection con = DBManager.connect();
		String sql = "SELECT COUNT(*) FROM courrier_sortant";
		Statement stmt = con.createStatement();
		ResultSet res = stmt.executeQuery(sql);
		
		int i = res.getInt(1);
		
		res.close();
		stmt.close();
		con.close();
		
		return i;
		
	}
	
	public void showMaxCourrierentrant(){
		try
		{
			this.maxCourrier.setText(Integer.toString(maxNumberEntrant()));
			this.maxCourrier1.setText(Integer.toString(maxNumberEntrant()));
			this.maxCourrier2.setText(Integer.toString(maxNumberEntrant()));
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
	
	// -------------------------- section courrier entrant
	
	public void showNbreCourrierOrdinaire() {
		
		try {
			
			Connection con = DBManager.connect();
			
			String sql = "SELECT COUNT(type_courrier) FROM "
						+ "courrier_entrant "
						+ "WHERE type_courrier='courrier ordinaire' ";
			
			Statement stmt = con.createStatement();
			ResultSet res = stmt.executeQuery(sql);
			
			
			this.nbreCourrierOrdinaire.setText(Integer.toString(res.getInt(1)));
			
			Double totalCourrierOridinaire = (double) res.getInt(1);
			int MaxCourrierEntrant = maxNumberEntrant();
			
			Double progress = (double) (totalCourrierOridinaire / MaxCourrierEntrant);
			
			
			this.progressBarCourrierOrdinaire.setProgress(progress);
			this.Showpercent.setText(Math.round(progress*100) + "%");
			
			stmt.close();
			res.close();
			con.close();
			
		}catch(Exception e ) {
			e.printStackTrace();
		}
		
	}
	
	public void showNbreCourrierConfidentiel() {
		try 
		{
			Connection con = DBManager.connect();
			
			String sql = "SELECT COUNT(type_courrier) FROM "
						+ "courrier_entrant "
						+ "WHERE type_courrier='courrier confidentiel' ";
			
			Statement stmt = con.createStatement();
			ResultSet res = stmt.executeQuery(sql);
			
			this.nbrCourrierConfidentiel.setText(Integer.toString(res.getInt(1)));
			
			Double totalCourrierConfidentiel = (double) res.getInt(1);
			int MaxCourrierEntrant = maxNumberEntrant();
			
			Double progress = (double) (totalCourrierConfidentiel / MaxCourrierEntrant);
			
			
			this.progressBarCourrierConfidentiel.setProgress(progress);
			this.Showpercent1.setText(Math.round(progress*100) + "%");
			
			stmt.close();
			res.close();
			con.close();
			
		}catch(Exception e ) {
			e.printStackTrace();
		}
	}

	public void showNbreCourrierAdministratif() {
		try 
		{
			Connection con = DBManager.connect();
			
			String sql = "SELECT COUNT(type_courrier) FROM "
						+ "courrier_entrant "
						+ "WHERE type_courrier='courrier administratif' ";
			
			Statement stmt = con.createStatement();
			ResultSet res = stmt.executeQuery(sql);
			
			this.nbrCourrierAdmin.setText(Integer.toString(res.getInt(1)));
			
			Double totalCourrierAdmin = (double) res.getInt(1);
			int MaxCourrierEntrant = maxNumberEntrant();
			
			Double progress = (double) (totalCourrierAdmin / MaxCourrierEntrant);
			
			
			this.progressBarCourrierAdministratif.setProgress(progress);
			this.Showpercent2.setText(Math.round(progress*100) + "%");
			
			stmt.close();
			res.close();
			con.close();
			
		}catch(Exception e ) {
			e.printStackTrace();
		}
		
	}
	
	// ------------------------------ section des courrier sortant
	
	public void showMaxCourrierSortant() {
		try 
		{
			maxCourrierSortant0.setText(Integer.toString(maxNumberSortant()));
			maxCourrierSortant1.setText(Integer.toString(maxNumberSortant()));
			maxCourrierSortant2.setText(Integer.toString(maxNumberSortant()));
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void showCourrierOrdinaireSortant() {
		try 
		{
			Connection con = DBManager.connect();
			
			String sql = "SELECT COUNT(type_courrier) FROM "
						+ "courrier_sortant "
						+ "WHERE type_courrier='courrier ordinaire' ";
			
			Statement stmt = con.createStatement(); 
			ResultSet res = stmt.executeQuery(sql);
			
			
			this.nbreCourrierOrdinaireSortant.setText(Integer.toString(res.getInt(1)));
			
			Double totalCourrierOridinaireSortant = (double) res.getInt(1);
			int MaxCourrierSortant = maxNumberSortant();
			
			Double progress = (double) (totalCourrierOridinaireSortant / MaxCourrierSortant);
			
			
			this.progressBarCourrierOrdinaireSortant.setProgress(progress);
			this.Showpercent3.setText(Math.round(progress*100) + "%");
			
			stmt.close();
			res.close();
			con.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void showCourrierConfidentielSortant() {
		try 
		{
			Connection con = DBManager.connect();
			
			String sql = "SELECT COUNT(type_courrier) FROM "
						+ "courrier_sortant "
						+ "WHERE type_courrier='courrier confidentiel' ";
			
			Statement stmt = con.createStatement();
			ResultSet res = stmt.executeQuery(sql);
			
			
			this.nbrCourrierConfidentielSortant.setText(Integer.toString(res.getInt(1)));
			
			Double totalConfidentielSortant = (double) res.getInt(1);
			int MaxCourrierSortant = maxNumberSortant();
			
			Double progress = (double) (totalConfidentielSortant / MaxCourrierSortant);
			
			
			this.progressBarCourrierConfidentielSortant.setProgress(progress);
			this.Showpercent4.setText(Math.round(progress*100) + "%");
			
			stmt.close();
			res.close();
			con.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void showCourrierAdminSortant() {
		try 
		{
		
			Connection con = DBManager.connect();
			
			String sql = "SELECT COUNT(type_courrier) FROM "
						+ "courrier_sortant "
						+ "WHERE type_courrier='courrier administratif' ";
			
			Statement stmt = con.createStatement();
			ResultSet res = stmt.executeQuery(sql);
			
			
			this.nbrCourrierAdminSortant.setText(Integer.toString(res.getInt(1)));
			
			Double totalAdminSortant = (double) res.getInt(1);
			int MaxCourrierSortant = maxNumberSortant();
			
			Double progress = (double) (totalAdminSortant / MaxCourrierSortant);
			
			
			this.progressBarCourrierAdministratifSortant.setProgress(progress);
			this.Showpercent5.setText(Math.round(progress*100) + "%");
			
			stmt.close();
			res.close();
			con.close();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
 