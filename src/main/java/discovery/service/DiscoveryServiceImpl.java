package discovery.service;

import discovery.domain.Discover;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DiscoveryServiceImpl implements DiscoveryService {

    @Autowired
    ConcurrentHashMap<String, Discover> concurrentHashMap;

    @Autowired
    DiscoveryRepository discoveryRepository;

    @Override
    public Collection<Discover> getAllservices() {

        return convertIterableToCollection(discoveryRepository.findAll());
    }

    @Override
    public Discover getService(String key) {
        Discover discover = discoveryRepository.findOne(key);

        return discover;
    }

    @Override
    public void saveService(Discover discover) {
        discoveryRepository.save(discover);
    }

    @Override
    public void deleteService(String key) { discoveryRepository.delete(key);
    }

    @Override
    public boolean serviceExist(String key) { return discoveryRepository.exists(key);
    }

    public Collection convertIterableToCollection (Iterable iterable){
        Collection collection = new ArrayList();

        iterable.forEach(collection::add);
        return collection;

    }
}
