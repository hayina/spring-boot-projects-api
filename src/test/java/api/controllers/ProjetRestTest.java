package api.controllers;

import api.beans.ProjetBean;
import api.dto.ExceptionDto;
import api.dto.PartnerDto;
import api.dto.SimpleDto;
import api.entities.Projet;
import api.enums.SrcFinancement;
import api.security.WebSecurityConfig;
import api.services.ProjetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@WebMvcTest(
    controllers  = {ProjetRest.class},
    excludeAutoConfiguration = { SecurityAutoConfiguration.class },
    excludeFilters = {
            @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = { WebSecurityConfig.class }),
            @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = { EnableGlobalMethodSecurity.class }),
    }
)
@DisplayName("saving project : ")
class ProjetRestTest {

    @Autowired WebApplicationContext context;
    @Autowired MockMvc mvc;
    @Autowired ObjectMapper objectMapper;
    @MockBean ProjetService projetService;

    ProjetBean regularProjetDto;

    enum ProjectCrossFields { indhNotNull, maitreOuvrageDelNotNull, partnersNotNull; }
    enum ProjectRequiredFields { intitule, montant, localisations, secteur, maitreouvrage, chargesuivi, anneeprojet, srcfinancement; }
    final String VALID_LOCATION_FIELD_NAME = "locationValid";
    final String INVALID_FORMAT_MSG = "Format invalid";
    final String REQUIRED_FIELD_MSG = "Champ obligatoire";
    final String SAVE_PROJECT_URL = "/api/projets";

    @BeforeEach
    void setUp() {

        regularProjetDto = new ProjetBean();

    }

    @Test
    @DisplayName("valid required fields")
    void saveNewProjectValidRequiredFields() throws Exception {

        Projet expectedProj = new Projet(1100);
        initRequiredFields();
        when(projetService.saveProjet(any(ProjetBean.class), any())).thenReturn(expectedProj);

        MvcResult mvcResult = performOkRequest(regularProjetDto);

        assertEquals(expectedProj.getId().toString(), mvcResult.getResponse().getContentAsString());

    }

    @Test
    @DisplayName("Full bean")
    void fullDtoTest() throws Exception {

        fullInitDto();
        Projet expectedProj = new Projet(1100);
        when(projetService.saveProjet(any(ProjetBean.class), any())).thenReturn(expectedProj);

        MvcResult mvcResult = performOkRequest(regularProjetDto);
        assertEquals(expectedProj.getId().toString(), mvcResult.getResponse().getContentAsString());

    }

    @Test
    @DisplayName("empty required fields")
    void saveNewProjetTestingEmptyRequieredFields() throws Exception {
//        ProjetBean emptyProjetDto = new ProjetBean();
        MvcResult mvcResult = performBadRequest(new ProjetBean());

        var exceptionResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ExceptionDto.class);

        // checking if we got 8 (ProjectRequiredFields.values().length) required fields
        assertThat(exceptionResponse.errors.size()).isEqualTo(ProjectRequiredFields.values().length);

        // checking if the required fields name match the errors fields name
        assertEquals(
            new HashSet<>(
                exceptionResponse.errors.stream().map(err -> err.field.toLowerCase()).collect(Collectors.toList())
            ), new HashSet<>(
                Arrays.stream(ProjectRequiredFields.values()).map(field -> field.name().toLowerCase()).collect(Collectors.toList())
            )
        );

        // checking if all the errors contain the message "Champ obligatoire"
        assertTrue(exceptionResponse.errors.stream().allMatch(error -> error.message.contains(REQUIRED_FIELD_MSG)));
    }

    @ParameterizedTest
    @DisplayName("invalid locations")
    @ValueSource(strings = { ".", "1." , "1.1.", "a.a" })
    void invalidLocationsTest(String invalidLocationPath) throws Exception{

        initRequiredFields();
        regularProjetDto.localisations = new ArrayList<>(List.of("1.1", invalidLocationPath));
        MvcResult mvcResult = performBadRequest(regularProjetDto);

        var exceptionResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ExceptionDto.class);
        assertThat(exceptionResponse.errors.size()).isEqualTo(1);
        assertTrue(exceptionResponse.errors.get(0).message.contains(INVALID_FORMAT_MSG));
        assertTrue(exceptionResponse.errors.get(0).field.toLowerCase().equals(VALID_LOCATION_FIELD_NAME.toLowerCase()));

    }

    @Test
    @DisplayName("(cross field validation) empty fields => partners, src financement, maitre ouvrage délégué")
    void emptyCrossFieldsTest() throws Exception {

        initRequiredFields();
        initForCrossFields();

        MvcResult mvcResult = performBadRequest(regularProjetDto);

        var exceptionResponse = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ExceptionDto.class);

        assertThat(exceptionResponse.errors.size()).isEqualTo(ProjectCrossFields.values().length);

        // checking if the required fields name match the errors fields name
        assertEquals(
                new HashSet<>(
                        exceptionResponse.errors.stream().map(err -> err.field.toLowerCase()).collect(Collectors.toList())
                ), new HashSet<>(
                        Arrays.stream(ProjectCrossFields.values()).map(field -> field.name().toLowerCase()).collect(Collectors.toList())
                )
        );

        // checking if all the errors contain the message "Champ obligatoire"
        assertTrue(exceptionResponse.errors.stream().allMatch(error -> error.message.contains(REQUIRED_FIELD_MSG)));

    }







    private void fullInitDto(){
        initRequiredFields();
        initForCrossFields();
        regularProjetDto.indhProgramme=1;
        regularProjetDto.maitreOuvrageDel=1;
        regularProjetDto.partners= new ArrayList<>(Arrays.asList(new PartnerDto(new SimpleDto(1, ""), 1100D)));
    }
    private void initForCrossFields() {
        regularProjetDto.isConvention = true;
        regularProjetDto.srcFinancement = SrcFinancement.INDH.val();
        regularProjetDto.isMaitreOuvrageDel = true;
    }
    private void initRequiredFields() {
        regularProjetDto.intitule = "projetX";
        regularProjetDto.montant = 100000D;
        regularProjetDto.localisations = new ArrayList<>(List.of("1.11"));
        regularProjetDto.secteur = 1;
        regularProjetDto.maitreOuvrage=1;
        regularProjetDto.chargeSuivi=1;
        regularProjetDto.anneeProjet=2020;
        regularProjetDto.srcFinancement= SrcFinancement.BG.val();;
    }
    private MvcResult performOkRequest(Object content) throws Exception {
        return performInit(content).andExpect(status().isOk()).andReturn();

    }

    private ResultActions performInit(Object content) throws Exception {
        return mvc.perform(
                post(SAVE_PROJECT_URL)
                        .content(objectMapper.writeValueAsString(content))
                        .contentType(MediaType.APPLICATION_JSON)
        );
    }

    private MvcResult performBadRequest(Object content) throws Exception {

        return performInit(content).andExpect(status().isBadRequest()).andReturn();
    }
}