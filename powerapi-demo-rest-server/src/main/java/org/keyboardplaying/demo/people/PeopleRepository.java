package org.keyboardplaying.demo.people;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/**
 * The repository interface used to store and retrieve the test data.
 *
 * @author Cyrille Chopelet
 */
@RepositoryRestResource(collectionResourceRel = "people", path = "people")
public interface PeopleRepository extends MongoRepository<Person, String> {
}
