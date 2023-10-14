package org.youcode.easybankjakartaee.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    protected String lastName;

    protected String firstName;

    protected LocalDate birthDate;

    protected String phone;

    protected String address;
}
