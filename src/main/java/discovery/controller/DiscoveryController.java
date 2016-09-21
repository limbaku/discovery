package discovery.controller;

import discovery.domain.Discovery;
import discovery.service.DiscoveryService;
import org.apache.commons.beanutils.BeanToPropertyValueTransformer;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
public class DiscoveryController {

        private static final Logger logger = LoggerFactory.getLogger(DiscoveryController.class);

        @Autowired
        DiscoveryService discoveryService;

        private static Collection collect(Collection collection, String propertyName) {
                return CollectionUtils.collect(collection, new BeanToPropertyValueTransformer(propertyName));
        }

        @RequestMapping(path = "/discover/", method = RequestMethod.GET)
        public ResponseEntity<Collection<Discovery>> listAllServices() {

                Collection<Discovery> discoverys = discoveryService.getAllservices();

                if (discoverys.isEmpty()) {
                        logger.info("ListallServices method - No services set up");
                        return new ResponseEntity<Collection<Discovery>>(HttpStatus.NO_CONTENT);
                }

                logger.info("ListallServices method - List of keys available: " + collect(discoverys,"key"));
                return new ResponseEntity<Collection<Discovery>>(discoverys, HttpStatus.OK);
        }

        @RequestMapping(path = "/discover/{key}", method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<String> getService(@PathVariable String key) {
                Discovery discovery = discoveryService.getService(key);

                if (discovery == null) {
                    logger.info("GetService method - No services found for key " + key);
                    return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
                }

                logger.info("GetService method - Service available in url " + discovery.getValue());
                return new ResponseEntity<String>(discovery.getValue(),HttpStatus.OK);

        }

        @RequestMapping(value = "/discover/", method = RequestMethod.POST)
        public ResponseEntity<Void> createService(@RequestBody Discovery discovery) {

                if (discoveryService.serviceExist(discovery.getKey())) {
                        logger.info("CreateService method - There is already a service with key " + discovery.getKey());
                        return new ResponseEntity<Void>(HttpStatus.CONFLICT);
                }

                discoveryService.saveService(discovery);
                logger.info("CreateService method - New service created with key " + discovery.getKey() + " and url " + discovery.getValue());
                return new ResponseEntity<Void>(HttpStatus.OK);
        }

        @RequestMapping(path = "/discover/{key}", method = RequestMethod.PUT)
        public ResponseEntity<Void> updateService(@PathVariable String key,@RequestBody Discovery discovery) {

                Discovery discoveryOwnService = discoveryService.getService(key);

                if (discoveryOwnService == null) {
                        logger.info("UpdateService method - No services found for key " + key);
                        return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
                }

                if (!key.equals(discovery.getKey())){
                        logger.info("UpdateService method - Conflict in keys " + discovery.getKey() + " and " + discoveryOwnService.getKey());
                        return new ResponseEntity<Void>(HttpStatus.CONFLICT);
                }

                discoveryOwnService.setValue(discovery.getValue());
                logger.info("UpdateService method - Updated service " + key + ". New url will be " + discoveryOwnService.getValue());
                discoveryService.saveService(discoveryOwnService);
                return new ResponseEntity<Void>(HttpStatus.OK);
        }

        @RequestMapping(path = "/discover/{key}", method = RequestMethod.DELETE)
        public ResponseEntity<Void> deleteService(@PathVariable String key) {

                Discovery discoverOwnService = discoveryService.getService(key);

                if (discoverOwnService == null) {
                        logger.info("DeleteService method - No services found for key " + key);
                        return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
                }

                discoveryService.deleteService(key);
                logger.info("DeleteService method - Deleted service with key " + key);
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
        }

}

