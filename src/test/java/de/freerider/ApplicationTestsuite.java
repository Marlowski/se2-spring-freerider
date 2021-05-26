package de.freerider;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;



@RunWith(JUnitPlatform.class)
@SelectClasses( {
	de.freerider.SampleTests.class,
	de.freerider.model.CustomerTest.class,
	de.freerider.repository.CustomerRepositoryTest.class
})
class ApplicationTestsuite {

}
