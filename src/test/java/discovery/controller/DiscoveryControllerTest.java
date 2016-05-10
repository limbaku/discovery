package discovery.controller;


import discovery.configuration.DiscoveryConfig;
import discovery.model.Discover;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.http.ResponseEntity;


import java.util.concurrent.ConcurrentHashMap;

import static com.jayway.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;



public class DiscoveryControllerTest {

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
    public void testGetServiceAvailable() throws Exception {
        //when(discoveryController.getService("lazylogin")).thenReturn(new ResponseEntity<String>("http://lazylogin.trafalgar.ws/", HttpStatus.OK));
        when(concurrentHashMap.get("lazylogin")).thenReturn(new Discover("lazylogin","http://lazylogin.trafalgar.ws/"));
        mockMvc.perform(get("/discover/lazylogin")).andExpect(status().isOk());
    }

    @Test
    public void testGetServiceNotAvailable() throws Exception {
        when(concurrentHashMap.get("lazylogin2")).thenReturn(new Discover("lazylogin2","http://lazylogin2.trafalgar.ws/"));
        mockMvc.perform(get("/discover/lazylogin")).andExpect(status().isNotFound());
    }

    @Test
    public void testCreateServiceDoNotExist() throws Exception {
        ConcurrentHashMap<String,Discover> testHashMap = new ConcurrentHashMap<String, Discover>();
        testHashMap.put("lazylogin",new Discover("lazylogin","http://lazylogin.trafalgar.ws/"));

        when(concurrentHashMap.keySet()).thenReturn(testHashMap.keySet());
        //when(concurrentHashMap.put("lazylogin3",new Discover("lazylogin3","http://lazylogin3.trafalgar.ws/"))).thenReturn(new Discover("lazylogin3","http://lazylogin3.trafalgar.ws/"));

        mockMvc.perform(post("/discover/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"key\":\"lazylogin2\", \"value\":\"http://lazylogin2.trafalgar.ws/\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateServiceExists() throws Exception {
        ConcurrentHashMap<String,Discover> testHashMap = new ConcurrentHashMap<String, Discover>();
        testHashMap.put("lazylogin",new Discover("lazylogin","http://lazylogin.trafalgar.ws/"));

        when(concurrentHashMap.keySet()).thenReturn(testHashMap.keySet());
        //when(concurrentHashMap.put("lazylogin",new Discover("lazylogin","http://lazylogin.trafalgar.ws/"))).thenReturn(new Discover("lazylogin","http://lazylogin.trafalgar.ws/"));

        mockMvc.perform(post("/discover/")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"key\":\"lazylogin\", \"value\":\"http://lazylogin.trafalgar.ws/\"}"))
                .andExpect(status().isConflict());
    }

    @Test
    public void testUpdateServiceExists() throws Exception {
        when(concurrentHashMap.get("lazylogin")).thenReturn(new Discover("lazylogin","http://lazylogin.trafalgar.ws/"));
        //when(concurrentHashMap.put("lazylogin",new Discover("lazylogin","http://lazylogin.trafalgar.ws/"))).thenReturn(new Discover("lazylogin","http://lazylogin.trafalgar.ws/"));

        mockMvc.perform(put("/discover/lazylogin")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"key\":\"lazylogin\", \"value\":\"http://lazylogin2.trafalgar.ws/\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateServiceDoNotExist() throws Exception {
        when(concurrentHashMap.get("lazylogin")).thenReturn(new Discover("lazylogin","http://lazylogin.trafalgar.ws/"));
        //when(concurrentHashMap.put("lazylogin",new Discover("lazylogin","http://lazylogin.trafalgar.ws/"))).thenReturn(new Discover("lazylogin","http://lazylogin.trafalgar.ws/"));

        mockMvc.perform(put("/discover/lazylogin2")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"key\":\"lazylogin2\", \"value\":\"http://lazylogin2.trafalgar.ws/\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testUpdateServiceWithConflict() throws Exception {
        when(concurrentHashMap.get("lazylogin")).thenReturn(new Discover("lazylogin","http://lazylogin.trafalgar.ws/"));
        //when(concurrentHashMap.put("lazylogin",new Discover("lazylogin","http://lazylogin.trafalgar.ws/"))).thenReturn(new Discover("lazylogin","http://lazylogin.trafalgar.ws/"));

        mockMvc.perform(put("/discover/lazylogin")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"key\":\"lazylogin2\", \"value\":\"http://lazylogin.trafalgar.ws/\"}"))
                .andExpect(status().isConflict());
    }

    @Test
    public void testDeleteServiceExists() throws Exception {
        when(concurrentHashMap.get("lazylogin")).thenReturn(new Discover("lazylogin","http://lazylogin.trafalgar.ws/"));
        //when(concurrentHashMap.put("lazylogin",new Discover("lazylogin","http://lazylogin.trafalgar.ws/"))).thenReturn(new Discover("lazylogin","http://lazylogin.trafalgar.ws/"));

        mockMvc.perform(delete("/discover/lazylogin")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"key\":\"lazylogin\", \"value\":\"http://lazylogin.trafalgar.ws/\"}"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteServiceDoNotExist() throws Exception {
        when(concurrentHashMap.get("lazylogin")).thenReturn(new Discover("lazylogin","http://lazylogin.trafalgar.ws/"));
        //when(concurrentHashMap.put("lazylogin",new Discover("lazylogin","http://lazylogin.trafalgar.ws/"))).thenReturn(new Discover("lazylogin","http://lazylogin.trafalgar.ws/"));

        mockMvc.perform(delete("/discover/lazylogin2")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"key\":\"lazylogin2\", \"value\":\"http://lazylogin2.trafalgar.ws/\"}"))
                .andExpect(status().isNotFound());

    }
}