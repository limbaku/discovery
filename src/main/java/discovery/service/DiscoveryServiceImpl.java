package discovery.service;

import discovery.domain.Discover;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DiscoveryServiceImpl implements DiscoveryService {

    @Autowired
    ConcurrentHashMap<String, Discover> concurrentHashMap;

    @Override
    public Collection<Discover> getAllservices() {
        return concurrentHashMap.values();
    }

    @Override
    public Discover getService(String key) {
        Discover discover = concurrentHashMap.get(key);

        if (discover == null)
            return null;
        else
            return discover;
    }

    @Override
    public void createService(Discover discover) {
        concurrentHashMap.put(discover.getKey(),discover);
    }

    @Override
    public void updateService(Discover discover) {
        concurrentHashMap.put(discover.getKey(),discover);
    }

    @Override
    public void deleteService(String key) {
        concurrentHashMap.remove(key);
    }

    @Override
    public boolean serviceExist(String key) {
        return concurrentHashMap.containsKey(key);
    }
}
