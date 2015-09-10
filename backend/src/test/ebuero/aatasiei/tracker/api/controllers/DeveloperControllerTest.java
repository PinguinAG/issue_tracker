package ebuero.aatasiei.tracker.api.controllers;

import ebuero.aatasiei.tracker.services.interfaces.DeveloperService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MockServletContext.class)
@WebAppConfiguration
public class DeveloperControllerTest {

    private MockMvc mvc;

    @Mock
    private DeveloperService developerService;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        mvc = MockMvcBuilders.standaloneSetup(new DeveloperController(developerService)).build();
    }

    @Test
    public void getAll() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/developers/").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"developers\":[]}")));
    }
}
