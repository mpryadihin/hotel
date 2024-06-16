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
class ClientControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @SneakyThrows
    @WithMockUser(username = "main", roles = {"ADMIN"})
    void index() {
        this.mvc.perform(get("/client"))
                .andExpect(model().attributeExists("clients"))
                .andExpect(view().name("client/index"));
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "main", roles = {"ADMIN"})
    void show() {
        int id = 15018;
        this.mvc.perform(get("/client/{id}", id))
                .andExpect(model().attributeExists("client"))
                .andExpect(view().name("client/show"));
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "main", roles = {"ADMIN"})
    void search() {
        this.mvc.perform(get("/client/search")
                .param("surname", "Прядихин"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("search"))
                .andExpect(view().name("client/index"));
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "main", roles = {"ADMIN"})
    void newClient() {
        this.mvc.perform(get("/client/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("client"))
                .andExpect(view().name("client/new"));
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "main", roles = {"ADMIN"})
    void create() {
        this.mvc.perform(post("/client")
                        .param("name", "Петр")
                        .param("surname", "Иванов")
                        .param("patronymic", "Иванович")
                        .param("passport", "6616 849093")
                        .param("birthday", "19.06.2002")
                        .param("info", " ")
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/client"));
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "main", roles = {"ADMIN"})
    void edit() {
        this.mvc.perform(get("/client/{id}/edit", 15019))
                .andExpect(status().isOk())
                .andExpect(view().name("client/edit"))
                .andExpect(model().attributeExists("client"));
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "main", roles = {"ADMIN"})
    void update() {
        this.mvc.perform(patch("/client/{id}", 15019)
                .param("name", "Дмитрий")
                .param("surname", "Иванов")
                .param("patronymic", "Иванович")
                .param("passport", "6616 849093")
                .param("birthday", "19.06.2002")
                .param("info", " ")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/client"));
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "main", roles = {"ADMIN"})
    void testDelete() {
        this.mvc.perform(delete("/client/{id}", 15019)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/client"));
    }
}