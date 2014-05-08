package fr.guronzan.mediatheque.mappingclasses;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
// @TransactionConfiguration(defaultRollback = false)
@ContextConfiguration(locations = { "/spring-test.xml" })
public class SpringTests {
	// Used for tests
}
