package api.controllers;

import api.ProjectsApplication;
import api.beans.ProjetBean;
import api.dao.GenericDao;
import api.dao.interfaces.IUserDao;
import api.entities.Projet;
import api.security.WebSecurityConfig;
import api.security.filters.JwtAuthorizationFilter;
import api.services.JwtService;
import api.services.ProjetService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.MockitoAnnotations.initMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;

import static org.junit.jupiter.api.Assertions.*;



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

    @BeforeEach
    void setUp() {}

    @Test
    @DisplayName("WHEN empty required fields THEN bad request 400")
    void saveNewProjetTestingEmptyRequieredFields() throws Exception {
        mvc.perform(
                post("/api/projets")
                .content(objectMapper.writeValueAsString(new ProjetBean()))
                .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isBadRequest());
    }
}