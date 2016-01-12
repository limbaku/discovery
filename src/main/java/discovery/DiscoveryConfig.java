package discovery;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class DiscoveryConfig {


    @Bean
    public ConcurrentHashMap<String, String> initialiseHashMap (){

        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap<String, String>(4,0.9f,1);

        concurrentHashMap.putIfAbsent("lazylogin","http://lazylogin.trafalgar.ws/");
        concurrentHashMap.putIfAbsent("recordserver","http://recordserver.trafalgar.ws/");
        concurrentHashMap.putIfAbsent("gamemanager","http://gamemanager.trafalgar.ws/");
        concurrentHashMap.putIfAbsent("discovery","http://discovery.trafalgar.ws/");

        return concurrentHashMap;
    }

}
