package discovery.controller;

import discovery.model.Discover;
import discovery.service.DiscoveryService;
import org.apache.commons.beanutils.BeanToPropertyValueTransformer;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.SystemEnvironmentPropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
public class DiscoveryController {

        private static final Logger logger = LoggerFactory.getLogger(DiscoveryController.class);

        @Autowired
        DiscoveryService persistence;

        public static Collection collect(Collection collection, String propertyName) {
                return CollectionUtils.collect(collection, new BeanToPropertyValueTransformer(propertyName));
        }

        @RequestMapping(path = "/discover/", method = RequestMethod.GET)
        public ResponseEntity<Collection<Discover>> listAllServices() {

                Collection<Discover> discovers = persistence.getAllservices();

                if (discovers.isEmpty()) {
                        logger.info("ListallServices method - No services set up");
                        return new ResponseEntity<Collection<Discover>>(HttpStatus.NO_CONTENT);
                }

                logger.info("ListallServices method - List of keys available: " + collect(discovers,"key"));
                return new ResponseEntity<Collection<Discover>>(discovers, HttpStatus.OK);
        }

        @RequestMapping(path = "/discover/{key}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<String> getService(@PathVariable String key) {
                Discover discover = persistence.getService(key);

                if (discover == null) {
                    logger.info("GetService method - No services found for key " + key);
                    return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
                }

                logger.info("GetService method - Service available in url " + discover.getValue());
                return new ResponseEntity<String>(discover.getValue(),HttpStatus.OK);

        }

        @RequestMapping(value = "/discover/", method = RequestMethod.POST)
        public ResponseEntity<Void> createService(@RequestBody Discover discover) {

                if (persistence.serviceExist(discover.getKey())) {
                        logger.info("CreateService method - There is already a service with key " + discover.getKey());
                        return new ResponseEntity<Void>(HttpStatus.CONFLICT);
                }

                persistence.createService(discover);
                logger.info("CreateService method - New service created with key " + discover.getKey() + " and url " + discover.getValue());
                return new ResponseEntity<Void>(HttpStatus.OK);
        }

        @RequestMapping(path = "/discover/{key}", method = RequestMethod.PUT)
        public ResponseEntity<Void> updateService(@PathVariable String key,@RequestBody Discover discover) {

                Discover discoverService = persistence.getService(key);

                if (discoverService == null) {
                        logger.info("UpdateService method - No services found for key " + key);
                        return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
                }

                if (!key.equals(discover.getKey())){
                        logger.info("UpdateService method - Conflict in keys " + discover.getKey() + " and " + discoverService.getKey());
                        return new ResponseEntity<Void>(HttpStatus.CONFLICT);
                }

                discoverService.setValue(discover.getValue());
                logger.info("UpdateService method - Updated service " + key + ". New url will be " + discoverService.getValue());
                persistence.updateService(discoverService);
                return new ResponseEntity<Void>(HttpStatus.OK);
        }

        @RequestMapping(path = "/discover/{key}", method = RequestMethod.DELETE)
        public ResponseEntity<Void> deleteService(@PathVariable String key) {

                Discover discoverService = persistence.getService(key);

                if (discoverService == null) {
                        logger.info("DeleteService method - No services found for key " + key);
                        return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
                }

                persistence.deleteService(key);
                logger.info("DeleteService method - Deleted service with key " + key);
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }

}

