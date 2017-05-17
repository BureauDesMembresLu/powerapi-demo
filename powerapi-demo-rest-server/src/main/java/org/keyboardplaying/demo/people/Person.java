package org.keyboardplaying.demo.people;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

/**
 * A person.
 *
 * @author Cyrille Chopelet
 */
@Document
public class Person {

    public enum Gender {
        MALE, FEMALE
    }

    @Id
    private final String uuid;
    private final String firstName;
    private final String lastName;
    private final Gender gender;
    private final LocalDate birthDate;

    public Person(String uuid, String firstName, String lastName, Gender gender, LocalDate birthDate) {
        this.uuid = uuid;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.birthDate = birthDate;
    }

    public String getUuid() {
        return uuid;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Gender getGender() {
        return gender;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    @Override
    public String toString() {
        return "Person{" +
                "uuid='" + uuid + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender +
                ", birthDate=" + birthDate +
                '}';
    }
}
