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
@Table(name = "fraction")
public class Fraction implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "commune")
	private Commune commune;
	
	private String nom;
	private Double lat;
	private Double lng;

	
	public Fraction() {}
	
	public Fraction(Integer id) {
		this.id = id;
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
	public String getNom() {
		return this.nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public Double getLat() {
		return lat;
	}
	public void setLat(Double lat) {
		this.lat = lat;
	}
	public Double getLng() {
		return lng;
	}
	public void setLng(Double lng) {
		this.lng = lng;
	}

	
	
}
