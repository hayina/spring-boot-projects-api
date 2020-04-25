package api.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api.beans.ProjetBean;
import api.beans.ProjetSearchBean;
import api.dao.DiversDao;
import api.dao.GenericDao;
import api.dao.MarcheDao;
import api.dto.DetailDto;
import api.dto.PageResult;
import api.entities.Projet;
import api.security.annotations.CurrentUser;
import api.security.annotations.DeleteProjectAuth;
import api.security.annotations.EditProjectAuth;
import api.security.annotations.SaveEditedProjectAuth;
import api.services.MarcheService;
import api.services.ProjetSearch;
import api.services.ProjetService;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api")
public class ProjetRest {

	final private ProjetService projetService;

	public ProjetRest(ProjetService projetService) {
		this.projetService = projetService;
	}


	@PostMapping(value = "/projets")
	@EditProjectAuth
	public Integer saveNewProjet(@CurrentUser Integer currentUser, @Valid @RequestBody ProjetBean bean) {


		return projetService.saveProjet(bean, currentUser).getId();
	}


	@PutMapping(value = "/projets/{idProjet}")
	@SaveEditedProjectAuth
	public Integer updateProjet(@CurrentUser Integer currentUser, @PathVariable Integer idProjet, @RequestBody ProjetBean bean) {

		bean.idProjet = idProjet;
		return projetService.saveProjet(bean, currentUser).getId();
	}

	
	@GetMapping(value = "/projets/loading/{idProjet}")
	@SaveEditedProjectAuth
	public Map<String, Object> loadingProjetEdit(@PathVariable Integer idProjet) {

		return projetService.projetLoadingForEdit(idProjet);
	}
	
	@GetMapping(value = "/projets/loading")
	@EditProjectAuth
	public Map<String, Object> loadingProjetNew() {
		
		return projetService.projetLoadingForEdit(null);
	}

	@DeleteMapping(value = "/projets/{idProjet}")
	@DeleteProjectAuth
	public void deleteProjet(@PathVariable Integer idProjet) {

		projetService.delete(idProjet);
	}

}






