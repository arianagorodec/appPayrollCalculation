import com.gorodeckaya.classes.Employee;
import com.gorodeckaya.classes.Payroll;
import com.gorodeckaya.classes.Post;
import com.gorodeckaya.classes.Total;
import org.junit.*;

import static org.junit.Assert.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestTotalSum {


    List<Object> employeeList;

    @BeforeClass
    public static void setNull(){
        System.out.println("Testing TotalSum");
    }

    @Before
    public void setEmployee(){
        employeeList = new ArrayList<>();
        Employee employee = new Employee("Gorodeckaya",
                "Ariana",
                "Mikhailovna",
                1,
                " ",
                " ",
                Date.valueOf("1999-12-29").toLocalDate(),
                1,
                1,
                "123456",
                " ");
        Post post = new Post();
        post.setName("default");
        post.setDepartment("default");
        post.setSalary(200.0);
        post.setCode(1);
        employee.setPost(post);
        Payroll payroll = new Payroll();
        payroll.setPersonalNumber(employee.getId());
        payroll.setMonth(12);
        payroll.setYear(2019);
        payroll.setWorkingDays(24);
        payroll.setWasWorkedDays(24);
        payroll.setPersonalNumber(employee.getId());
        Map<String,Double> chargesCurrent = new HashMap<>();
        Map<String,Double> deducationsCurrent = new HashMap<>();
        chargesCurrent.put("2",300.0);
        chargesCurrent.put("3",200.0);
        deducationsCurrent.put("1", 30.0);
        payroll.setCharges(chargesCurrent);
        payroll.setDeducations(deducationsCurrent);
        ArrayList <Payroll> payrolls = new ArrayList<>();
        payrolls.add(payroll);
        employee.setPayrollList(payrolls);

        payrolls = new ArrayList<>();

        Employee employee1 = new Employee("2",
                "2",
                "2",
                2,
                " ",
                " ",
                Date.valueOf("1999-12-29").toLocalDate(),
                1,
                1,
                "123987",
                " ");
        Post post1 = new Post();
        post1.setName("default");
        post1.setDepartment("default");
        post1.setSalary(300.0);
        post1.setCode(2);

        employee1.setPost(post);
        Payroll payroll1 = new Payroll();
        payroll1.setPersonalNumber(employee.getId());
        payroll1.setMonth(12);
        payroll1.setYear(2019);
        payroll1.setWorkingDays(24);
        payroll1.setWasWorkedDays(24);
        payroll1.setPersonalNumber(employee.getId());
        Map<String,Double> chargesCurrent1 = new HashMap<>();
        Map<String,Double> deducationsCurrent1 = new HashMap<>();
        chargesCurrent1.put("2",100.0);
        chargesCurrent1.put("3",300.0);
        deducationsCurrent1.put("1", 30.0);
        payroll1.setCharges(chargesCurrent1);
        payroll1.setDeducations(deducationsCurrent1);

        payrolls.add(payroll1);
        employee1.setPayrollList(payrolls);


        employeeList.add(employee);
        employeeList.add(employee1);
    }


    @Ignore
    public void Empty() {
        assertTrue(employeeList.isEmpty());
        System.out.println("@Test: EmptyArrayList");

    }


    @Test
    public void EmptyCollection() {
        assertFalse(employeeList.isEmpty());
        System.out.println("@Test: NotEmptyArrayList");

    }

    @Test(timeout = 100)
    public void testCalculationTotalSum() throws SQLException {
        Total total = new Total();
        total.setMonth(12);
        total.setYear(2019);

        total.calculationTotalSum(employeeList);

        Total ex = new Total();
        ex.setSalaryAccruals(500.0);
        ex.setOtherCharges(400.0);
        ex.setAllCharges(900.0);
        ex.setAllDeducation(60.0);
        ex.setDeducation(60.0);
        ex.setTotal(840.0);

        //assertFalse(ex.equals(total));
        assertTrue(ex.getAllCharges().toString().equals(total.getAllCharges().toString()));
        assertTrue(ex.getOtherCharges().toString().equals(total.getOtherCharges().toString()));
        assertTrue(ex.getAllDeducation().toString().equals(total.getAllDeducation().toString()));
        assertTrue(ex.getDeducation().toString().equals(total.getDeducation().toString()));
        assertTrue(ex.getSalaryAccruals().toString().equals(total.getSalaryAccruals().toString()));
        assertTrue(ex.getTotal().toString().equals(total.getTotal().toString()));
        System.out.println("@Test: Right Calculation");
    }

}
