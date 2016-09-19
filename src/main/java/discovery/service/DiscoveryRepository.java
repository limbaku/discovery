package discovery.service;


import discovery.domain.Discover;
import org.springframework.data.repository.CrudRepository;

public interface DiscoveryRepository extends CrudRepository<Discover, String> {
}
