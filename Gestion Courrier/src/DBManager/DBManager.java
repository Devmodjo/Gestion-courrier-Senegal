package DBManager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DBManager {

	public static Connection connect()  {
		
		Connection con = null;
		
		try {
			
			Class.forName("org.sqlite.JDBC");
			con = DriverManager.getConnection("jdbc:sqlite:DB_COURRIER.db");
			Statement stmt = con.createStatement();
			
			// creation des table
			String userTable = "CREATE TABLE IF NOT EXISTS user ("
					+ "id_user	INTEGER NOT NULL,"
					+ "name_user	TEXT NOT NULL,"
					+ "password_user	TEXT NOT NULL,"
					+ "PRIMARY KEY(id_user AUTOINCREMENT)"
					+ ");";
			stmt.execute(userTable);
			
			
			String courrierEntrantTable = "CREATE TABLE IF NOT EXISTS courrier_entrant ("
					+ "id_courrier INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE,"
					+ "nom_courrier TEXT NOT NULL,"
					+ "nature_courrier TEXT NOT NULL,"
					+ "nom_expediteur TEXT NOT NULL,"
					+ "adresse_expediteur TEXT NOT NULL,"
					+ "nom_destinataire TEXT NOT NULL,"
					+ "adresse_destinataire TEXT NOT NULL,"
					+ "poids DECIMAL NOT NULL,  "
					+ "description_courrier TEXT NOT NULL,"
					+ "date_expedition TEXT NOT NULL,"
					+ "type_courrier TEXT NOT NULL, "
					+ "id_user INTEGER  ,  "
					+ "FOREIGN KEY (id_user) REFERENCES user(id_user)"
					+ ");";
			stmt.execute(courrierEntrantTable);
			
			System.out.println("Database connected...");
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		
		return con;
	}
}
