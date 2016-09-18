package discovery.service;


import discovery.domain.Discover;

import java.util.Collection;

public interface DiscoveryService {

    Collection<Discover> getAllservices();
    Discover getService(String key);
    void createService (Discover discover);
    void updateService (Discover discover);
    void deleteService (String key);
    boolean serviceExist (String key);




}
