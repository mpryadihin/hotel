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
class RoomControllerTest {

    @Autowired
    private MockMvc mvc;

    @SneakyThrows
    @Test
    @WithMockUser(username = "main", roles = {"ADMIN"})
    void index() {
        this.mvc.perform(get("/room"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("rooms"))
                .andExpect(view().name("room/index"));
    }

    @SneakyThrows
    @Test
    @WithMockUser(username = "main", roles = {"ADMIN"})
    void showFreeRooms() {
        this.mvc.perform(get("/room/freeRooms"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("rooms"))
                .andExpect(view().name("room/index"));
    }

    @SneakyThrows
    @Test
    @WithMockUser(username = "main", roles = {"ADMIN"})
    void show() {
        this.mvc.perform(get("/room/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("room"))
                .andExpect(model().attributeExists("date"))
                .andExpect(view().name("room/show"));
    }

    @SneakyThrows
    @Test
    @WithMockUser(username = "main", roles = {"ADMIN"})
    void newRoom() {
        this.mvc.perform(get("/room/new"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("types"))
                .andExpect(model().attributeExists("personal"))
                .andExpect(view().name("room/new"));

    }
    @SneakyThrows
    @Test
    @WithMockUser(username = "main", roles = {"ADMIN"})
    void create() {
        this.mvc.perform(post("/room")
                        .param("idTypeRoom", "2")
                        .param("price", "2200")
                        .param("idPersonal", "3")
                        .param("seats", "4")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/room"));

    }

    @SneakyThrows
    @Test
    @WithMockUser(username = "main", roles = {"ADMIN"})
    void edit() {
        this.mvc.perform(get("/room/{id}/edit", 3))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("room"))
                .andExpect(model().attributeExists("types"))
                .andExpect(model().attributeExists("personal"))
                .andExpect(view().name("room/edit"));
    }

    @SneakyThrows
    @Test
    @WithMockUser(username = "main", roles = {"ADMIN"})
    void update() {
        this.mvc.perform(patch("/room/{id}", 3)
                .param("idTypeRoom", "2")
                .param("price", "2200")
                .param("idPersonal", "3")
                .param("seats", "7")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/room"));
    }


    @SneakyThrows
    @Test
    @WithMockUser(username = "main", roles = {"ADMIN"})
    void testDelete() {
        this.mvc.perform(delete("/room/{id}", 3)
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/room"));
    }
}