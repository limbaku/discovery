package discovery.controller;

import discovery.model.Discover;
import discovery.service.DiscoveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
public class DiscoveryController {


        @Autowired
        DiscoveryService persistence;

        @RequestMapping(path = "/discover/", method = RequestMethod.GET)
        public ResponseEntity<Collection<Discover>> listAllServices() {
                Collection<Discover> discovers = persistence.getAllservices();

                if (discovers.isEmpty()) {
                        return new ResponseEntity<Collection<Discover>>(HttpStatus.NO_CONTENT);
                }

                return new ResponseEntity<Collection<Discover>>(discovers, HttpStatus.OK);
        }


        @RequestMapping(path = "/discover/{key}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<String> getService(@PathVariable String key) {
                Discover discover = persistence.getService(key);

                if (discover == null) {
                    return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
                }
                return new ResponseEntity<String>(discover.getValue(),HttpStatus.OK);

        }


        @RequestMapping(value = "/discover/", method = RequestMethod.POST)
        public ResponseEntity<Void> createService(@RequestBody Discover discover) {

                if (persistence.serviceExist(discover.getKey())) {
                        return new ResponseEntity<Void>(HttpStatus.CONFLICT);
                }

                persistence.createService(discover);
                return new ResponseEntity<Void>(HttpStatus.OK);
        }


        @RequestMapping(path = "/discover/{key}", method = RequestMethod.PUT)
        public ResponseEntity<Void> updateService(@PathVariable String key,@RequestBody Discover discover) {

                Discover discoverService = persistence.getService(key);

                if (discoverService == null) {
                        return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
                }

                if (!key.equals(discover.getKey())){
                        return new ResponseEntity<Void>(HttpStatus.CONFLICT);
                }

                discoverService.setValue(discover.getValue());
                persistence.updateService(discoverService);
                return new ResponseEntity<Void>(HttpStatus.OK);
        }

        @RequestMapping(path = "/discover/{key}", method = RequestMethod.DELETE)
        public ResponseEntity<Void> deleteService(@PathVariable String key) {

                Discover discoverService = persistence.getService(key);

                if (discoverService == null) {
                        return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
                }

                persistence.deleteService(key);
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }

}

