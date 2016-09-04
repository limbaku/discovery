package discovery.controller;


import discovery.model.Discover;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;



import java.util.concurrent.ConcurrentHashMap;


import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;


@Ignore("DiscoverController tested using Spock")
public class DiscoveryControllerTestMockito {

    private MockMvc mockMvc;

    @InjectMocks
    private DiscoveryController discoveryController;

    @Mock
    private ConcurrentHashMap<String, Discover> concurrentHashMap;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(discoveryController).build();
    }

    @Test
    public void shouldGetExistingService() throws Exception {
        //given
        String key="lazylogin";
        String value="http://lazylogin.trafalgar.ws/";

        //when
        when(concurrentHashMap.get(key)).thenReturn(new Discover(key,value));

        //then
        mockMvc.perform(get("/discover/lazylogin")).andExpect(status().isOk());
    }

    @Test
    public void shouldReturnNotFoundWhenGettingService() throws Exception {
        //given
        String key="lazylogin";
        String value="http://lazylogin.trafalgar.ws/";

        //when
        when(concurrentHashMap.get(key)).thenReturn(new Discover(key,value));

        //then
        mockMvc.perform(get("/discover/lazylogin2")).andExpect(status().isNotFound());
    }

    @Test
    public void shouldCreateNewService() throws Exception {

        //when
        when(concurrentHashMap.containsKey(anyString())).thenReturn(false);

        //then
        mockMvc.perform(post("/discover/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"key\":\"lazylogin2\", \"value\":\"http://lazylogin2.trafalgar.ws/\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnConflict() throws Exception {
        //when
        when(concurrentHashMap.containsKey(anyString())).thenReturn(true);

        //then
        mockMvc.perform(post("/discover/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"key\":\"lazylogin\", \"value\":\"http://lazylogin.trafalgar.ws/\"}"))
                .andExpect(status().isConflict());
    }

    @Test
    public void shouldUpdateService() throws Exception {
        //given
        String key="lazylogin";
        String value="http://lazylogin.trafalgar.ws/";

        //when
        when(concurrentHashMap.get(key)).thenReturn(new Discover(key,value));

        //then
        mockMvc.perform(put("/discover/lazylogin")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"key\":\"lazylogin\", \"value\":\"http://lazylogin2.trafalgar.ws/\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnNotFoundWhenUpdatingService() throws Exception {
        //given
        String key="lazylogin";
        String value="http://lazylogin.trafalgar.ws/";

        //when
        when(concurrentHashMap.get(key)).thenReturn(new Discover(key,value));

        //then
        mockMvc.perform(put("/discover/lazylogin2")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"key\":\"lazylogin2\", \"value\":\"http://lazylogin2.trafalgar.ws/\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturnConflictWhenUpdatingService() throws Exception {
        //given
        String key="lazylogin";
        String value="http://lazylogin.trafalgar.ws/";

        //when
        when(concurrentHashMap.get(key)).thenReturn(new Discover(key,value));

        //then
        mockMvc.perform(put("/discover/lazylogin")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"key\":\"lazylogin2\", \"value\":\"http://lazylogin.trafalgar.ws/\"}"))
                .andExpect(status().isConflict());
    }

    @Test
    public void shouldDeleteService() throws Exception {
        //given
        String key="lazylogin";
        String value="http://lazylogin.trafalgar.ws/";

        //when
        when(concurrentHashMap.get(key)).thenReturn(new Discover(key,value));

        //then
        mockMvc.perform(delete("/discover/lazylogin")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"key\":\"lazylogin\", \"value\":\"http://lazylogin.trafalgar.ws/\"}"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnNotFoundWhenDeletingService() throws Exception {
        //given
        String key="lazylogin";
        String value="http://lazylogin.trafalgar.ws/";

        //when
        when(concurrentHashMap.get(key)).thenReturn(new Discover(key,value));

        //then
        mockMvc.perform(delete("/discover/lazylogin2")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"key\":\"lazylogin2\", \"value\":\"http://lazylogin2.trafalgar.ws/\"}"))
                .andExpect(status().isNotFound());

    }
}