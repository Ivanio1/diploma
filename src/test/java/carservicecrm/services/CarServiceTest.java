package carservicecrm.services;

import carservicecrm.models.Car;
import carservicecrm.repositories.CarRepository;
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

class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testList() {
        Car car1 = new Car();
        Car car2 = new Car();
        List<Car> expectedCars = Arrays.asList(car1, car2);

        when(carRepository.findAllCars()).thenReturn(expectedCars);

        List<Car> actualCars = carService.list();

        assertEquals(expectedCars.size(), actualCars.size());
        assertTrue(actualCars.containsAll(expectedCars));
        verify(carRepository, times(1)).findAllCars();
    }

    @Test
    void testSaveCar_Success() {
        Car car = new Car();

        boolean result = carService.saveCar(car);

        assertTrue(result);
        verify(carRepository, times(1)).save(car);
    }

    @Test
    void testSaveCar_Failure() {
        Car car = new Car();

        doThrow(new RuntimeException("Database error")).when(carRepository).save(any(Car.class));

        boolean result = carService.saveCar(car);

        assertFalse(result);
        verify(carRepository, times(1)).save(car);
    }

    @Test
    void testGetCar() {
        Long carId = 1L;
        Car expectedCar = new Car();

        when(carRepository.findCarById(carId)).thenReturn(expectedCar);

        Car actualCar = carService.getCar(carId);

        assertEquals(expectedCar, actualCar);
        verify(carRepository, times(1)).findCarById(carId);
    }
}