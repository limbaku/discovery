package discovery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


@RestController
public class DiscoveryController {

        @Autowired
        ConcurrentHashMap<String,Discover> concurrentHashMap;


        @RequestMapping(path = "/discover/", method = RequestMethod.GET)
        public ResponseEntity<Collection<Discover>> listAllServices() {

                return new ResponseEntity<Collection<Discover>>(concurrentHashMap.values(), HttpStatus.OK);
        }

        @RequestMapping(path = "/discover/{key}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<String> getService(@PathVariable String key) {
                Discover discover = concurrentHashMap.get(key);

                if (discover == null) {

                    return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<String>(discover.getValue(),HttpStatus.OK);

        }

        @RequestMapping(value = "/discover/", method = RequestMethod.POST)
        public ResponseEntity<Void> createService(@RequestBody Discover discover) {

                for (String keyInserted: concurrentHashMap.keySet()){
                        if (discover.getKey() == keyInserted){
                                return new ResponseEntity<Void>(HttpStatus.CONFLICT);
                        }
                }
                concurrentHashMap.put(discover.getKey(),discover);
                return new ResponseEntity<Void>(HttpStatus.OK);
        }

        @RequestMapping(path = "/discover/{key}", method = RequestMethod.PUT)
        public ResponseEntity<Void> updateService(@PathVariable String key,@RequestBody Discover discover) {

                Discover discoverService = concurrentHashMap.get(key);

                if (discoverService == null) {

                        return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
                }

                if (!key.equals(discover.getKey())){
                        return new ResponseEntity<Void>(HttpStatus.CONFLICT);
                }

                discoverService.setValue(discover.getValue());
                return new ResponseEntity<Void>(HttpStatus.OK);
        }

        @RequestMapping(path = "/discover/{key}", method = RequestMethod.DELETE)
        public ResponseEntity<Void> deleteService(@PathVariable String key){

                return void;
        }

}

