package api.entities;


import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;





@Entity
@Table(name = "projet")
public class Projet implements java.io.Serializable {

	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Integer id;
	
	private String intitule;
	private Double montant;
	private boolean convention;


	
	@Column(name = "annee_projet")
	private Integer anneeProjet;
	




	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_saisie")
	private Date dateSaisie;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_last_modif")
	private Date DateLastModif;
	
	
	// ==> @JoinColumn
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "secteur")
	private Secteur secteur;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "projet_maitre_ouvrage")
	private ProjetMaitreOuvrage projetMaitreOuvrage;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "projet_maitre_ouvrage_delegue")
	private ProjetMaitreOuvrage projetMaitreOuvrageDelegue;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "charge_suivi")
	private User chargeSuivi;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "default_marche")
	private Marches defaultMarche;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_saisie")
	private User userSaisie;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "src_financement")
	private SrcFinancement srcFinancement;
	
	//// MAPPED BY
	


	@OneToOne(mappedBy = "projet", cascade = CascadeType.ALL, orphanRemoval=true)
    private ProjetIndh indh;

	@OneToMany(mappedBy = "projet", cascade = CascadeType.ALL, orphanRemoval=true)
	private Set<ProjetMaitreOuvrage> maitreOuvrages = new HashSet<>(0);


	@OneToMany(fetch = FetchType.LAZY, mappedBy = "projet", cascade = CascadeType.ALL, orphanRemoval=true)
	private Set<Marches> marches = new HashSet<>(0);
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "projet", cascade = CascadeType.ALL, orphanRemoval=true)
	private Set<Localisation> localisations = new HashSet<>(0);
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "projet", cascade = CascadeType.ALL, orphanRemoval=true)
	private Set<ProjetPartenaire> projetPartenaires = new HashSet<>(0);
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "projet", cascade = CascadeType.ALL, orphanRemoval=true)
	private Set<ProjetUser> projetUsers = new HashSet<>(0);
	
	public Projet() {}
	
	public Projet(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIntitule() {
		return intitule;
	}

	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}

	public Double getMontant() {
		return montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}

	public Secteur getSecteur() {
		return secteur;
	}

	public void setSecteur(Secteur secteur) {
		this.secteur = secteur;
	}

	public Date getDateSaisie() {
		return dateSaisie;
	}

	public void setDateSaisie(Date dateSaisie) {
		this.dateSaisie = dateSaisie;
	}


	public Set<Localisation> getLocalisations() {
		return localisations;
	}

	public void setLocalisations(Set<Localisation> localisations) {
		this.localisations = localisations;
	}

	public Set<ProjetPartenaire> getProjetPartenaires() {
		return projetPartenaires;
	}

	public void setProjetPartenaires(Set<ProjetPartenaire> projetPartenaires) {
		this.projetPartenaires = projetPartenaires;
	}

	public Set<ProjetUser> getProjetUsers() {
		return projetUsers;
	}

	public void setProjetUsers(Set<ProjetUser> projetUsers) {
		this.projetUsers = projetUsers;
	}

	public ProjetMaitreOuvrage getProjetMaitreOuvrage() {
		return projetMaitreOuvrage;
	}

	public void setProjetMaitreOuvrage(ProjetMaitreOuvrage projetMaitreOuvrage) {
		this.projetMaitreOuvrage = projetMaitreOuvrage;
	}

	public ProjetMaitreOuvrage getProjetMaitreOuvrageDelegue() {
		return projetMaitreOuvrageDelegue;
	}

	public void setProjetMaitreOuvrageDelegue(ProjetMaitreOuvrage projetMaitreOuvrageDelegue) {
		this.projetMaitreOuvrageDelegue = projetMaitreOuvrageDelegue;
	}

	public boolean isConvention() {
		return convention;
	}

	public void setConvention(boolean convention) {
		this.convention = convention;
	}

	public ProjetIndh getIndh() {
		return indh;
	}

	public void setIndh(ProjetIndh indh) {
		this.indh = indh;
	}


	public User getChargeSuivi() {
		return chargeSuivi;
	}

	public void setChargeSuivi(User chargeSuivi) {
		this.chargeSuivi = chargeSuivi;
	}

	public Set<Marches> getMarches() {
		return marches;
	}

	public void setMarches(Set<Marches> marches) {
		this.marches = marches;
	}

	public User getUserSaisie() {
		return userSaisie;
	}

	public void setUserSaisie(User userSaisie) {
		this.userSaisie = userSaisie;
	}

	public Date getDateLastModif() {
		return DateLastModif;
	}

	public void setDateLastModif(Date dateLastModif) {
		DateLastModif = dateLastModif;
	}

	public SrcFinancement getSrcFinancement() {
		return srcFinancement;
	}

	public void setSrcFinancement(SrcFinancement srcFinancement) {
		this.srcFinancement = srcFinancement;
	}

	public Integer getAnneeProjet() {
		return anneeProjet;
	}

	public void setAnneeProjet(Integer anneeProjet) {
		this.anneeProjet = anneeProjet;
	}

	public Marches getDefaultMarche() {
		return defaultMarche;
	}

	public void setDefaultMarche(Marches defaultMarche) {
		this.defaultMarche = defaultMarche;
	}


	public void setMaitreOuvrages(Set<ProjetMaitreOuvrage> maitreOuvrages) {
		this.maitreOuvrages = maitreOuvrages;
	}

	public Set<ProjetMaitreOuvrage> getMaitreOuvrages() {
		return maitreOuvrages;
	}

	
}
