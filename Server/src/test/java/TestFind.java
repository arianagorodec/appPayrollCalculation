import com.gorodeckaya.classes.Employee;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.gorodeckaya.Server.findClass;
import static org.junit.Assert.*;

public class TestFind {

    @Test
    public void TestFindClass() {

        Object object = null;

        Employee employee = new Employee();
        employee.setType("fghj");
        object = (Object) employee;

        List<Object> list = new ArrayList<>();
        list = findClass(object);

        assertNotNull(list);

        employee = new Employee();
        employee.setType("checkLogin");
        employee.setLogin("ariana");
        object = (Object) employee;
        list = findClass(object);

        assertEquals(list.get(0), true);

    }
}
