package com.tinnova.teste.infra.restapi;

import application.AbstractTest;
import application.ClearContext;
import com.tinnova.teste.TesteApplication;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TesteApplication.class)
@WebAppConfiguration
@ClearContext
public class VehicleControllerTests extends AbstractTest {

    @Test
    @ClearContext
    public void findAll() throws Exception {
        final String payload = "{\"name\":\"Punto\",\"brand\":\"Fiat\",\"year\":2011,\"description\":\"vermelho\",\"sold\":false}";

        this.mockMvc.perform(post("/vehicle")
                .content(payload)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        this.mockMvc.perform(get("/vehicle").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", equalTo(1)));
    }

    @Test
    @ClearContext
    public void findById() throws Exception {
        final String payload = "{\"name\":\"Punto\",\"brand\":\"Fiat\",\"year\":2011,\"description\":\"vermelho\",\"sold\":false}";

        this.mockMvc.perform(post("/vehicle")
                .content(payload)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        this.mockMvc.perform(get("/vehicle/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Punto")))
                .andExpect(jsonPath("$.brand", equalTo("Fiat")))
                .andExpect(jsonPath("$.year", equalTo(2011)))
                .andExpect(jsonPath("$.description", equalTo("vermelho")))
                .andExpect(jsonPath("$.sold", equalTo(false)))
                .andExpect(jsonPath("$.created", equalTo(LocalDate.now().toString())))
                .andExpect(jsonPath("$.updated", equalTo(null)));
    }

    @Test
    @ClearContext
    public void create() throws Exception {

        final String payload = "{\"name\":\"Punto\",\"brand\":\"Fiat\",\"year\":2011,\"description\":\"vermelho\",\"sold\":false}";

        this.mockMvc.perform(post("/vehicle")
                .content(payload)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo("Punto")))
                .andExpect(jsonPath("$.brand", equalTo("Fiat")))
                .andExpect(jsonPath("$.year", equalTo(2011)))
                .andExpect(jsonPath("$.description", equalTo("vermelho")))
                .andExpect(jsonPath("$.sold", equalTo(false)))
                .andExpect(jsonPath("$.created", equalTo(LocalDate.now().toString())))
                .andExpect(jsonPath("$.updated", equalTo(null)));
    }

    @Test
    @ClearContext
    public void update() throws Exception {
        final String payload = "{\"name\":\"Punto\",\"brand\":\"Fiat\",\"year\":2011,\"description\":\"vermelho\",\"sold\":false}";

        this.mockMvc.perform(post("/vehicle")
                .content(payload)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        final String payloadUpdate = "{\"name\":\"Punto\",\"brand\":\"Fiat\",\"year\":2015,\"description\":\"vermelho\",\"sold\":false}";

        this.mockMvc.perform(put("/vehicle/1")
                .content(payloadUpdate)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Punto")))
                .andExpect(jsonPath("$.brand", equalTo("Fiat")))
                .andExpect(jsonPath("$.year", equalTo(2015)))
                .andExpect(jsonPath("$.description", equalTo("vermelho")))
                .andExpect(jsonPath("$.sold", equalTo(false)))
                .andExpect(jsonPath("$.created", equalTo(LocalDate.now().toString())))
                .andExpect(jsonPath("$.updated", equalTo(LocalDate.now().toString())));
    }

    @Test
    @ClearContext
    public void sold() throws Exception {
        final String payload = "{\"name\":\"Punto\",\"brand\":\"Fiat\",\"year\":2011,\"description\":\"vermelho\",\"sold\":false}";

        this.mockMvc.perform(post("/vehicle")
                .content(payload)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());


        this.mockMvc.perform(patch("/vehicle/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Punto")))
                .andExpect(jsonPath("$.brand", equalTo("Fiat")))
                .andExpect(jsonPath("$.year", equalTo(2011)))
                .andExpect(jsonPath("$.description", equalTo("vermelho")))
                .andExpect(jsonPath("$.sold", equalTo(true)))
                .andExpect(jsonPath("$.created", equalTo(LocalDate.now().toString())))
                .andExpect(jsonPath("$.updated", equalTo(LocalDate.now().toString())));
    }

    @Test
    @ClearContext
    public void remove() throws Exception {
        final String payload = "{\"name\":\"Punto\",\"brand\":\"Fiat\",\"year\":2011,\"description\":\"vermelho\",\"sold\":false}";

        this.mockMvc.perform(post("/vehicle")
                .content(payload)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        this.mockMvc.perform(delete("/vehicle/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @ClearContext
    public void notSold() throws Exception {
        final String payload = "{\"name\":\"Punto\",\"brand\":\"Fiat\",\"year\":2011,\"description\":\"vermelho\",\"sold\":false}";

        this.mockMvc.perform(post("/vehicle")
                .content(payload)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        this.mockMvc.perform(get("/vehicle/not-sold")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", equalTo(1)));
    }

    @Test
    @ClearContext
    public void getAllPerDecade() throws Exception {
        final String payload = "{\"name\":\"Punto\",\"brand\":\"Fiat\",\"year\":2011,\"description\":\"vermelho\",\"sold\":false}";

        this.mockMvc.perform(post("/vehicle")
                .content(payload)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        this.mockMvc.perform(get("/vehicle/per-decade")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", equalTo(1)));
    }

    @Test
    @ClearContext
    public void getAllByManufacturer() throws Exception {
        final String payload = "{\"name\":\"Punto\",\"brand\":\"Fiat\",\"year\":2011,\"description\":\"vermelho\",\"sold\":false}";

        this.mockMvc.perform(post("/vehicle")
                .content(payload)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        this.mockMvc.perform(get("/vehicle/by-manufacturer")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", equalTo(1)));
    }

    @Test
    @ClearContext
    public void getAllLastWeek() throws Exception {
        final String payload = "{\"name\":\"Punto\",\"brand\":\"Fiat\",\"year\":2011,\"description\":\"vermelho\",\"sold\":false}";

        this.mockMvc.perform(post("/vehicle")
                .content(payload)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());

        this.mockMvc.perform(get("/vehicle/last-week")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", equalTo(1)));
    }
}
