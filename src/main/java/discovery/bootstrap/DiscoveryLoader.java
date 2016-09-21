package discovery.bootstrap;


import discovery.controller.DiscoveryController;
import discovery.domain.Discovery;
import discovery.service.DiscoveryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class DiscoveryLoader implements ApplicationListener<ContextRefreshedEvent>{

    private DiscoveryRepository discoveryRepository;

    private static final Logger logger = LoggerFactory.getLogger(DiscoveryLoader.class);

    @Autowired
    public void setProductRepository(DiscoveryRepository discoveryRepository) {
        this.discoveryRepository = discoveryRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        Discovery lazyloginService = new Discovery();
        lazyloginService.setKey("lazylogin");
        lazyloginService.setValue("http://lazylogin.trafalgar.ws/");
        discoveryRepository.save(lazyloginService);

        logger.info("Initialising services - Saved service with key " + lazyloginService.getKey());

        Discovery recordserverService = new Discovery();
        recordserverService.setKey("recordserver");
        recordserverService.setValue("http://recordserver.trafalgar.ws/");
        discoveryRepository.save(recordserverService);

        logger.info("Initialising services - Saved service with key " + recordserverService.getKey());

        Discovery gamemanagerService = new Discovery();
        gamemanagerService.setKey("gamemanager");
        gamemanagerService.setValue("http://gamemanager.trafalgar.ws/");
        discoveryRepository.save(gamemanagerService);

        logger.info("Initialising services - Saved service with key " + gamemanagerService.getKey());

        Discovery discoveryService = new Discovery();
        discoveryService.setKey("discovery");
        discoveryService.setValue("http://discovery.trafalgar.ws/");
        discoveryRepository.save(discoveryService);

        logger.info("Initialising services - Saved service with key " + discoveryService.getKey());

    }
}
