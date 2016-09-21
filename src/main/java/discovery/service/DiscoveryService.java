package discovery.service;


import discovery.domain.Discovery;

import java.util.Collection;

public interface DiscoveryService {

    Collection<Discovery> getAllservices();
    Discovery getService(String key);
    void saveService (Discovery discovery);
    void deleteService (String key);
    boolean serviceExist (String key);




}
