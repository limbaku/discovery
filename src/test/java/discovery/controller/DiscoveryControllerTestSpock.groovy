package discovery.controller

import discovery.model.Discover
import org.springframework.http.HttpStatus
import spock.lang.Specification

import java.util.concurrent.ConcurrentHashMap

class DiscoveryControllerTestSpock extends Specification{

    DiscoveryController discoveryController = new DiscoveryController();
    ConcurrentHashMap<String, Discover> concurrentHashMap = Mock()

    def setup(){
        discoveryController.concurrentHashMap = concurrentHashMap;
    }

    def "Test should get existing service"(){
        given:
        def key="lazylogin";
        def value="http://lazylogin.trafalgar.ws/";
        concurrentHashMap.get("/discover/lazylogin") >> new Discover(key,value);

        when:

        def response = discoveryController.getService("/discover/lazylogin");
        then:
        response.statusCode.equals(HttpStatus.OK);
    }

    def "Test should return not found when getting service"(){
        given:
        def key="lazylogin";
        def value="http://lazylogin.trafalgar.ws/";
        concurrentHashMap.get("/discover/lazylogin") >> new Discover(key,value);

        when:

        def response = discoveryController.getService("/discover/lazylogin2");
        then:
        response.statusCode.equals(HttpStatus.NOT_FOUND);
    }

    def "Test should create new service"(){
        given:
        def key="lazylogin";
        def value="http://lazylogin.trafalgar.ws/";
        concurrentHashMap.containsKey(key) >> false;

        when:
        def response = discoveryController.createService(new Discover(key,value));

        then:
        response.statusCode.equals(HttpStatus.OK);
    }

    def "Test should return conflict when creating new service"(){
        given:
        def key="lazylogin";
        def value="http://lazylogin.trafalgar.ws/";
        concurrentHashMap.containsKey(key) >> true;

        when:
        def response = discoveryController.createService(new Discover(key,value));
        then:
        response.statusCode.equals(HttpStatus.CONFLICT);
    }

    def "Test should update service"(){
        given:
        def key="lazylogin";
        def value="http://lazylogin.trafalgar.ws/";
        def value2="http://lazylogin2.trafalgar.ws/";
        concurrentHashMap.get(key) >> new Discover(key,value);

        when:

        def response = discoveryController.updateService(key,new Discover(key,value2));
        then:
        response.statusCode.equals(HttpStatus.OK);
    }

    def "Test should return not found when updating service"(){
        given:
        def key="lazylogin";
        def key2="lazylogin2";
        def value="http://lazylogin.trafalgar.ws/";
        def value2="http://lazylogin2.trafalgar.ws/";
        concurrentHashMap.get(key) >> new Discover(key,value);

        when:

        def response = discoveryController.updateService(key2,new Discover(key,value2));
        then:
        response.statusCode.equals(HttpStatus.NOT_FOUND);
    }

    def "Test should return conflict when updating service"(){
        given:
        def key="lazylogin";
        def key2="lazylogin2";
        def value="http://lazylogin.trafalgar.ws/";
        concurrentHashMap.get(key) >> new Discover(key,value);

        when:

        def response = discoveryController.updateService(key,new Discover(key2,value));
        then:
        response.statusCode.equals(HttpStatus.CONFLICT);
    }

    def "Test should delete service"(){
        given:
        def key="lazylogin";
        def value="http://lazylogin.trafalgar.ws/";
        concurrentHashMap.get(key) >> new Discover(key,value);

        when:

        def response = discoveryController.deleteService(key);
        then:
        response.statusCode.equals(HttpStatus.NO_CONTENT);
    }

    def "Test should return not found when deleting service"(){
        given:
        def key="lazylogin";
        def key2="lazylogin2";
        def value="http://lazylogin.trafalgar.ws/";
        concurrentHashMap.get(key) >> new Discover(key,value);

        when:

        def response = discoveryController.deleteService(key2);
        then:
        response.statusCode.equals(HttpStatus.NOT_FOUND);
    }
}
