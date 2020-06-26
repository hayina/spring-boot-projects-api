package api.beans;

import api.dto.PartnerDto;
import api.dto.SimpleDto;
import api.entities.*;
import api.enums.SrcFinancement;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ProjetBean {
	
	public Integer idProjet;

	@NotBlank
	public String intitule;

	@NotNull
	public Double montant;

	@NotEmpty
	public List<String> localisations;
	// we have to add validation for string value => "1.11.."
	@AssertTrue(message = "Format invalid")
	public boolean isLocationValid() {
		return localisations == null || localisations.size() == 0
				|| localisations.stream().allMatch(location -> location.matches("^[1-9][0-9]*(\\.[1-9][0-9]*)?"));
	}

	@NotNull
	public Integer secteur;

	@NotNull
	public Integer maitreOuvrage;

	@NotNull
	public Integer chargeSuivi;

	@NotNull
	public Integer anneeProjet;

	@NotNull
	public Integer srcFinancement;
	public Integer indhProgramme;
	@AssertTrue(message = "Champ obligatoire")
	public boolean isIndhNotNull() {
		return !api.enums.SrcFinancement.INDH.val().equals(srcFinancement) || indhProgramme != null;
	}

	public boolean isMaitreOuvrageDel = false;
	public Integer maitreOuvrageDel;
	@AssertTrue(message = "Champ obligatoire")
	public boolean isMaitreOuvrageDelNotNull() {
		return !isMaitreOuvrageDel || maitreOuvrageDel != null;
	}

	public boolean isConvention = false;
	public List<PartnerDto> partners;
	@AssertTrue(message = "Champ obligatoire")
	public boolean isPartnersNotNull() {
		return !isConvention || (partners != null && partners.size() != 0);
	}


	public ProjetBean fullInitDto(){
		initRequiredFields();
		initRequiredCrossFields();
		return this;
	}
	public void initRequiredCrossFields() {
		initForCrossFields();
		this.indhProgramme=11; // Lutte contre la pauvreté en milieu rural
		this.maitreOuvrageDel=10; // al omrane
		this.partners= new ArrayList<>(Arrays.asList(
				new PartnerDto(new SimpleDto(8, ""), 1100D), // Province Taourirt
				new PartnerDto(new SimpleDto(9, ""), 1200D) // Conseil Provincial Taourirt
		));
	}
	public void initForCrossFields() {
		this.isConvention = true;
		this.srcFinancement = SrcFinancement.INDH.val();
		this.isMaitreOuvrageDel = true;
	}
	public ProjetBean initRequiredFields() {
		this.intitule = "projetX";
		this.montant = 100000D;
		this.localisations = new ArrayList<>(List.of("24", "13.1")); // 24 => Taourirt, 13.1 => Ain Lahjer.OULAD AYOUB
		this.secteur = 1; // santé
		this.maitreOuvrage=8; // province taourirt
		this.chargeSuivi=2; // sahli
		this.anneeProjet=2020;
		this.srcFinancement= SrcFinancement.BG.val();;
		this.isConvention=false;
		this.isMaitreOuvrageDel=false;
		return this;

	}

	public static Projet getFullProjectEntity(Integer idProjet){

		var projet = getOnlyRequiredProjectEntity(idProjet);

//		projet.setIndh(new ProjetIndh(projet, new IndhProgramme(22)));

		projet.setProjetMaitreOuvrageDelegue(new ProjetMaitreOuvrage(new Acheteur(22), projet, true));
		projet.getMaitreOuvrages().add(projet.getProjetMaitreOuvrageDelegue());

		// (11,'Ministère de l'interieur'), (12,'ONEE - Branche Eau)
		projet.getProjetPartenaires().add(
				new ProjetPartenaire(projet, new Acheteur(12), 3300D, "commentaire3")
		);
		projet.getProjetPartenaires().add(
				new ProjetPartenaire(projet, new Acheteur(11), 4400D, "commentaire4")
		);

		return projet;
	}

	public static Projet getOnlyRequiredProjectEntity(Integer idProjet){

		var projet = new Projet(idProjet);

		projet.setIntitule("projetY");
		projet.setMontant(200000D);
		projet.setSecteur(new Secteur(2)); // enseignement
		projet.setProjetMaitreOuvrage(new ProjetMaitreOuvrage(new Acheteur(31), projet, false)); // 31 'Commune Taourirt'
		projet.getMaitreOuvrages().add(projet.getProjetMaitreOuvrage());

		projet.setAnneeProjet(2021);
		projet.setSrcFinancement(new api.entities.SrcFinancement(SrcFinancement.BP.val()));
		projet.getLocalisations().add(new Localisation(projet, new Commune(14))); // 14 'Machraa Hammadi'
		projet.getLocalisations().add(new Localisation(projet, new Commune(20), new Fraction(195))); // 20 El Ateuf, 195 'OULAD MAHJOUB'

		return projet;
	}


}
