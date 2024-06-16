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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DiplomApplication.class)
@AutoConfigureMockMvc
class AuthControllerTest {

    @Autowired
    private MockMvc mvc;

    @SneakyThrows
    @Test
    void loginPage() {
        this.mvc.perform(get("/auth/login"))
                .andExpect(view().name("auth/login"));
    }

    @SneakyThrows
    @Test
    @WithMockUser(username = "main", roles = {"ADMIN"})
    void registrationPage() {
        this.mvc.perform(get("/auth/registration"))
                .andExpect(model().attributeExists("person"))
                .andExpect(view().name("auth/registration"));
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "main", roles = {"ADMIN"})
    void performRegistration() {
        this.mvc.perform(post("/auth/registration")
                .param("username", "main2")
                .param("password", "$2a$10$u9uebCub1/Jb2JHvnuRprOXIwv9PCBGu72rrI/l6gcEUje2qghaZ6")
                .param("role", "ROLE_USER").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/auth/login"));
    }
}