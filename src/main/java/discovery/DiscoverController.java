package discovery;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentHashMap;


@RestController
public class DiscoverController {

        @RequestMapping("/discover")
        public String discoverer(@RequestParam(value="key") String key) {

            String value=null;
            Discover discover = new Discover();

            ConcurrentHashMap<String,String> concurrentHashMap = discover.initialiseHashMap();

            for (String keyValue : concurrentHashMap.keySet()){
                if (concurrentHashMap.get(key) == null){
                    return "Could not find a value for key provided";
                }
                else{
                    return concurrentHashMap.get(key);
                }
            }

            return value;
        }
}

