package api.controllers;

import api.beans.ProjetBean;
import api.dto.ExceptionDto;
import api.enums.ProjectRequiredFields;
import api.security.WebSecurityConfig;
import api.services.ProjetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
import org.springframework.web.context.WebApplicationContext;

import java.util.*;
import java.util.stream.Collectors;

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
class ProjetRestTest {

    @Autowired WebApplicationContext context;
    @Autowired MockMvc mvc;
    @Autowired ObjectMapper objectMapper;

    @MockBean ProjetService projetService;


    ProjetBean regularProjetDto = new ProjetBean();

    @BeforeEach
    void setUp() {

        // 8 required fields
        regularProjetDto.intitule = "projetX";
        regularProjetDto.montant = 100000D;
        regularProjetDto.localisations = new ArrayList<>(List.of("1.11."));
        regularProjetDto.secteur = 1;
        regularProjetDto.maitreOuvrage=1;
        regularProjetDto.chargeSuivi=1;
        regularProjetDto.anneeProjet=2020;
        regularProjetDto.srcFinancement=1;
    }

    @Test
    @DisplayName("WHEN empty required fields THEN bad request 400")
    void saveNewProjetTestingEmptyRequieredFields() throws Exception {
        ProjetBean emptyProjetDto = new ProjetBean();
        MvcResult mvcResult = mvc.perform(
                post("/api/projets")
                        .content(objectMapper.writeValueAsString(emptyProjetDto))
                        .contentType(MediaType.APPLICATION_JSON)
        )
        // checking we have a bad request (400)
        .andExpect(status().isBadRequest())
        .andReturn();

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
        assertTrue(exceptionResponse.errors.stream().allMatch(error -> error.message.contains("Champ obligatoire")));
    }
}