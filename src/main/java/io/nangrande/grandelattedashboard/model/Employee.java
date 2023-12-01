package io.nangrande.grandelattedashboard.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class Employee {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @Size(min = 1)
    private String lastName;
    @NotNull
    @Size(min = 2)
    private String firstName;
    private String middleName;
    @NotNull
    @Enumerated(EnumType.STRING)
    private EmployeeGender gender;
    @NotNull
    private LocalDate dob;
    @NotNull
    private String email;
    @NotNull
    @Size(min = 4)
    private String password;
    @NotNull
    private String phoneNumber;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) && Objects.equals(lastName, employee.lastName) && Objects.equals(firstName, employee.firstName) && Objects.equals(middleName, employee.middleName) && gender == employee.gender && Objects.equals(dob, employee.dob) && Objects.equals(email, employee.email) && Objects.equals(password, employee.password) && Objects.equals(phoneNumber, employee.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, lastName, firstName, middleName, gender, dob, email, password, phoneNumber);
    }
}
