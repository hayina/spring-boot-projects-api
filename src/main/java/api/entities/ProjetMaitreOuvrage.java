package api.entities;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


@Entity
@Table(name = "projet_maitre_ouvrage")
public class ProjetMaitreOuvrage implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "maitre_ouvrage")
	private Acheteur maitreOuvrage;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "projet")
	private Projet projet;
	

	
	private boolean delegate;

	public ProjetMaitreOuvrage() {
	}

	public ProjetMaitreOuvrage(Acheteur maitreOuvrage, Projet projet, boolean delegate) {
		this.maitreOuvrage = maitreOuvrage;
		this.projet = projet;
		this.delegate = delegate;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Acheteur getMaitreOuvrage() {
		return maitreOuvrage;
	}

	public void setMaitreOuvrage(Acheteur maitreOuvrage) {
		this.maitreOuvrage = maitreOuvrage;
	}

	public Projet getProjet() {
		return projet;
	}

	public void setProjet(Projet projet) {
		this.projet = projet;
	}


	public boolean isDelegate() {
		return delegate;
	}

	public void setDelegate(boolean delegate) {
		this.delegate = delegate;
	}




}
