package org.example.calcularcuota;

import  org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@WebMvcTest(controllers = org.example.controller.CalculadoraController.class)
@EnableWebMvc
class CalculadoraControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testInteresSimple() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/finanzas/interes-simple")
                        .param("monto", "1000")
                        .param("tasa", "12")
                        .param("meses", "24"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("240.0"));
    }

    @Test
    void testInteresCompuesto() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/finanzas/interes-compuesto")
                        .param("monto", "1000")
                        .param("tasa", "12")
                        .param("meses", "24"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("1269.73"));
    }

    @Test
    void testCuota() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/finanzas/cuota")
                        .param("monto", "1000000")
                        .param("tasa", "12")
                        .param("meses", "24"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("47073.47"));
    }
}
