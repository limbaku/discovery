package discovery.service;

import discovery.domain.Discovery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

@Service
public class DiscoveryServiceImpl implements DiscoveryService {

    @Autowired
    DiscoveryRepository discoveryRepository;

    @Override
    public Collection<Discovery> getAllservices() {

        return convertIterableToCollection(discoveryRepository.findAll());
    }

    @Override
    public Discovery getService(String key) {
        Discovery discovery = discoveryRepository.findOne(key);

        return discovery;
    }

    @Override
    public void saveService(Discovery discovery) {
        discoveryRepository.save(discovery);
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
