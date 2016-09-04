package discovery.service;


import discovery.model.Discover;
import org.springframework.data.repository.CrudRepository;

public interface DiscoveryRepository extends CrudRepository<Discover, Integer> {
}
