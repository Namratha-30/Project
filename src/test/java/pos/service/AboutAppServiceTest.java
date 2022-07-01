package pos.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AboutAppServiceTest extends AbstractUnitTest{
    @Autowired
    private AboutAppService service;

    //Test the about app controller
    @Test
    public void testServiceApis() {
        assertEquals("Point of sale", service.getName());
        assertEquals("1.0", service.getVersion());
    }

}