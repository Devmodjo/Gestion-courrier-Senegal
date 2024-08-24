package Model;

public class courrierSortant {

	private Integer id;
	private String nomCourrier;
	private String natureCourrier;
	private String nomExpediteur;
	private String adresseExpediteur;
	private String nomDestinataire;
	private String adresseDestinataire;
	private double poid;
	private String descriptionCourrier;
	private String date;
	private String typeCourrier;
	
	public courrierSortant(
			Integer id,
			String nomCourrier,
			String natureCourrier,
			String nomExpediteur,
			String adresseExpediteur,
			String nomDestinataire,
			String adresseDestinataire,
			double poid, 
			String descriptionCourrier,
			String date, 
			String typeCourrier) {
		
		this.setId(id);
		this.setNomCourrier(nomCourrier);
		this.setNatureCourrier(natureCourrier);
		this.setNomExpediteur(nomExpediteur);
		this.setAdresseExpediteur(adresseExpediteur);
		this.setNomDestinataire(nomDestinataire);
		this.setAdresseDestinataire(adresseDestinataire);
		this.setPoid(poid);
		this.setDescriptionCourrier(descriptionCourrier);
		this.setDate(date);
		this.setTypeCourrier(typeCourrier);
		
		
	}
	
	// deuxime constructeur sans le parametre id
	public courrierSortant(
			String nomCourrier,
			String natureCourrier,
			String nomExpediteur,
			String adresseExpediteur,
			String nomDestinataire,
			String adresseDestinataire,
			double poid, 
			String descriptionCourrier,
			String date, 
			String typeCourrier) {
		
		this.setId(id);
		this.setNomCourrier(nomCourrier);
		this.setNatureCourrier(natureCourrier);
		this.setNomExpediteur(nomExpediteur);
		this.setAdresseExpediteur(adresseExpediteur);
		this.setNomDestinataire(nomDestinataire);
		this.setAdresseDestinataire(adresseDestinataire);
		this.setPoid(poid);
		this.setDescriptionCourrier(descriptionCourrier);
		this.setDate(date);
		this.setTypeCourrier(typeCourrier);
		
		
	}

	public String getNomCourrier() {
		return nomCourrier;
	}

	public void setNomCourrier(String nomCourrier) {
		this.nomCourrier = nomCourrier;
	}

	public String getNatureCourrier() {
		return natureCourrier;
	}

	public void setNatureCourrier(String natureCourrier) {
		this.natureCourrier = natureCourrier;
	}

	public String getNomExpediteur() {
		return nomExpediteur;
	}

	public void setNomExpediteur(String nomExpediteur) {
		this.nomExpediteur = nomExpediteur;
	}

	public String getAdresseExpediteur() {
		return adresseExpediteur;
	}

	public void setAdresseExpediteur(String adresseExpediteur) {
		this.adresseExpediteur = adresseExpediteur;
	}

	public String getNomDestinataire() {
		return nomDestinataire;
	}

	public void setNomDestinataire(String nomDestinataire) {
		this.nomDestinataire = nomDestinataire;
	}

	public String getAdresseDestinataire() {
		return adresseDestinataire;
	}

	public void setAdresseDestinataire(String adresseDestinataire) {
		this.adresseDestinataire = adresseDestinataire;
	}

	public double getPoid() {
		return poid;
	}

	public void setPoid(double poid) {
		this.poid = poid;
	}

	public String getDescriptionCourrier() {
		return descriptionCourrier;
	}

	public void setDescriptionCourrier(String descriptionCourrier) {
		this.descriptionCourrier = descriptionCourrier;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTypeCourrier() {
		return typeCourrier;
	}

	public void setTypeCourrier(String typeCourrier) {
		this.typeCourrier = typeCourrier;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public int setId(String string) {
		// TODO Auto-generated method stub
		return 0;
	}

	

}


