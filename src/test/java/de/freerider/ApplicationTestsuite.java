package de.freerider;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;



@RunWith(JUnitPlatform.class)
@SelectClasses( {
		de.freerider.repository.CustomerRepositoryTest.class,
		de.freerider.repository.CustomerRepositoryTest_EmptyRepositoryTestCase.class,
		de.freerider.repository.CustomerRepositoryTest_NullArgumentTestCase.class,
		de.freerider.repository.CustomerRepositoryTest_SaveTestCase.class,
		de.freerider.datamodel.CustomerTest.class
})
class ApplicationTestsuite {

}
