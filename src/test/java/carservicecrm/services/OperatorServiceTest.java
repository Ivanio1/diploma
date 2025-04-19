package carservicecrm.services;

import carservicecrm.models.Operator;
import carservicecrm.repositories.OperatorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OperatorServiceTest {

    @Mock
    private OperatorRepository operatorRepository;

    @InjectMocks
    private OperatorService operatorService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSaveOperator_Success() {
        Operator operator = new Operator();

        boolean result = operatorService.saveOperator(operator);

        assertTrue(result);
        verify(operatorRepository, times(1)).save(operator);
    }

    @Test
    void testSaveOperator_Failure() {
        Operator operator = new Operator();

        doThrow(new RuntimeException("Database error")).when(operatorRepository).save(any(Operator.class));

        boolean result = operatorService.saveOperator(operator);

        assertFalse(result);
        verify(operatorRepository, times(1)).save(operator);
    }

    @Test
    void testList() {
        Operator operator1 = new Operator();
        Operator operator2 = new Operator();
        List<Operator> expectedOperators = Arrays.asList(operator1, operator2);

        when(operatorRepository.findAllOperators()).thenReturn(expectedOperators);

        List<Operator> actualOperators = operatorService.list();

        assertEquals(expectedOperators.size(), actualOperators.size());
        assertTrue(actualOperators.containsAll(expectedOperators));
        verify(operatorRepository, times(1)).findAllOperators();
    }
}