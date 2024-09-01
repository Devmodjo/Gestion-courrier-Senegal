package SceneController;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import DBManager.DBManager;
import Model.courrierEntrant;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.*;
 
public class ListeDashCourrierEntrantController {

    @FXML
    private Button impression;    // Bouton pour courrier ordinaire
    @FXML
    private Button impression1;   // Bouton pour courrier confidentiel
    @FXML
    private Button impression2;   // Bouton pour courrier administratif

    @FXML
    private TabPane tabPane;      // TabPane qui contient les 3 tabs

    @FXML
    private Tab tabOrdinaire;     // Tab pour courrier ordinaire
    @FXML
    private Tab tabConfidentiel;  // Tab pour courrier confidentiel
    @FXML
    private Tab tabAdministratif; // Tab pour courrier administratif

    @FXML
    private TableView<courrierEntrant> TableViewCourrierOrdinaire;
    @FXML
    private TableView<courrierEntrant> TableViewCourrierConfidentiel;
    @FXML
    private TableView<courrierEntrant> TableViewCourrierAdminstratif;

    @FXML
    private TableColumn<courrierEntrant, Integer> IDColumOrdinaire;
    @FXML
    private TableColumn<courrierEntrant, Integer> IDColumConfidentiel;
    @FXML
    private TableColumn<courrierEntrant, Integer> IDColumAdminstratif;

    @FXML
    private TableColumn<courrierEntrant, String> nomCourrierColumOrdinaire;
    @FXML
    private TableColumn<courrierEntrant, String> nomCourrierColumConfidentiel;
    @FXML
    private TableColumn<courrierEntrant, String> nomCourrierColumAdminstratif;

    @FXML
    private TableColumn<courrierEntrant, String> nomExpediteurOrdinaire;
    @FXML
    private TableColumn<courrierEntrant, String> nomExpediteurConfidentiel;
    @FXML
    private TableColumn<courrierEntrant, String> nomExpediteurAdminstratif;

    @FXML
    private TableColumn<courrierEntrant, String> dateArriveColumOrdinaire;
    @FXML
    private TableColumn<courrierEntrant, String> dateArriveColumConfidentiel;
    @FXML
    private TableColumn<courrierEntrant, String> dateArriveColumAdminstratif;

    @FXML
    private TableColumn<courrierEntrant, String> objetColumOrdinaire;
    @FXML
    private TableColumn<courrierEntrant, String> objetColumConfidentiel;
    @FXML
    private TableColumn<courrierEntrant, String> objetColumAdminstratif;

    @FXML
    private void initialize() {
        // Initialiser les trois TableView
        updateTableViewCourrierOrdinaire();
        updateTableViewCourrierConfidentiel();
        updateTableViewCourrierAdministratif();

        // Actions des boutons d'impression
        impression.setOnAction(e -> printSelectedTableView(TableViewCourrierOrdinaire));
        impression1.setOnAction(e -> printSelectedTableView(TableViewCourrierConfidentiel));
        impression2.setOnAction(e -> printSelectedTableView(TableViewCourrierAdminstratif));
    }

    // Méthode pour imprimer la TableView sélectionnée
    private void printSelectedTableView(TableView<courrierEntrant> tableView) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer sous...");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichier PDF", "*.pdf"));
        Stage stage = (Stage) tabPane.getScene().getWindow();
        File file = fileChooser.showSaveDialog(stage);

        if (file != null) {
            exportTableViewToPDF(tableView, file.getAbsolutePath());
            infoAlertBox("Document OK", " document imprimer avec succès ");
        }
    }

    // Exporter les données d'une TableView vers un fichier PDF
    private void exportTableViewToPDF(TableView<courrierEntrant> tableView, String outputPath) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(outputPath));
            document.open();

            PdfPTable pdfTable = new PdfPTable(tableView.getColumns().size());
            pdfTable.setWidthPercentage(100);

            // Ajouter les en-têtes des colonnes
            for (TableColumn<?, ?> column : tableView.getColumns()) {
                PdfPCell cell = new PdfPCell(new Phrase(column.getText()));
                pdfTable.addCell(cell);
            }

            // Ajouter les données de la table
            for (courrierEntrant item : tableView.getItems()) {
                pdfTable.addCell(String.valueOf(item.getId()));
                pdfTable.addCell(item.getNomCourrier());
                pdfTable.addCell(item.getNomExpediteur());
                pdfTable.addCell(item.getDate());
                pdfTable.addCell(item.getDescriptionCourrier());
            }

            document.add(pdfTable);
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Mise à jour de la TableView pour courrier ordinaire
    public void updateTableViewCourrierOrdinaire() {
        try {
        	
            ObservableList<courrierEntrant> listCourrierOrdinaire = getCourrierEntrantOrdinaire();
            IDColumOrdinaire.setCellValueFactory(new PropertyValueFactory<>("id"));
            nomCourrierColumOrdinaire.setCellValueFactory(new PropertyValueFactory<>("nomCourrier"));
            nomExpediteurOrdinaire.setCellValueFactory(new PropertyValueFactory<>("nomExpediteur"));
            dateArriveColumOrdinaire.setCellValueFactory(new PropertyValueFactory<>("date"));
            objetColumOrdinaire.setCellValueFactory(new PropertyValueFactory<>("descriptionCourrier"));
            TableViewCourrierOrdinaire.setItems(listCourrierOrdinaire);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Mise à jour de la TableView pour courrier confidentiel
    public void updateTableViewCourrierConfidentiel() {
        try {
        	
            ObservableList<courrierEntrant> listCourrierConfidentiel = getCourrierEntrantConfidentiel();
            IDColumConfidentiel.setCellValueFactory(new PropertyValueFactory<>("id"));
            nomCourrierColumConfidentiel.setCellValueFactory(new PropertyValueFactory<>("nomCourrier"));
            nomExpediteurConfidentiel.setCellValueFactory(new PropertyValueFactory<>("nomExpediteur"));
            dateArriveColumConfidentiel.setCellValueFactory(new PropertyValueFactory<>("date"));
            objetColumConfidentiel.setCellValueFactory(new PropertyValueFactory<>("descriptionCourrier"));
            TableViewCourrierConfidentiel.setItems(listCourrierConfidentiel);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Mise à jour de la TableView pour courrier administratif
    public void updateTableViewCourrierAdministratif() {
        try {
        	
            ObservableList<courrierEntrant> listCourrierAdministratif = getCourrierEntrantAdministratif();
            IDColumAdminstratif.setCellValueFactory(new PropertyValueFactory<>("id"));
            nomCourrierColumAdminstratif.setCellValueFactory(new PropertyValueFactory<>("nomCourrier"));
            nomExpediteurAdminstratif.setCellValueFactory(new PropertyValueFactory<>("nomExpediteur"));
            dateArriveColumAdminstratif.setCellValueFactory(new PropertyValueFactory<>("date"));
            objetColumAdminstratif.setCellValueFactory(new PropertyValueFactory<>("descriptionCourrier"));
            TableViewCourrierAdminstratif.setItems(listCourrierAdministratif);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Récupérer les courriers depuis la base de données pour chaque type
    public ObservableList<courrierEntrant> getCourrierEntrantOrdinaire() throws SQLException {
        return getCourriers("courrier ordinaire");
    }

    public ObservableList<courrierEntrant> getCourrierEntrantConfidentiel() throws SQLException {
        return getCourriers("courrier confidentiel");
    }

    public ObservableList<courrierEntrant> getCourrierEntrantAdministratif() throws SQLException {
        return getCourriers("courrier administratif");
    }

    private ObservableList<courrierEntrant> getCourriers(String type) throws SQLException {
    	
        Connection con = DBManager.connect();
        
        ObservableList<courrierEntrant> listOfCourriers = FXCollections.observableArrayList();
        String sql = "SELECT id_courrier, nom_courrier, nom_expediteur, date_expedition, description_courrier " +
                "FROM courrier_entrant WHERE type_courrier='" + type + "'";
        Statement stmt = con.createStatement();
        ResultSet res = stmt.executeQuery(sql);

        while (res.next()) {
        	courrierEntrant c = new courrierEntrant(
                    res.getInt("id_courrier"),
                    res.getString("nom_courrier"),
                    res.getString("nom_expediteur"),
                    res.getString("date_expedition"),
                    res.getString("description_courrier"));
            listOfCourriers.add(c);
        }

        con.close();
        return listOfCourriers;
    }
    
    public void infoAlertBox(String title, String contain) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle(title);
		alert.setHeaderText(contain);
		alert.showAndWait();
		
	}
}
