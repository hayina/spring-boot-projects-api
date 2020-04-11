package api.entities;


import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name = "marches")
public class Marches implements java.io.Serializable {

	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Integer id;

	private String intitule;
	
	@Column(name = "num_marche")
	private String numMarche;
	
	@Column(name = "delai_execution")
	private Integer delaiExecution;
	
	@Column(name = "work_days_last_arret")
	private Long workDaysLastArretOrRecep;
	
//	@Temporal(TemporalType.DATE)
//	@Column(name = "last_reprise")
//	private Date lastReprise;
	
	private Double montant;

	@Temporal(TemporalType.DATE)
	@Column(name = "date_plis")
	private Date datePlis;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "date_approbation")
	private Date dateApprobation;
	

	
	@Temporal(TemporalType.DATE)
	@Column(name = "date_reception_provisoire")
	private Date dateReceptionProvisoire;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "date_reception_definitive")
	private Date dateReceptionDefinitive;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_saisie")
	private Date dateSaisie;

	////////////////// @JoinColumn
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "projet")
	private Projet projet;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "type_marche")
	private MarchesType marchesType;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "etat_marche")
	private MarchesEtat marchesEtat;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "current_taux")
	private MarchesTaux currentTaux;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "start_os")
	private MarchesOs startOs;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "current_os")
	private MarchesOs currentOs;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "current_decompte")
	private MarchesDecomptes currentDecompte;
	

	////////////////// mappedBy
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "marches", cascade = CascadeType.ALL, orphanRemoval=true)
	private Set<MarchesSociete> marchesSocietes = new HashSet<>(0);
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "marches", cascade = CascadeType.ALL, orphanRemoval=true)
	private Set<MarchesTaux> marchesTaux = new HashSet<>(0);
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "marches", cascade = CascadeType.ALL, orphanRemoval=true)
	private Set<MarchesOs> MarchesOss = new HashSet<>(0);
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "marches", cascade = CascadeType.ALL, orphanRemoval=true)
	private Set<MarchesDecomptes> marchesDecomptes = new HashSet<>(0);
	
	

	public Marches() {
	}

	public Marches(Integer id) {
		this.id = id;
	}




	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	

	public MarchesType getMarchesType() {
		return this.marchesType;
	}

	public void setMarchesType(MarchesType marchesType) {
		this.marchesType = marchesType;
	}


	

	public MarchesEtat getMarchesEtat() {
		return this.marchesEtat;
	}

	public void setMarchesEtat(MarchesEtat marchesEtat) {
		this.marchesEtat = marchesEtat;
	}


	public MarchesTaux getCurrentTaux() {
		return currentTaux;
	}

	public void setCurrentTaux(MarchesTaux currentTaux) {
		this.currentTaux = currentTaux;
	}


	public Projet getProjet() {
		return this.projet;
	}

	public void setProjet(Projet projet) {
		this.projet = projet;
	}



	
	

	public Date getDatePlis() {
		return datePlis;
	}

	public void setDatePlis(Date datePlis) {
		this.datePlis = datePlis;
	}


	public Date getDateApprobation() {
		return this.dateApprobation;
	}

	public void setDateApprobation(Date dateApprobation) {
		this.dateApprobation = dateApprobation;
	}

	public Date getDateReceptionProvisoire() {
		return this.dateReceptionProvisoire;
	}

	public void setDateReceptionProvisoire(Date dateReceptionProvisoire) {
		this.dateReceptionProvisoire = dateReceptionProvisoire;
	}

	public Date getDateReceptionDefinitive() {
		return dateReceptionDefinitive;
	}

	public void setDateReceptionDefinitive(Date dateReceptionDefinitive) {
		this.dateReceptionDefinitive = dateReceptionDefinitive;
	}

	public String getIntitule() {
		return intitule;
	}

	public void setIntitule(String intitule) {
		this.intitule = intitule;
	}

	public String getNumMarche() {
		return this.numMarche;
	}

	public void setNumMarche(String numMarche) {
		this.numMarche = numMarche;
	}

	public Integer getDelaiExecution() {
		return this.delaiExecution;
	}

	public void setDelaiExecution(Integer delaiExecution) {
		this.delaiExecution = delaiExecution;
	}

	public Double getMontant() {
		return this.montant;
	}

	public void setMontant(Double montant) {
		this.montant = montant;
	}



	

	public Set<MarchesSociete> getMarchesSocietes() {
		return this.marchesSocietes;
	}

	public void setMarchesSocietes(Set<MarchesSociete> marchesSocietes) {
		this.marchesSocietes = marchesSocietes;
	}

	public Set<MarchesTaux> getMarchesTaux() {
		return marchesTaux;
	}

	public void setMarchesTaux(Set<MarchesTaux> marchesTaux) {
		this.marchesTaux = marchesTaux;
	}

	public Set<MarchesOs> getMarchesOss() {
		return MarchesOss;
	}

	public void setMarchesOss(Set<MarchesOs> marchesOss) {
		MarchesOss = marchesOss;
	}

	public Set<MarchesDecomptes> getMarchesDecomptes() {
		return marchesDecomptes;
	}

	public void setMarchesDecomptes(Set<MarchesDecomptes> marchesDecomptes) {
		this.marchesDecomptes = marchesDecomptes;
	}

//	public Date getDateOsStart() {
//		return dateOsStart;
//	}
//
//	public void setDateOsStart(Date dateOsStart) {
//		this.dateOsStart = dateOsStart;
//	}

	public Date getDateSaisie() {
		return dateSaisie;
	}

	public void setDateSaisie(Date dateSaisie) {
		this.dateSaisie = dateSaisie;
	}

	public MarchesOs getCurrentOs() {
		return currentOs;
	}

	public void setCurrentOs(MarchesOs currentOs) {
		this.currentOs = currentOs;
	}

	public MarchesDecomptes getCurrentDecompte() {
		return currentDecompte;
	}

	public void setCurrentDecompte(MarchesDecomptes currentDecompte) {
		this.currentDecompte = currentDecompte;
	}

	public MarchesOs getStartOs() {
		return startOs;
	}

	public void setStartOs(MarchesOs startOs) {
		this.startOs = startOs;
	}

	public Long getWorkDaysLastArretOrRecep() {
		return workDaysLastArretOrRecep;
	}

	public void setWorkDaysLastArretOrRecep(Long workDaysLastArretOrRecep) {
		this.workDaysLastArretOrRecep = workDaysLastArretOrRecep;
	}



//	public Date getLastReprise() {
//		return lastReprise;
//	}
//
//	public void setLastReprise(Date lastReprise) {
//		this.lastReprise = lastReprise;
//	}



	
	
}
