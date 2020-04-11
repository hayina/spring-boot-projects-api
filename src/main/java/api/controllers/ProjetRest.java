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

@RestController
@RequestMapping(value = "/api")
public class ProjetRest {

	@Autowired
	private GenericDao<Projet, Integer> gProjetDao;
	@Autowired
	private MarcheDao marcheDao;
	@Autowired
	private ProjetService projetService;
	@Autowired
	private ProjetSearch projetSearch;
	@Autowired
	private MarcheService marcheService;
	@Autowired
	private DiversDao diversDao;



	/**
	 * saving or updating project
	 * @param currentUser
	 * @param bean
	 * @return
	 */
	@PostMapping(value = "/projets")
	@EditProjectAuth
	public Integer saveNewProjet(@CurrentUser Integer currentUser, @RequestBody ProjetBean bean) {
		
		return projetService.saveProjet(bean, currentUser);
	}
	
	@PutMapping(value = "/projets/{idProjet}")
	@SaveEditedProjectAuth
	public Integer updateProjet(@CurrentUser Integer currentUser, @PathVariable Integer idProjet, @RequestBody ProjetBean bean) {
		
		bean.idProjet = idProjet;
		return projetService.saveProjet(bean, currentUser);
	}
	
	
	/**
	 * loading prject data for new or edit page
	 * @param idProjet
	 * @return
	 */
	
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
	
	/**
	 * deleting the project
	 * @param idProjet
	 */

	@DeleteMapping(value = "/projets/{idProjet}")
	@DeleteProjectAuth
	public void deleteProjet(@PathVariable Integer idProjet) {
		
		gProjetDao.delete(Projet.class, idProjet);
	}
	
	
	
	@GetMapping(value = "/projets/detail/{idProjet}") 
	public DetailDto getProjetForDetail(@PathVariable Integer idProjet) {
		
		return new DetailDto(
				projetService.getProjetForDetail(idProjet), 
				marcheService.getDefaultMarcheForDetail(idProjet),
				marcheDao.getMarchesIdsWithTypeByProjet(idProjet)
		);
	}
	
	@GetMapping(value = "/projets/search/loading")
	public Map<String, Object> projetsSearchLoading() {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("secteurs", diversDao.getSecteurs());
		map.put("srcFinancements", diversDao.getSrcFinancements());
		map.put("communes", diversDao.getCommunes());
		map.put("acheteurs", diversDao.getAcheteurs());
		
		return map;
	}
	
	@GetMapping(value = "/projets")
	public PageResult getAllProjets(ProjetSearchBean bean) {
		
		return projetSearch.getListProjets(bean);
	}
	

	

}






