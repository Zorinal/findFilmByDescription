package org.vced.filmByDescription;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class LoginTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @SneakyThrows
    void should_return_redirect_for_anonymous_user() {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @SneakyThrows
    void should_return_login_page_for_anonymous_user() {
        this.mockMvc.perform(get("/login"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("Войти")));
    }

    @WithMockUser(username = "user")
    @Test
    @SneakyThrows
    void should_redirect_to_main_page_after_successful_login() {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(authenticated())
                .andExpect(content().string(containsString("Find film by description")));
    }

    @WithMockUser(username = "user")
    @Test
    @SneakyThrows
    void should_return_main_page_for_authenticated_user() {
        this.mockMvc.perform(formLogin().user("admin").password("admin"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
    @WithMockUser(username = "user")
    @Test
    @SneakyThrows
    void should_return_forbidden_for_incorrect_login() {
        this.mockMvc.perform(post("/login").param("user", "Alfred"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}