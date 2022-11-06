package spase.harbour.friends.util;

import spase.harbour.friends.model.Person;

import java.util.List;

public class FilterUtils {

    public static List<Person> filterPersonList(List<Person> personList, String criteria) {
        return personList.stream().filter(person -> {
            if(person.getName().contains(criteria)){
                return true;
            } else if(person.getEmail().contains(criteria)) {
                return true;
            } else if(person.getAddress().contains(criteria)){
                return true;
            } else {
                return false;
            }
        }).toList();
    }
}
