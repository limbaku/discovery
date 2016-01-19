package discovery;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class DiscoveryConfig {

    @Bean
    public List<Discover> initialiseList (){

        List<Discover> concurrentList = new ArrayList<Discover>();

        concurrentList.add(new Discover("lazylogin","http://lazylogin.trafalgar.ws/"));
        concurrentList.add(new Discover("recordserver","http://recordserver.trafalgar.ws/"));
        concurrentList.add(new Discover("gamemanager","http://gamemanager.trafalgar.ws/"));
        concurrentList.add(new Discover("discovery","http://discovery.trafalgar.ws/"));

        return concurrentList;
    }

}
