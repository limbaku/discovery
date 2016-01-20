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
        public ResponseEntity<Collection<Discover>> listAll() {

                return new ResponseEntity<Collection<Discover>>(concurrentHashMap.values(), HttpStatus.OK);
        }

        @RequestMapping(path = "/discover/{key}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<String> discoverer(@PathVariable String key) {
                Discover discover = concurrentHashMap.get(key);

                if (discover == null) {

                    return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<String>(discover.getValue(),HttpStatus.OK);

        }

        @RequestMapping(value = "/discover/", method = RequestMethod.POST)
        public ResponseEntity<Void> createService(@RequestBody Discover discover) {


                concurrentHashMap.putIfAbsent(discover.getKey(),discover);
                return new ResponseEntity<Void>(HttpStatus.OK);


        }


}

