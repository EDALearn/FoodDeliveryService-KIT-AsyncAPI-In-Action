package io.zenwave360.example.orders.infrastructure.mongodb;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
@org.springframework.transaction.annotation.Transactional
public abstract class BaseRepositoryIntegrationTest {

}
