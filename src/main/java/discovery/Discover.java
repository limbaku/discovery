package discovery;


import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class Discover {


    public ConcurrentHashMap<String, String> initialiseHashMap (){

        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap<String, String>(4,0.9f,1);

        concurrentHashMap.putIfAbsent("lazylogin","http://lazylogin.trafalgar.ws/");
        concurrentHashMap.putIfAbsent("recordserver","http://recordserver.trafalgar.ws/");
        concurrentHashMap.putIfAbsent("gamemanager","http://gamemanager.trafalgar.ws/");
        concurrentHashMap.putIfAbsent("discovery","http://discovery.trafalgar.ws/");

        return concurrentHashMap;
    }

}
