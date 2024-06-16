package testContainers;


import lombok.SneakyThrows;
import org.junit.Test;
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
public class AdminControllerTest {

    @Autowired
    private MockMvc mvc;

   @SneakyThrows
   @Test
   @WithMockUser(username = "main", roles = {"ADMIN"})
    public void testIndex() {
        this.mvc.perform(get("/admin"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/options"))
                .andExpect(model().attributeExists("users"));
    }

    @SneakyThrows
    @Test
    @WithMockUser(username = "main", roles = {"ADMIN"})
    public void testFindOne() {
       int id = 3;
       this.mvc.perform(get("/admin/{id}", id))
               .andExpect(status().isOk())
               .andExpect(view().name("admin/show_user"))
               .andExpect(model().attributeExists("user"));
    }

    @SneakyThrows
    @Test
    @WithMockUser(username = "main", roles = {"ADMIN"})
    public void testEdit() {
       int id = 3;
       this.mvc.perform(get("/admin/{id}/edit", id))
               .andExpect(status().isOk())
               .andExpect(view().name("admin/edit_user"))
               .andExpect(model().attributeExists("user"));
    }

    @SneakyThrows
    @Test
    @WithMockUser(username = "main", roles = {"ADMIN"})
    public void testUpdate() {
       this.mvc.perform(patch("/admin/{id}", 6)
                       .param("id", "1")
                       .param("username", "admin")
                       .param("password", "$2a$10$u9uebCub1/Jb2JHvnuRprOXIwv9PCBGu72rrI/l6gcEUje2qghaZ6")
                       .param("role", "ROLE_USER")
                       .with(csrf()))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/admin"));
    }

    @SneakyThrows
    @Test
    @WithMockUser(username = "main", roles = {"ADMIN"})
    public void testDelete() {
       int id = 3;
       this.mvc.perform(delete("/admin/{id}", id)
                       .with(csrf()))
               .andExpect(status().is3xxRedirection())
               .andExpect(redirectedUrl("/admin"));
    }
}
