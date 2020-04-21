package api.services;

import api.beans.ProjetBean;
import api.dao.GenericDao;
import api.dto.PartnerDto;
import api.entities.Projet;
import api.entities.ProjetMaitreOuvrage;
import org.hamcrest.core.IsInstanceOf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

class ProjetServiceTest {


    private @InjectMocks ProjetService projetService;

    private @Mock EntityManager entityManager;

    private @Mock GenericDao<Projet, Integer> genericProjetDao;

    private @Mock GenericDao<ProjetMaitreOuvrage, Integer> gProjetMaitreOuvrageDao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void saveNewProjet() {
//        Projet projet = new Projet();
        Integer projetId = 1475820;
//        when(genericProjetDao.find(anyInt(), Projet.class)).thenReturn(projet);

        when(genericProjetDao.find(anyInt(), eq(Projet.class))).thenReturn(new Projet());
        when(genericProjetDao.persist(any(Projet.class))).thenReturn(new Projet(projetId));
        doNothing().when(entityManager).flush();
        when(gProjetMaitreOuvrageDao.persist(any(ProjetMaitreOuvrage.class))).thenReturn(any(ProjetMaitreOuvrage.class));

        ProjetBean bean = new ProjetBean();
        bean.srcFinancement=1;
        bean.secteur=1;
        bean.isConvention=false;
        bean.isMaitreOuvrageDel = false;

//        bean.intitule="intitule";
//        bean.montant=3000D;
//        bean.indhProgramme
//        public List<String> localisations;
//        public List<PartnerDto> partners;
//        public Integer maitreOuvrage;
//        public Integer maitreOuvrageDel;
//        public Integer chargeSuivi;
//        public Integer anneeProjet;


        Integer stoerdProjetId = projetService.saveProjet(bean, 2);

//        assertNotNull(stoerdProjetId);
        verify(genericProjetDao, times(0)).find(anyInt(), eq(Projet.class));
        verify(genericProjetDao, times(1)).persist(any(Projet.class));
        verify(entityManager, times(1)).flush();
        verify(gProjetMaitreOuvrageDao, times(1)).persist(any(ProjetMaitreOuvrage.class));
    }
}