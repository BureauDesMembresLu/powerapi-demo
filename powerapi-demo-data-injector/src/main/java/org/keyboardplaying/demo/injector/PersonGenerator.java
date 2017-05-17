package org.keyboardplaying.demo.injector;

import org.keyboardplaying.demo.people.Person;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.*;

/**
 * @author Cyrille Chopelet
 */
@Component
public class PersonGenerator {

    private static final int CURRENT_YEAR = 2017;
    private static final int MAX_AGE = 100;

    private final Random random = new Random();

    private List<String> maleFirstNames;
    private List<String> femaleFirstNames;
    private List<String> lastNames;

    @PostConstruct
    public void loadNames() throws IOException, URISyntaxException {
        maleFirstNames = loadFile("male_firstnames.txt");
        femaleFirstNames = loadFile("female_firstnames.txt");
        lastNames = loadFile("lastnames.txt");
    }

    private List<String> loadFile(String fileName) throws IOException, URISyntaxException {
        List<String> result = new ArrayList<>();

        Scanner scanner = new Scanner(new File(this.getClass().getResource("/" + fileName).toURI()));
        while (scanner.hasNext()) {
            result.add(scanner.next());
        }

        return result;
    }

    public Person randomPerson() {
        Person.Gender gender = randomGender();
        String firstName = randomName(gender == Person.Gender.FEMALE ? femaleFirstNames : maleFirstNames);
        String lastName = randomName(lastNames);
        return new Person(UUID.randomUUID().toString(), firstName, lastName, gender, randomBirthDate());
    }

    private String randomName(List<String> list) {
        return list.get(random.nextInt(list.size()));
    }

    private Person.Gender randomGender() {
        return random.nextBoolean() ? Person.Gender.FEMALE : Person.Gender.MALE;
    }

    private LocalDate randomBirthDate() {
        int year = CURRENT_YEAR - random.nextInt(MAX_AGE);
        int month = random.nextInt(Month.DECEMBER.getValue() - 1) + 1;
        int day = random.nextInt(Month.of(month).length(Year.isLeap(year)) - 1) + 1;
        return LocalDate.of(year, month, day);
    }
}
