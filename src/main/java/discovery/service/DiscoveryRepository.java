package discovery.service;


import discovery.domain.Discovery;
import org.springframework.data.repository.CrudRepository;

public interface DiscoveryRepository extends CrudRepository<Discovery, String> {
}
