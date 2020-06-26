package api.services;

import api.beans.ProjetBean;
import api.entities.Projet;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;


//@DataJpaTest(
//        includeFilters = {
//        @ComponentScan.Filter( type= FilterType.REGEX, pattern = { "api.dao.*" })
//}
//)
//@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
//@TestPropertySource(properties = { "spring.jpa.hibernate.ddl-auto=validate" })
@SpringBootTest
@Disabled
public class ProjetServiceTestIT {

    @Autowired
    private ProjetService projetService;

    private static Stream<Arguments> getDtoForSaveService() {
        return ProjetServiceTest.getDtoForSaveService();
    }

    @ParameterizedTest
    @MethodSource("getDtoForSaveService")
    void saveNewProject(ProjetBean dto, Projet entityToEdit, boolean editMode){


        if(editMode) dto.idProjet = 45000;
        Projet entity = projetService.saveProjet(dto, 2);
        ProjetServiceTest.assertFields(dto, entity, true);
    }
}
