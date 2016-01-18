package discovery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@RestController
public class DiscoveryController {

        @Autowired
        ConcurrentHashMap<String,String> concurrentHashMap;

        @RequestMapping(path = "/discover/", method = RequestMethod.GET)
        public ResponseEntity<Map<String,String>> listAll() {

            return new ResponseEntity<Map<String,String>>(concurrentHashMap, HttpStatus.OK);
        }

        @RequestMapping(path = "/discover/{key}", method = RequestMethod.GET)
        public String discoverer(@PathVariable String key) {

            String value=null;

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

