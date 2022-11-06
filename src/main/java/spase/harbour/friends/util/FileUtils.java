package spase.harbour.friends.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import spase.harbour.friends.model.Person;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileUtils {

    public static List<Person> readJSON(File file) throws IOException {
        ObjectMapper objectMapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
        JsonNode rootNode = objectMapper.readTree(file);
        List<Person> personList =new ArrayList<>();
        if(rootNode.getNodeType().name().equals("ARRAY")) {
            personList = objectMapper.convertValue(rootNode, new TypeReference<>() {});
        } else if(rootNode.getNodeType().name().equals("OBJECT")) {
            personList.add(objectMapper.convertValue(rootNode, Person.class));
        }
        return personList;
    }

    public static List<Person> getPersonsFromFile(File file) throws IOException {
        return readJSON(file);
    }

    public static void writeIntoJson(List<Person> personList, String filePath) throws IOException {
        File file = new File(filePath);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writeValue(file, personList);


    }

}
