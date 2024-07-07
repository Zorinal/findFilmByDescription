package org.vced.filmByDescription;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor
@WithMockUser(username = "user") // аутентефицированный пользователь с таким то именем
@TestPropertySource("/application-test.properties") // там создали бд специально для тестирования
@Sql(value = {"/create-user-before.sql", "/films-list-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS) // скрипт, который нужно выполнить перед тестами
@Sql(value = {"/films-list-after.sql", "/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_CLASS)
class FilmControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Test
    @SneakyThrows
    void should_present_main_page_with_four_films(){
        // необходимо, чтобы html соответствовал формату xml
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id='film-list']/div").nodeCount(4));
    }
    @WithMockUser(username = "mail@mail.com")
    @Test
    @SneakyThrows
    void should_present_main_page_with_no_films(){
        // необходимо, чтобы html соответствовал формату xml
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id='film-list']/div").nodeCount(0));
    }
}