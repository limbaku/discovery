package discovery.configuration;


import discovery.model.Discover;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class DiscoveryConfig {

    @Bean
    public ConcurrentHashMap<String, Discover> initialiseList (){

        ConcurrentHashMap<String,Discover> concurrentHashMap = new ConcurrentHashMap<String, Discover>();

        concurrentHashMap.put("lazylogin",new Discover("lazylogin","http://lazylogin.trafalgar.ws/"));
        concurrentHashMap.put("recordserver",new Discover("recordserver","http://recordserver.trafalgar.ws/"));
        concurrentHashMap.put("gamemanager",new Discover("gamemanager","http://gamemanager.trafalgar.ws/"));
        concurrentHashMap.put("discovery",new Discover("discovery","http://discovery.trafalgar.ws/"));

        return concurrentHashMap;
    }
}
