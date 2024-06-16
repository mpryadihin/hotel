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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DiplomApplication.class)
@AutoConfigureMockMvc
class TypeRoomControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @SneakyThrows
    @WithMockUser(username = "main", roles = {"ADMIN"})
    void index() {
        this.mvc.perform(get("/typeRoom"))
                .andExpect(status().isOk())
                .andExpect(view().name("typeRoom/index"))
                .andExpect(model().attributeExists("type"));
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "main", roles = {"ADMIN"})
    void show() {
        this.mvc.perform(get("/typeRoom/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(view().name("typeRoom/show"))
                .andExpect(model().attributeExists("type"));
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "main", roles = {"ADMIN"})
    void newType() {
        this.mvc.perform(get("/typeRoom/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("type"))
                .andExpect(view().name("typeRoom/new"));
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "main", roles = {"ADMIN"})
    void create() {
        this.mvc.perform(post("/typeRoom")
                        .param("name", "Люкс 2")
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/typeRoom"));
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "main", roles = {"ADMIN"})
    void edit() {
        this.mvc.perform(get("/typeRoom/{id}/edit", 7))
                .andExpect(status().isOk())
                .andExpect(view().name("typeRoom/edit"))
                .andExpect(model().attributeExists("type"));
    }


    @Test
    @SneakyThrows
    @WithMockUser(username = "main", roles = {"ADMIN"})
    void update() {
        this.mvc.perform(patch("/typeRoom/{id}", 7)
                    .param("name", "Люкс 3")
                    .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/typeRoom"));
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "main", roles = {"ADMIN"})
    void testDelete() {
        this.mvc.perform(delete("/typeRoom/{id}", 8)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/typeRoom"));
    }
}