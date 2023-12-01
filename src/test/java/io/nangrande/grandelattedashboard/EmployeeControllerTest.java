package io.nangrande.grandelattedashboard;

import io.nangrande.grandelattedashboard.model.Employee;
import io.nangrande.grandelattedashboard.repository.EmployeeRepository;
import io.nangrande.grandelattedashboard.shared.GenericResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static io.nangrande.grandelattedashboard.model.EmployeeGender.WOMAN;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeControllerTest {
    public static final String API_EMPLOYEES = "/api/v1/employees";

    @Autowired
    TestRestTemplate testRestTemplate;

    @Autowired
    EmployeeRepository employeeRepository;

    @BeforeEach
    public void cleanup() {
        employeeRepository.deleteAll();
    }

    private Employee createValidEmployee() {
        Employee employee = new Employee();
        employee.setLastName("Добкина Валерия ");
        employee.setFirstName("Валерия");
        employee.setMiddleName("Петровна");
        employee.setGender(WOMAN);
        employee.setDob(LocalDate.of(1997, Month.MAY, 16));
        employee.setEmail("dobkinavalya@yandex.ru");
        employee.setPassword("dobkinavalya1997");
        employee.setPhoneNumber("+79821234567");
        return employee;
    }

    @Test
    public void postEmployee_whenEmployeeIsValid_receiveOk() {
        Employee employee = createValidEmployee();
        ResponseEntity<Object> response = postSignup(employee, Object.class);
        assertEquals(HttpStatus.OK, response.getStatusCode(), "Статус ответа не соответствует ожидаемому");
    }

    @Test
    public void postEmployee_whenEmployeeIsValid_employeeSavedToDB() {
        Employee employee = createValidEmployee();
        postSignup(employee, Object.class);
        assertEquals(1, employeeRepository.count(), "Количество пользователей не соответствует ожидаемому");
    }

    @Test
    public void postEmployee_whenUserIsValid_receiveSuccessMessage() {
        Employee employee = createValidEmployee();
        ResponseEntity<GenericResponse> response = postSignup(employee, GenericResponse.class);
        assertNotNull(Objects.requireNonNull(response.getBody()).getMessage(),
                "Сообщение не получено");
    }

    @Test
    public void postEmployee_whenUserIsValid_passwordInDBIsHashed() {
        Employee employee = createValidEmployee();
        postSignup(employee, Object.class);
        List<Employee> employees = employeeRepository.findAll();
        Employee inDB = employees.get(0);
        assertNotEquals(employee.getPassword(), inDB.getPassword(), "Пароль не зашифрован");
    }

    @Test
    public void postEmployee_whenUserHasNullLastName_receiveBadRequest() {
        Employee employee = createValidEmployee();
        employee.setLastName(null);
        ResponseEntity<Object> response = postSignup(employee, Object.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Не введена фамилия");
    }

    @Test
    public void postEmployee_whenUserHasNullFirstName_receiveBadRequest() {
        Employee employee = createValidEmployee();
        employee.setFirstName(null);
        ResponseEntity<Object> response = postSignup(employee, Object.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Не введено имя");
    }

    @Test
    public void postEmployee_whenUserHasNullGender_receiveBadRequest() {
        Employee employee = createValidEmployee();
        employee.setGender(null);
        ResponseEntity<Object> response = postSignup(employee, Object.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Не введен пол");
    }

    @Test
    public void postEmployee_whenUserHasNullDob_receiveBadRequest() {
        Employee employee = createValidEmployee();
        employee.setDob(null);
        ResponseEntity<Object> response = postSignup(employee, Object.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Не введена дата рождения");
    }

    @Test
    public void postEmployee_whenUserHasNullEmail_receiveBadRequest() {
        Employee employee = createValidEmployee();
        employee.setEmail(null);
        ResponseEntity<Object> response = postSignup(employee, Object.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Не введен email");
    }

    @Test
    public void postEmployee_whenUserHasNullPassword_receiveBadRequest() {
        Employee employee = createValidEmployee();
        employee.setPassword(null);
        ResponseEntity<Object> response = postSignup(employee, Object.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Не введен пароль");
    }

    @Test
    public void postEmployee_whenUserHasNullPhoneNumber_receiveBadRequest() {
        Employee employee = createValidEmployee();
        employee.setPhoneNumber(null);
        ResponseEntity<Object> response = postSignup(employee, Object.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Не введен номер телефона");
    }

    @Test
    public void postEmployee_whenUserHasLastNameWithLessThanRequired_receiveBadRequest() {
        Employee employee = createValidEmployee();
        employee.setLastName("Иванов");
        ResponseEntity<Object> response = postSignup(employee, Object.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Не введена фамилия");
    }

    @Test
    public void postEmployee_whenUserHasFirstNameLessThatRequired_receiveBadRequest() {
        Employee employee = createValidEmployee();
        employee.setFirstName("Иван");
        ResponseEntity<Object> response = postSignup(employee, Object.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Не введено имя");
    }

    @Test
    public void postEmployee_whenUserHasPasswordLessThanRequired_receiveBadRequest() {
        Employee employee = createValidEmployee();
        employee.setPassword("P4sswd");
        ResponseEntity<Object> response = postSignup(employee, Object.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode(), "Не введен пароль");
    }

    public <T> ResponseEntity<T> postSignup(Object request, Class<T> response) {
        return testRestTemplate.postForEntity(API_EMPLOYEES, request, response);
    }
}
