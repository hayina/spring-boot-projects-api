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
		this.indhProgramme=1;
		this.maitreOuvrageDel=2;
		this.partners= new ArrayList<>(Arrays.asList(
				new PartnerDto(new SimpleDto(1, "partner1"), 1100D),
				new PartnerDto(new SimpleDto(2, "partner2"), 1200D)
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
		this.localisations = new ArrayList<>(List.of("12", "1.11"));
		this.secteur = 1;
		this.maitreOuvrage=1;
		this.chargeSuivi=1;
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

		projet.getProjetPartenaires().add(
				new ProjetPartenaire(projet, new Acheteur(111), 3300D, "commentaire3")
		);
		projet.getProjetPartenaires().add(
				new ProjetPartenaire(projet, new Acheteur(222), 4400D, "commentaire4")
		);

		return projet;
	}

	public static Projet getOnlyRequiredProjectEntity(Integer idProjet){

		var projet = new Projet(idProjet);

		projet.setIntitule("projetY");
		projet.setMontant(200000D);
		projet.setSecteur(new Secteur(2));
		projet.setProjetMaitreOuvrage(new ProjetMaitreOuvrage(new Acheteur(11), projet, false));
		projet.getMaitreOuvrages().add(projet.getProjetMaitreOuvrage());

		projet.setAnneeProjet(2021);
		projet.setSrcFinancement(new api.entities.SrcFinancement(SrcFinancement.BP.val()));
		projet.getLocalisations().add(new Localisation(projet, new Commune(13)));
		projet.getLocalisations().add(new Localisation(projet, new Commune(2), new Fraction(12)));

//		projet.setIndh(new ProjetIndh(projet, new IndhProgramme(22)));


		return projet;
	}


}
