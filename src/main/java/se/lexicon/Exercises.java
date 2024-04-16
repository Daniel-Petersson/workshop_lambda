package se.lexicon;

import se.lexicon.data.DataStorage;
import se.lexicon.data.DataStorageImpl;
import se.lexicon.model.Gender;
import se.lexicon.model.Person;
import java.time.temporal.ChronoUnit;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

public class Exercises {

    private final static DataStorage storage = DataStorage.INSTANCE;

    /*
       TODO:  1.	Find everyone that has firstName: “Erik” using findMany().
    */
    public static void exercise1(String message) {
        System.out.println(message);
        // Create a Predicate that checks if a Person's first name is "Erik"
        Predicate<Person> findByFirstName = person -> "Erik".equalsIgnoreCase(person.getFirstName());
        // Call the findMany method with the findByFirstName predicate as an argument
        List<Person> personNamedErik = storage.findMany(findByFirstName);

        // Iterate over the returned list of Person objects
        for (Person person : personNamedErik) {
            // Print out the first name and last name of each Person
            System.out.println(person.getFirstName() + " " + person.getLastName());
        }
        System.out.println("----------------------");
    }

    /*
        TODO:  2.	Find all females in the collection using findMany().
     */
    public static void exercise2(String message) {
        System.out.println(message);
        // Create a Predicate<Person> that checks if a Person's gender is female.
        Predicate<Person> gender = person -> person.getGender().equals(Gender.FEMALE);
        // Call the findMany method with the gender predicate as an argument
        List<Person> females = storage.findMany(gender);

        // Iterate over the returned list of Person objects
        for (Person person : females) {
            // Print out the first name and last name of each Person
            System.out.println(person.getFirstName() + " " + person.getLastName() + " " + person.getGender());
        }

        System.out.println("----------------------");
    }

    /*
        TODO:  3.	Find all who are born after (and including) 2000-01-01 using findMany().
     */
    public static void exercise3(String message) {
        System.out.println(message);
        // Create a Predicate<Person> that checks if all who are born after (and including) 2000-01-01.
        LocalDate date2000 = LocalDate.of(2000, 1, 1);
        Predicate<Person> bornAfter2000 = person -> !person.getBirthDate().isBefore(date2000);
        // Call the findMany method with the bornAfter2000 predicate as an argument
        List<Person> personsBornAfter2000 = storage.findMany(bornAfter2000);

        // Iterate over the returned list of Person objects
        for (Person person : personsBornAfter2000) {
            // Print out the first name and last name of each Person
            System.out.println(person.getFirstName() + " " + person.getLastName());
        }

        System.out.println("----------------------");
    }

    /*
        TODO: 4.	Find the Person that has an id of 123 using findOne().
     */
    public static void exercise4(String message) {
        System.out.println(message);
        //Write your code here
        Predicate<Person> personId123 = person -> person.getId() == 123;
        Person personWithId = storage.findOne(personId123);
        System.out.println(personWithId.getFirstName() + " " + personWithId.getLastName());

        System.out.println("----------------------");

    }

    /*
        TODO:  5.	Find the Person that has an id of 456 and convert to String with following content:
            “Name: Nisse Nilsson born 1999-09-09”. Use findOneAndMapToString().
     */
    public static void exercise5(String message) {
        System.out.println(message);
        // Define a Predicate that checks if a Person's ID is 456
        // Predicate<Person> personId456 = person -> person.getId() == 456;
        Predicate<Person> personId456 = person -> person.getId() == 456;
        // Define a Function that formats a Person object into a String
        Function<Person, String> personToString = person -> "Name" + person.getFirstName() + " " + person.getLastName() + " born " + person.getBirthDate();

        // Call the findOneAndMapToString method with the Predicate and Function as arguments
        String personInfo = storage.findOneAndMapToString(personId456,personToString);

        // Print the String returned by the findOneAndMapToString method
        System.out.println(personInfo);

        System.out.println("----------------------");
    }

    /*
     TODO:  6.	Find all male people whose names start with “E” and convert each to a String using findManyAndMapEachToString().
  */
    public static void exercise6(String message) {
        System.out.println(message);
        // Define a Predicate that checks if a Person's gender is male and their name starts with "E"
        Predicate<Person> maleNameStartsWithE  = person -> person.getGender().equals(Gender.MALE)&& person.getFirstName().startsWith("E");
        // Define a Function that converts a Person object to a String
        Function<Person,String> personStringFunction = person -> person.getFirstName() + person.getLastName()+ person.getGender();
        // Call the findManyAndMapEachToString() method with the Predicate and Function as arguments
        List<String> personInfo = storage.findManyAndMapEachToString(maleNameStartsWithE,personStringFunction);
        // Print the List<String> returned by the findManyAndMapEachToString() method
        System.out.println(personInfo);
        System.out.println("----------------------");
    }

    /*
        TODO:  7.	Find all people who are below age of 10 and convert them to a String like this:
            “Olle Svensson 9 years”. Use findManyAndMapEachToString() method.
     */
    public static void exercise7(String message) {
        System.out.println(message);
        // Call the findManyAndMapEachToString() method with the Predicate and Function as arguments
        List<String> personInfo = storage.findManyAndMapEachToString(
                // Define a Predicate that checks if a Person's age is below 10
                person -> {
                    LocalDate currentDate = LocalDate.now();
                    long age = ChronoUnit.YEARS.between(person.getBirthDate(), currentDate);
                    return age < 10;
                },
                // Define a Function that converts a Person object to a String in the format "Olle Svensson 9 years"
                person -> {
                    LocalDate currentDate = LocalDate.now();
                    long age = ChronoUnit.YEARS.between(person.getBirthDate(), currentDate);
                    return person.getFirstName() + " " + person.getLastName() + " " + age + " years";
                }
        );

        // Print the List<String> returned by the findManyAndMapEachToString() method
        personInfo.forEach(System.out::println);
    }

    /*
        TODO:  8.	Using findAndDo() print out all people with firstName “Ulf”.
     */
    public static void exercise8(String message) {
        System.out.println(message);
        // Define a Predicate that checks if a Person's first name is "Ulf"
        Predicate<Person> personsWithFirstNameUlf = person -> person.getFirstName().equalsIgnoreCase("ulf");
        // Define a Consumer that prints out a Person object
        Consumer<Person> personConsumer = person -> System.out.println(person.getFirstName() + " " + person.getLastName());
        // Call the findAndDo() method with the Predicate and Consumer as arguments
        storage.findAndDo(personsWithFirstNameUlf,personConsumer);
        System.out.println("----------------------");
    }

    /*
        TODO:  9.	Using findAndDo() print out everyone who have their lastName contain their firstName.
     */
    public static void exercise9(String message) {
        System.out.println(message);
        // Define a Predicate that checks if a Person's last name contains their first name
        Predicate<Person> lastNameContainingFirstName = person -> person.getLastName().contains(person.getFirstName());
        // Define a Consumer that prints out a Person object
        Consumer<Person> personConsumer = person -> System.out.println(person.getLastName()+ " "+ person.getFirstName());
        // Call the findAndDo() method with the Predicate and Consumer as arguments
        storage.findAndDo(lastNameContainingFirstName,personConsumer);
        System.out.println("----------------------");
    }

    /*
        TODO:  10.	Using findAndDo() print out the firstName and lastName of everyone whose firstName is a palindrome.
     */
    public static void exercise10(String message) {
        System.out.println(message);
        // Define a Predicate that checks if a Person's first name is a palindrome
        Predicate<Person> namePalindrome = person -> {
            String firstName = person.getFirstName().toLowerCase();
            String reversedFirstName = new StringBuilder(firstName).reverse().toString();
            return firstName.equals(reversedFirstName);
        };
        // Define a Consumer that prints out a Person's first name and last name
        Consumer<Person> personConsumer = person -> System.out.println(person.getFirstName() + " " + person.getLastName());
        // Call the findAndDo() method with the Predicate and Consumer as arguments
        storage.findAndDo(namePalindrome,personConsumer);
        System.out.println("----------------------");
    }

    /*
        TODO:  11.	Using findAndSort() find everyone whose firstName starts with A sorted by birthdate.
     */
    public static void exercise11(String message) {
        System.out.println(message);
        // Define a Predicate that checks if a Person's first name starts with "A"
        storage.findAndSort(
                person -> person.getFirstName().startsWith("A"),
                Comparator.comparing(Person::getBirthDate)
        ).forEach(System.out::println);
        // Define a Comparator that sorts Person objects by birthdate

        // Call the findAndSort() method with the Predicate and Comparator as arguments
        // Print the List<Person> returned by the findAndSort() method
        System.out.println("----------------------");
    }

    /*
        TODO:  12.	Using findAndSort() find everyone born before 1950 sorted reversed by lastest to earliest.
     */
    public static void exercise12(String message) {
        System.out.println(message);
        // Define a Predicate that checks if a Person was born before 1950
        storage.findAndSort(
                person -> person.getBirthDate().getYear()<1950,
                Comparator.comparing(Person::getBirthDate).reversed()
        ).forEach(System.out::println);
        // Define a Comparator that sorts Person objects by birthdate in reverse order
        // Call the findAndSort() method with the Predicate and Comparator as arguments
        // Print the List<Person> returned by the findAndSort() method
        System.out.println("----------------------");
    }

    /*
        TODO:  13.	Using findAndSort() find everyone sorted in following order: lastName > firstName > birthDate.
     */
    public static void exercise13(String message) {
        System.out.println(message);
        // Define a Comparator that sorts Person objects first by last name, then by first name, and finally by birthdate
        storage.findAndSort(
                Comparator.comparing(Person::getLastName).thenComparing(Person::getFirstName).thenComparing(Person::getBirthDate)
        ).forEach(System.out::println);
        // Call the findAndSort() method with the Comparator as an argument
        // Print the List<Person> returned by the findAndSort() method
        System.out.println("----------------------");
    }
}
