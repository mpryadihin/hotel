package testContainers;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.mpryadihin.diplom.DiplomApplication;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DiplomApplication.class)
@AutoConfigureMockMvc
class ResidesControllerTest {

    @Autowired
    private MockMvc mvc;

    @SneakyThrows
    @Test
    @WithMockUser(username = "main", roles = {"ADMIN"})
    void index() {
        this.mvc.perform(get("/resides"))
                .andExpect(status().isOk())
                .andExpect(view().name("resides/index"))
                .andExpect(model().attributeExists("resides"));
    }

    @SneakyThrows
    @Test
    @WithMockUser(username = "main", roles = {"ADMIN"})
    void show() {
        this.mvc.perform(get("/resides/{id}", 8))
                .andExpect(status().isOk())
                .andExpect(view().name("resides/show"));
    }

    @SneakyThrows
    @Test
    @WithMockUser(username = "main", roles = {"ADMIN"})
    void newResides() {
        this.mvc.perform(get("/resides/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("resides/new"));
    }

    @SneakyThrows
    @Test
    @WithMockUser(username = "main", roles = {"ADMIN"})
    void create() {
        this.mvc.perform(post("/resides")
                        .param("idClient", "15018")
                        .param("idRoom", "2")
                        .param("dateIn", "10.06.2024")
                        .param("dateOut", "13.06.2024")
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/resides"));
    }

    @SneakyThrows
    @Test
    @WithMockUser(username = "main", roles = {"ADMIN"})
    void edit() {
        this.mvc.perform(get("/resides/{id}/edit", 13))
                .andExpect(status().isOk())
                .andExpect(view().name("resides/edit"));
    }

    @SneakyThrows
    @Test
    @WithMockUser(username = "main", roles = {"ADMIN"})
    void testDelete() {
        this.mvc.perform(delete("/resides/{id}", 14)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/resides"));
    }
}