package io.zenwave360.example.delivery.core.outbound.mongodb;

import io.zenwave360.example.delivery.core.domain.Delivery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/** Spring Data MongoDB repository for the Delivery entity. */
@SuppressWarnings("unused")
@Repository
public interface DeliveryRepository extends MongoRepository<Delivery, String> {

}
