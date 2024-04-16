package se.lexicon.data;


import se.lexicon.model.Person;
import se.lexicon.util.PersonGenerator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;


/**
 * Create implementations for all methods. I have already provided an implementation for the first method *
 */
public class DataStorageImpl implements DataStorage {

    private static final DataStorage INSTANCE;

    static {
        INSTANCE = new DataStorageImpl();
    }

    private final List<Person> personList;

    private DataStorageImpl() {
        personList = PersonGenerator.getInstance().generate(1000);
    }

    static DataStorage getInstance() {
        return INSTANCE;
    }


    @Override
    public List<Person> findMany(Predicate<Person> filter) {
        // Create an empty list to store the Person objects that satisfy the filter
        List<Person> result = new ArrayList<>();

        // Iterate over each Person object in the personList
        for (Person person : personList) {
            // Check if the Person object satisfies the filter
            if (filter.test(person)) {
                // If the Person object satisfies the filter, add it to the result list
                result.add(person);
            }
        }

        // After all Person objects have been checked, return the list of Person objects that satisfy the filter
        return result;
    }

    @Override
    public Person findOne(Predicate<Person> filter) {
        // Iterate over each Person object in the personList
        for (Person person : personList){
            // Check if the Person object satisfies the filter
            if (filter.test(person)){
                // If the Person object satisfies the filter, return the Person object
                return person;
            }
        }

        // If no Person object satisfies the filter, return null
        return null;
    }

    @Override
    public String findOneAndMapToString(Predicate<Person> filter, Function<Person, String> personToString) {
        // Iterate over each Person object in the personList
        for (Person person : personList){
            // Check if the Person object satisfies the filter
            if (filter.test(person)){
                // If the Person object satisfies the filter, convert it to a String using the personToString function
                // and return the String representation
                return personToString.apply(person);
            }
        }

        // If no Person object satisfies the filter, return null
        return null;
    }

    @Override
    public List<String> findManyAndMapEachToString(Predicate<Person> filter, Function<Person, String> personToString) {
        // Create an empty list to store the String representations of Person objects that satisfy the filter
        List<String> personsFound = new ArrayList<>();

        // Iterate over each Person object in the personList
        for (Person person : personList){
            // Check if the Person object satisfies the filter
            if (filter.test(person)){
                // If the Person object satisfies the filter, convert it to a String using the personToString function
                // and add it to the personsFound list
                personsFound.add(personToString.apply(person));
            }
        }

        // After all Person objects have been checked, return the list of String representations of Person objects that satisfy the filter
        return personsFound;
    }

    @Override
    public void findAndDo(Predicate<Person> filter, Consumer<Person> consumer) {
        // Iterate over each Person object in the personList
        for (Person person:personList){
            // Check if the Person object satisfies the filter
            if (filter.test(person)){
                consumer.accept(person);
            }
        }
    }

    @Override
    public List<Person> findAndSort(Comparator<Person> comparator) {
        // Create a new list that is a copy of personList
        List<Person> sortedPersons = new ArrayList<>(personList);

        // Sort the copied list using the provided comparator
        sortedPersons.sort(comparator);

        // Return the sorted list
        return sortedPersons;
    }

    @Override
    public List<Person> findAndSort(Predicate<Person> filter, Comparator<Person> comparator) {
        // Create an empty list to store the Person objects that satisfy the filter
        List<Person> sortedPersons = new ArrayList<>();

        // Iterate over each Person object in the personList
        for (Person person : personList){
            // Check if the Person object satisfies the filter
            if (filter.test(person)){
                // If the Person object satisfies the filter, add it to sortedPersons
                sortedPersons.add(person);
            }
        }

        // After all Person objects have been checked, sort sortedPersons using the provided comparator
        sortedPersons.sort(comparator);

        // Return the sorted list
        return sortedPersons;
    }
}
