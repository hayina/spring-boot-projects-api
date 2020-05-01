package api.services;

import api.beans.ProjetBean;
import api.entities.Projet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


//@DataJpaTest(
//        includeFilters = {
//        @ComponentScan.Filter( type= FilterType.REGEX, pattern = { "api.dao.*" })
//}
//)
//@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
//@TestPropertySource(properties = { "spring.jpa.hibernate.ddl-auto=validate" })
@SpringBootTest
public class ProjetServiceTestIT {


    @Autowired
    private ProjetService projetService;
//    @Autowired
//    private GenericDao<Projet, Integer> gProjetDao;
//    @PersistenceContext
//    private EntityManager entityManager;
//    @Autowired
//    private GenericDao<Commune, Integer> communeDao;
//    @Autowired
//    private GenericDao<Projet, Integer> projetDao;

    @Test
    void saveNewProject(){

        var fullInitDto = new ProjetBean().fullInitDto();
//
        Projet entity = projetService.saveProjet(fullInitDto, 2);
//
        ProjetServiceTest.assertFields(fullInitDto, entity, true);

//        List<Commune> communes = communeDao.findAll(Commune.class);
//        List<Projet> projets = projetDao.findAll(Projet.class);

//        Assertions.assertThat(projets.size()).isGreaterThan(0);
//        Assertions.assertThat(communes.size()).isEqualTo(2);

    }
}
