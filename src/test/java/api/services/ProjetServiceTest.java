package api.services;

import api.beans.ProjetBean;
import api.dao.GenericDao;
import api.entities.Projet;
import api.helpers.Helpers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


class ProjetServiceTest {


    private @InjectMocks ProjetService projetService;
    private @Mock EntityManager entityManager;
    private @Mock GenericDao<Projet, Integer> gProjetDao;

    static Integer projetId = 1475820;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @ParameterizedTest
    @MethodSource("getDtoForSaveService")
    void saveNewProjet(ProjetBean dto, boolean requiredFieldsOnly, boolean editMode, Projet projetToEdit) {



        if(editMode) dto.idProjet = projetId;

        when(gProjetDao.persist(any(Projet.class))).thenReturn(new Projet(projetId));
        when(gProjetDao.find(eq(projetId), eq(Projet.class))).thenReturn(projetToEdit);
        doNothing().when(entityManager).flush();

        Projet entity = projetService.saveProjet(dto, 2);

        assertFields(dto, entity, requiredFieldsOnly);

//        verify(gProjetDao, times(editMode ? 1 : 0)).find(any(), eq(Projet.class));
//        verify(entityManager, times(editMode ? 1 : 0)).flush();
//        verify(gProjetDao, times(editMode ? 0 : 1)).persist(any(Projet.class));
    }


    static public Stream<Arguments> getDtoForSaveService(){

        var fullInitDto = new ProjetBean().fullInitDto();
        var justRequiredFieldsDto = new ProjetBean().initRequiredFields();
        var fullProjectEntity = ProjetBean.getFullProjectEntity(projetId);
//        var onlyRequiredProjectEntity = ProjetBean.getOnlyRequiredProjectEntity(projetId);

        return Stream.of(
//                Arguments.of(ProjetBean dto, boolean requiredFieldsOnly, boolean editMode),
                // new
                Arguments.of(fullInitDto, false, false, null),
                Arguments.of(justRequiredFieldsDto, true, false, null),
                // edit
                Arguments.of(fullInitDto, false, true, fullProjectEntity),
//                Arguments.of(fullInitDto, false, true, onlyRequiredProjectEntity),
                Arguments.of(justRequiredFieldsDto, true, true, fullProjectEntity)
        );
    }

    private void assertFields(ProjetBean dto, Projet entity, boolean requiredFieldsOnly) {
        // check for required fields
        assertAll(
                () -> assertThat(entity.getId()).isEqualTo(projetId),
                () -> assertThat(entity.getIntitule()).isEqualTo(dto.intitule),
                () -> assertThat(entity.getMontant()).isEqualTo(dto.montant),
                () -> assertThat(entity.getSecteur().getId()).isEqualTo(dto.secteur),
                () -> assertThat(entity.getAnneeProjet()).isEqualTo(dto.anneeProjet),
                () -> assertThat(entity.getSrcFinancement().getId()).isEqualTo(dto.srcFinancement),
                () -> assertThat(entity.getProjetMaitreOuvrage().getMaitreOuvrage().getId()).isEqualTo(dto.maitreOuvrage),
                () -> assertThat(entity.getProjetMaitreOuvrage().isDelegate()).isEqualTo(false),

//              () -> assertThat(entity.getChargeSuivi().getId()).isEqualTo(dto.chargeSuivi),
                () -> assertThat(
                        Helpers.compareLists(
                                entity.getLocalisations().stream()
                                        .map(location -> location.getCommune().getId() + (location.getFraction() != null ? "." + location.getFraction().getId() : ""))
                                        .collect(Collectors.toList()),
                                dto.localisations,
                                // String::equals
                                (entityLocation, dtoLocation) -> entityLocation.equals(dtoLocation)
                        )

                ).isTrue(),
                () -> assertThat(entity.getMaitreOuvrages().size()).isEqualTo(dto.isMaitreOuvrageDel ? 2 : 1)
        );

        if(requiredFieldsOnly) {
            assertAll(
                    () -> assertThat(entity.getIndh()).isNull(),
                    () -> assertThat(entity.getProjetMaitreOuvrageDelegue()).isNull(),
                    () -> assertThat(entity.getProjetPartenaires()).isEmpty()
            );
        }
        else {
            assertAll(
                    () -> assertThat(entity.getIndh().getProgramme().getId()).isEqualTo(dto.indhProgramme),
                    () -> assertThat(entity.getProjetMaitreOuvrageDelegue().getMaitreOuvrage().getId()).isEqualTo(dto.maitreOuvrageDel),
                    () -> assertThat(entity.getProjetMaitreOuvrageDelegue().isDelegate()).isEqualTo(true),
                    () -> assertThat(
                            Helpers.compareLists(dto.partners, entity.getProjetPartenaires(),
                                    (dtoPartner, entityProjetPartner) ->
                                            dtoPartner.partner.value.equals(entityProjetPartner.getPartenaire().getId())
                                                    && dtoPartner.montant.equals(entityProjetPartner.getFinancement()))
                    ).isTrue()
            );
        }

    }



//    private boolean compareLists(ProjetBean dto, Projet entity) {
//
//        return entity.getProjetPartenaires().stream().allMatch(entityProjetPartner ->
//                dto.partners.stream().anyMatch(dtoPartner ->
//                        dtoPartner.partner.value.equals(entityProjetPartner.getPartenaire().getId())
//                                        &&  dtoPartner.montant.equals(entityProjetPartner.getFinancement())
//                )
//        );
//    }

}