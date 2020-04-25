package api.beans;

import java.util.List;

import api.dto.PartnerDto;
import api.entities.IndhProgramme;
import api.entities.ProjetIndh;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
				|| localisations.stream().allMatch(location -> location.matches("[1-9]+(\\.[1-9]+)*"));
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
	@AssertTrue
	public boolean isINDHNotNull() {
		return !api.enums.SrcFinancement.INDH.val().equals(srcFinancement) || indhProgramme != null;
	}

	public boolean isMaitreOuvrageDel = false;
	public Integer maitreOuvrageDel;
	@AssertTrue
	public boolean isMaitreOuvrageDelNotNull() {
		return !isMaitreOuvrageDel || maitreOuvrageDel != null;
	}

	public boolean isConvention = false;
	public List<PartnerDto> partners;
	@AssertTrue
	public boolean isPartnersNotNull() {
		return !isConvention || (partners != null && partners.size() != 0);
	}


}
