package api.controllers;

import api.beans.ProjetBean;
import api.entities.Projet;
import api.security.annotations.CurrentUser;
import api.security.annotations.DeleteProjectAuth;
import api.security.annotations.EditProjectAuth;
import api.security.annotations.SaveEditedProjectAuth;
import api.services.ProjetService;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Map;

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


		Projet projet = projetService.saveProjet(bean, currentUser);
		return projet.getId();
	}


	@PutMapping(value = "/projets/{idProjet}")
	@SaveEditedProjectAuth
	public Integer updateProjet(@CurrentUser Integer currentUser, @PathVariable Integer idProjet,
								@Valid @RequestBody ProjetBean bean) {

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






