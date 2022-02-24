package com.digitalclash.mvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
class PetControllerTest {

    // @Test
    // void returnsXmlDogWithQParam(@Autowired MockMvc mvc) throws Exception {
    // mvc.perform(get("/dog").param("name", "Makenna").param("format",
    // "xml")).andDo(print()).andExpect(status().isOk()).andExpect(xpath("/Dog/name").string("Makenna"));
    // }

    @Test
    void returnsXmlDogByPathExtension(@Autowired MockMvc mvc) throws Exception {
        mvc.perform(get("/pets/dog.xml").param("name", "Otis")).andDo(print()).andExpect(status().isOk())
                .andExpect(xpath("/Dog/name").string("Otis"));
    }

    @Test
    void returnsJsonDogByPathExtension(@Autowired MockMvc mvc) throws Exception {
        mvc.perform(get("/pets/dog.json").param("name", "Emma")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Emma"));
    }

    @Test
    void returnsXmlSeaOtterByPathExtension(@Autowired MockMvc mvc) throws Exception {
        mvc.perform(get("/pets/seaotter.xml").param("name", "Fred")).andDo(print()).andExpect(status().isOk())
                .andExpect(xpath("/SeaOtter/name").string("Fred"));
    }

    @Test
    void returnsJsonSeaOtterByPathExtension(@Autowired MockMvc mvc) throws Exception {
        mvc.perform(get("/pets/seaotter.json").param("name", "Fred")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Fred"));
    }

    @Test
    void returnsXmlDogByParam(@Autowired MockMvc mvc) throws Exception {
        mvc.perform(get("/pets/dog").param("name", "Otis").param("format", "xml")).andDo(print())
                .andExpect(status().isOk()).andExpect(xpath("/Dog/name").string("Otis"));
    }

    @Test
    void returnsJsonDogByParam(@Autowired MockMvc mvc) throws Exception {
        mvc.perform(get("/pets/dog").param("name", "Emma").param("format", "json")).andDo(print())
                .andExpect(status().isOk()).andExpect(jsonPath("$.name").value("Emma"));
    }

    @Test
    void returnsXmlSeaOtterByParam(@Autowired MockMvc mvc) throws Exception {
        mvc.perform(get("/pets/seaotter").param("name", "Fred").param("format", "xml")).andDo(print())
                .andExpect(status().isOk()).andExpect(xpath("/SeaOtter/name").string("Fred"));
    }

    @Test
    void returnsJsonSeaOtterByParam(@Autowired MockMvc mvc) throws Exception {
        mvc.perform(get("/pets/seaotter").param("name", "Fred").param("format", "json")).andDo(print())
                .andExpect(status().isOk()).andExpect(jsonPath("$.name").value("Fred"));
    }

}
