package discovery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ConcurrentHashMap;


@RestController
public class DiscoveryController {

        @Autowired
        DiscoveryConfig discoveryConfig;

        @RequestMapping("/discover/{key}")
        public String discoverer(@PathVariable String key) {

            String value=null;

            ConcurrentHashMap<String,String> concurrentHashMap = discoveryConfig.initialiseHashMap();

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

