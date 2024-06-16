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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DiplomApplication.class)
@AutoConfigureMockMvc
class StatisticsControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @SneakyThrows
    @WithMockUser(username = "main", roles = {"ADMIN"})
    void statistics() {
        this.mvc.perform(get("/statistics"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("years"))
                .andExpect(view().name("statistics/index"));
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "main", roles = {"ADMIN"})
    void show() {
        this.mvc.perform(get("/statistics/{year}", 2024))
                .andExpect(status().isOk())
                .andExpect(view().name("statistics/show"));
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "main", roles = {"ADMIN"})
    void resides() {
        this.mvc.perform(get("/statistics/{year}/resides", 2024))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("chartResides"))
                .andExpect(view().name("statistics/resides"));
    }

    @Test
    @SneakyThrows
    @WithMockUser(username = "main", roles = {"ADMIN"})
    void incomes() {
        this.mvc.perform(get("/statistics/{year}/incomes", 2024))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("chartIncomes"))
                .andExpect(view().name("statistics/incomes"));
    }
}