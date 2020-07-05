package com.example.demo;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static com.example.demo.UsersController.context;
import static com.example.demo.UsersController.path;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
public class UserControllerTest {
    private MockMvc mockMvc;
    @InjectMocks
    public UsersController usersController;

    @Before
    public void setUp()  {
        mockMvc = MockMvcBuilders.standaloneSetup(usersController).build();
        StorageProcessing storageProcessing = context.getBean("storageProcessing", StorageProcessing.class);
        storageProcessing.getUsersFromFile(path);
    }

    @Test
    public void ControllerTest() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.get("/1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content()
                        .string("{\"name\":\"name1\",\"familyName\":\"familyName1\",\"birthday\":\"date1\",\"id\":\"1\"}"));
        mockMvc.perform(get("/1"))
                .andExpect(status().is2xxSuccessful())
                .andReturn();
    }
}
