package api.controllers;


import api.beans.ProjetSearchBean;
import api.dao.DiversDao;
import api.dto.DetailDto;
import api.dto.PageResult;
import api.services.ProjetSearch;
import api.services.ProjetService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/projets")
public class ProjetDivers {

    final private ProjetService projetService;
    final private ProjetSearch projetSearch;
    final private DiversDao diversDao;

    public ProjetDivers(ProjetService projetService, ProjetSearch projetSearch, DiversDao diversDao) {
        this.projetService = projetService;
        this.projetSearch = projetSearch;
        this.diversDao = diversDao;
    }

    @GetMapping
    public PageResult getAllProjets(ProjetSearchBean bean) {

        return projetSearch.getListProjets(bean);
    }

    @GetMapping(value = "/detail/{idProjet}")
    public DetailDto getProjetForDetail(@PathVariable Integer idProjet) {

        return projetService.getDetailDto(idProjet);
    }



    @GetMapping(value = "/search/loading")
    public Map<String, Object> projetsSearchLoading() {

        Map<String, Object> map = new HashMap<>();

        map.put("secteurs", diversDao.getSecteurs());
        map.put("srcFinancements", diversDao.getSrcFinancements());
        map.put("communes", diversDao.getCommunes());
        map.put("acheteurs", diversDao.getAcheteurs());

        return map;
    }


}
