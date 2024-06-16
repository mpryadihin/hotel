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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DiplomApplication.class)
@AutoConfigureMockMvc
class ScheduleControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @SneakyThrows
    @WithMockUser(username = "main", roles = {"ADMIN"})
    void index() {
        this.mvc.perform(get("/schedule")
                        .param("year", "2024")
                        .param("month", "5"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("monthValue"))
                .andExpect(model().attributeExists("scheduleList"))
                .andExpect(model().attributeExists("personal"))
                .andExpect(model().attributeExists("days"))
                .andExpect(view().name("schedule/index"));
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "main", roles = {"ADMIN"})
    void edit() {
        this.mvc.perform(get("/schedule/edit")
                        .param("year", "2024")
                        .param("month", "5"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("monthValue"))
                .andExpect(model().attributeExists("scheduleListDTO"))
                .andExpect(model().attributeExists("personal"))
                .andExpect(model().attributeExists("days"))
                .andExpect(view().name("schedule/edit"));
    }
}