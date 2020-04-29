package api.beans;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ProjetBeanTest {

    ProjetBean dto;

    @BeforeEach
    void setUp() {
        dto = new ProjetBean();
        dto.localisations = new ArrayList<>(List.of("20", "20.11"));
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    @DisplayName("valid locations")
    void validLocationTest() {
        assertTrue(dto.isLocationValid());
    }

    @ParameterizedTest
    @DisplayName("invalid locations")
    @ValueSource(strings = { ".", "a", "1a", "0", ".1", "1.", ".1.1." , "1.0", "1.a", "1..1", "10.10.10" })
    void invalidLocationTest(String location) {
        dto.localisations.add(location);
        assertFalse(dto.isLocationValid());
    }

}