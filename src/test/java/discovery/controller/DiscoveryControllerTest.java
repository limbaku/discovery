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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import org.springframework.web.context.WebApplicationContext;

import java.util.concurrent.ConcurrentHashMap;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;



public class DiscoveryControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private DiscoveryController discoveryController;

    @Mock
    private Discover discover;

    @Mock
    private ConcurrentHashMap<String, Discover> concurrentHashMap;



    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.standaloneSetup(discoveryController).build();
    }


    @Test
    public void validAccessControlRequest() throws Exception {
        //Bundle bundle = new Bundle();
        //bundle.setAuthorized(false);
        //Mockito.when(discoveryController.getService(any(String.class))).thenReturn(bundle);
        mockMvc.perform(get("/discover/lazylogin")).andExpect(status().isOk());
    }



    @Test
    public void testGetService() throws Exception {

        //mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();


        mockMvc.perform(get("/discover/lazylogin"))
                .andExpect(status().isOk());

    }

    public void testCreateService() throws Exception {

    }

    public void testUpdateService() throws Exception {

    }

    public void testDeleteService() throws Exception {

    }
}