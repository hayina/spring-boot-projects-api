package api.entities;


import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;


@Entity
@Table(name = "localisation")
public class Localisation implements java.io.Serializable {

	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "commune")
	private Commune commune;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fraction")
	private Fraction fraction;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "projet")
	private Projet projet;
	



	public Localisation() {}

	public Localisation(Projet projet, Commune commune, Fraction fraction) {
		this.projet = projet;
		this.commune = commune;
		this.fraction = fraction;
	}
	public Localisation(Projet projet, Commune commune) {
		this.projet = projet;
		this.commune = commune;
	}
	
	public Localisation(Commune commune) {
		this.commune = commune;
	}
	
	
	
	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Commune getCommune() {
		return this.commune;
	}
	public void setCommune(Commune commune) {
		this.commune = commune;
	}
	public Fraction getFraction() {
		return this.fraction;
	}
	public void setFraction(Fraction fraction) {
		this.fraction = fraction;
	}

	public Projet getProjet() {
		return projet;
	}

	public void setProjet(Projet projet) {
		this.projet = projet;
	}


}
