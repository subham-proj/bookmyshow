package org.bankingsystem.userservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * Basic sanity test that the Spring context starts.
 *
 * NOTE: this boots the full context, so it needs the PostgreSQL connection in
 * application.yml to be reachable. Once you fill in the DB credentials this will
 * pass; until then, skip tests when building (see the run instructions).
 */
@SpringBootTest
class UserServiceApplicationTests {

    @Test
    void contextLoads() {
    }
}
