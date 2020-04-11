package api.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "marches_etat")
public class MarchesEtat implements java.io.Serializable {

	@Id
	private int id;
	private String nom;


	public MarchesEtat() {}
	public MarchesEtat(int id) {
		this.id = id;
	}


	public int getId() {
		return this.id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNom() {
		return this.nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}

}
