package org.youcode.easybankjakartaee.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client extends Person{
    private int code;


    public Client(String lastName, String firstName, LocalDate birthDate, String phone, String address, int code) {
        super(lastName, firstName, birthDate, phone, address);
        this.code = code;
    }

    public Client(String lastName, String firstName, LocalDate birthDate, String phone, String address) {
        super(lastName, firstName, birthDate, phone, address);
    }

}
