package discovery.controller;


import discovery.Application;
import discovery.model.Discover;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = Application.class)
@Ignore("DiscoverController tested using Spock")
public class DiscoveryControllerTestSpring {

    private MockMvc mockMvc;

    @Autowired
    private DiscoveryController discoveryController;

    @Autowired
    private ConcurrentHashMap<String, Discover> concurrentHashMap;


    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(discoveryController).build();
    }

    @Test
    public void shouldGetExistingService() throws Exception {
        //then
        mockMvc.perform(get("/discover/lazylogin")).andExpect(status().isOk());
    }

    @Test
    public void shouldReturnNotFoundWhenGettingService() throws Exception {
        //then
        mockMvc.perform(get("/discover/lazylogin4")).andExpect(status().isNotFound());
    }

    @Test
    public void shouldCreateNewService() throws Exception {
        //then
        mockMvc.perform(post("/discover/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"key\":\"lazylogin2\", \"value\":\"http://lazylogin2.trafalgar.ws/\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnConflict() throws Exception {
        //then
        mockMvc.perform(post("/discover/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"key\":\"discovery\", \"value\":\"http://discovery.trafalgar.ws/\"}"))
                .andExpect(status().isConflict());
    }

    @Test
    public void shouldUpdateService() throws Exception {
        //then
        mockMvc.perform(put("/discover/lazylogin")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"key\":\"lazylogin\", \"value\":\"http://lazylogin2.trafalgar.ws/\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnNotFoundWhenUpdatingService() throws Exception {
        //then
        mockMvc.perform(put("/discover/lazylogin4")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"key\":\"lazylogin4\", \"value\":\"http://lazylogin4.trafalgar.ws/\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnConflictWhenUpdatingService() throws Exception {
        //then
        mockMvc.perform(put("/discover/lazylogin")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"key\":\"lazylogin2\", \"value\":\"http://lazylogin.trafalgar.ws/\"}"))
                .andExpect(status().isConflict());
    }

    @Test
    public void shouldDeleteService() throws Exception {
        //then
        mockMvc.perform(delete("/discover/gamemanager")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"key\":\"gamemanager\", \"value\":\"http://gamemanager.trafalgar.ws/\"}"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnNotFoundWhenDeletingService() throws Exception {
        //then
        mockMvc.perform(delete("/discover/lazylogin2")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"key\":\"lazylogin2\", \"value\":\"http://lazylogin2.trafalgar.ws/\"}"))
                .andExpect(status().isNotFound());
    }
}