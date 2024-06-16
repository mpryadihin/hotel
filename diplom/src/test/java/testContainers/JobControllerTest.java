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
class JobControllerTest {

    @Autowired
    private MockMvc mvc;

    @SneakyThrows
    @Test
    @WithMockUser(username = "main", roles = {"ADMIN"})
    void index() {
        this.mvc.perform(get("/job"))
                .andExpect(status().isOk())
                .andExpect(view().name("job/index"))
                .andExpect(model().attributeExists("job"));
    }

    @SneakyThrows
    @Test
    @WithMockUser(username = "main", roles = {"ADMIN"})
    void show() {
        this.mvc.perform(get("/job/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("job/show"))
                .andExpect(model().attributeExists("job"))
                .andExpect(model().attributeExists("sentences"));
    }

    @SneakyThrows
    @Test
    @WithMockUser(username = "main", roles = {"ADMIN"})
    void newJob() {
        this.mvc.perform(get("/job/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("job"))
                .andExpect(view().name("job/new"));
    }

    @SneakyThrows
    @Test
    @WithMockUser(username = "main", roles = {"ADMIN"})
    void create() {
        this.mvc.perform(post("/job")
                        .param("name", "Швейцар 2")
                        .param("resp", "Отслеживать приходящих и уходящих гостей")
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/job"));
    }

    @SneakyThrows
    @Test
    @WithMockUser(username = "main", roles = {"ADMIN"})
    void edit() {
        this.mvc.perform(get("/job/{id}/edit", 7))
                .andExpect(status().isOk())
                .andExpect(view().name("job/edit"))
                .andExpect(model().attributeExists("job"));
    }

    @SneakyThrows
    @Test
    @WithMockUser(username = "main", roles = {"ADMIN"})
    void update() {
        this.mvc.perform(patch("/job/{id}", 7)
                        .param("name", "Швейцар 3")
                        .param("resp", "Отслеживать приходящих и уходящих гостей")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/job"));
    }

    @SneakyThrows
    @Test
    @WithMockUser(username = "main", roles = {"ADMIN"})
    void testDelete() {
        this.mvc.perform(delete("/job/{id}", 7)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/job"));
    }
}