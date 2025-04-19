package carservicecrm.models;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.time.LocalTime;

public class OperatorTest {

    @Test
    public void testOperatorFields() {
        Operator operator = new Operator();

        Long id = 1L;
        LocalTime workingTimeStart = LocalTime.of(9, 0);
        LocalTime workingTimeEnd = LocalTime.of(17, 0);

        operator.setId(id);
        operator.setWorkingTimeStart(workingTimeStart);
        operator.setWorkingTimeEnd(workingTimeEnd);

        assertEquals(id, operator.getId());
        assertEquals(workingTimeStart, operator.getWorkingTimeStart());
        assertEquals(workingTimeEnd, operator.getWorkingTimeEnd());
    }

    @Test
    public void testOperatorEmployee() {
        Operator operator = new Operator();

        Employee employee = new Employee();
        operator.setEmployee(employee);

        assertEquals(employee, operator.getEmployee());
    }

    @Test
    public void testWorkingTimes() {
        Operator operator = new Operator();

        LocalTime start = LocalTime.of(8, 30);
        LocalTime end = LocalTime.of(17, 30);

        operator.setWorkingTimeStart(start);
        operator.setWorkingTimeEnd(end);

        assertEquals(start, operator.getWorkingTimeStart());
        assertEquals(end, operator.getWorkingTimeEnd());
    }
}