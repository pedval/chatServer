package com.pedrovalencia.chatserver.controllers;

import com.pedrovalencia.chatserver.domain.Message;
import com.pedrovalencia.chatserver.repositories.ChatRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Matchers.isA;
import static org.mockito.Mockito.when;


/**
 * Created by pedrovalencia on 06/04/15.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MockServletContext.class)
@WebAppConfiguration
public class ChatControllerTest {

    private MockMvc mvc;

    @InjectMocks
    private ChatController controller;

    @Mock
    private ChatRepository repository;

    @Before
    public void setUp() throws Exception {
        repository = Mockito.mock(ChatRepository.class);
        controller = new ChatController();
        controller.setChatRepository(repository);
        mvc = MockMvcBuilders.standaloneSetup(controller).build();

    }

    @Test
    public void getTest() throws Exception {

        when(repository.findOne("1428094981415")).thenReturn(new Message("1428094981415", "Message 4"));

        mvc.perform(MockMvcRequestBuilders.get("/chat/1428094981415").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"timestamp\":\"1428094981415\",\"message\":\"Message 4\"}")));
    }

    @Test
    public void postTest() throws Exception {
        String newMessage = "{\"timestamp\":\"1428094981715\",\"message\":\"Message 5\"}";
        mvc.perform(MockMvcRequestBuilders.post("/chat").contentType(MediaType.APPLICATION_JSON).content(newMessage))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"code\":\"0000\",\"description\":\"Message created\"}")));
    }

    @Test
    public void deleteTest() throws Exception {
        mvc.perform(MockMvcRequestBuilders.delete("/chat/1428094981415").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"code\":\"0001\",\"description\":\"Message deleted\"}")));
    }

    @Test
    public void updateTest() throws Exception {
        String newMessage = "{\"timestamp\":\"1423094981715\",\"message\":\"Message 6\"}";
        mvc.perform(MockMvcRequestBuilders.put("/chat/1423094981715").contentType(MediaType.APPLICATION_JSON).content(newMessage))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("{\"code\":\"0002\",\"description\":\"Message updated\"}")));
    }
}
