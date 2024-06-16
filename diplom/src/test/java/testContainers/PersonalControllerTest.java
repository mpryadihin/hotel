package testContainers;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.mpryadihin.diplom.DiplomApplication;

import java.nio.charset.StandardCharsets;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DiplomApplication.class)
@AutoConfigureMockMvc
class PersonalControllerTest {

    @Autowired
    private MockMvc mvc;
    
    @SneakyThrows
    @Test
    @WithMockUser(username = "main", roles = {"ADMIN"})
    void index() {
        this.mvc.perform(get("/personal"))
                .andExpect(model().attributeExists("personal"))
                .andExpect(view().name("personal/index"));
    }



    @SneakyThrows
    @Test
    @WithMockUser(username = "main", roles = {"ADMIN"})
    void newPersonal() {
        this.mvc.perform(get("/personal/new"))
                .andExpect(model().attributeExists("personal"))
                .andExpect(model().attributeExists("job"))
                .andExpect(view().name("personal/new"));
    }

    @SneakyThrows
    @Test
    @WithMockUser(username = "main", roles = {"ADMIN"})
    void create() {
        MockMultipartFile file = new MockMultipartFile("file", "test.jpg", MediaType.IMAGE_JPEG_VALUE, "Some image content".getBytes(StandardCharsets.UTF_8));
        mvc.perform(multipart("/personal")
                        .file(file)
                        .param("name", "Иван")
                        .param("surname", "Иванов")
                        .param("patronymic", "Иванович")
                        .param("birthday", "19.06.2002")
                        .param("idJob", "1")
                        .param("salary", "300")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/personal"));
    }

    @SneakyThrows
    @Test
    @WithMockUser(username = "main", roles = {"ADMIN"})
    void edit() {
        mvc.perform(get("/personal/{id}/edit", 6))
                .andExpect(status().isOk())
                .andExpect(view().name("personal/edit"));
    }


    @SneakyThrows
    @Test
    @WithMockUser(username = "main", roles = {"ADMIN"})
    void testDelete() {
        mvc.perform(delete("/personal/{id}", 6)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/personal"));
    }

    @SneakyThrows
    @Test
    @WithMockUser(username = "main", roles = {"ADMIN"})
    void getImage() {
        mvc.perform(get("/personal/image/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.IMAGE_JPEG));
    }
}