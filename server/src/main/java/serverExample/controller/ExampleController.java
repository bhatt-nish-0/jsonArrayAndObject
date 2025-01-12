package serverExample.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ExampleController {

    @GetMapping("/test")
    public ResponseEntity<Object> x() {
        Person p = new Person("adam",22);
        Person p1 = new Person("nish",32);

        JSONObject a = p.toJSONObject();
        JSONObject b = p1.toJSONObject();
        b.put("rola","rappa");

        JSONArray jsonArray = new JSONArray();
        jsonArray.put(a);
        jsonArray.put(b);

        JSONObject a1 = new JSONObject();
        a1.put("hi","hello");
        a1.put("fruits",jsonArray);

        ResponseEntity<Object> ok = ResponseEntity.ok(a1);
        ObjectMapper objectMapper = new ObjectMapper();
        return ok;
    }

    @GetMapping("/test2")
    public void test() throws JsonProcessingException {
        ResponseEntity r = this.x();

//        System.out.println("full response is " + r);
//        int startIndex = r.indexOf("{");
//        int endIndex = r.lastIndexOf("}") + 1;
//        String jsonPart = "";
//        if (startIndex != -1 && endIndex != -1) {
//            jsonPart = r.substring(startIndex, endIndex);
//            System.out.println("Extracted JSON: " + jsonPart);
//        } else {
//            System.out.println("Invalid string format.");
//        }



        //JSONObject jsonObject = new JSONObject(jsonPart);
        System.out.println("56");
        System.out.println(r.getBody());
        System.out.println("58");
        JSONObject jsonObject = new JSONObject(r.getBody().toString());
        System.out.println(jsonObject);
        String hi = jsonObject.getString("hi");
        JSONArray fruits = jsonObject.getJSONArray("fruits");
        //String fruits = jsonObject.getString("fruits");
        System.out.println("64");
        List<Person> persons = new ArrayList<>();
        for (int i = 0; i < fruits.length(); i++) {
            JSONObject jsonObject1 = fruits.getJSONObject(i);
            String name = jsonObject1.getString("name");
            int age = jsonObject1.getInt("age");

            String rola = null;
            try {
                if (jsonObject1.getString("rola") != null) {
                    rola = jsonObject1.getString("rola");
                }
                System.out.println("no exception!");
            } catch (Exception e) {
                System.out.println("exception!");
            }


            Person person = new Person(name, age);
            if (rola != null) {

                person.setRola(rola);
            }
            persons.add(person);
        }

        for (int i =0; i< persons.size(); i++) {
            System.out.println("printing");
            System.out.println(persons.get(i).getAge());
            System.out.println(persons.get(i).getName());
            if (persons.get(i).getRola() != null) {
                System.out.println("in if bro!");
                System.out.println(persons.get(i).getRola());
            }
        }

        System.out.println("83");
        System.out.println(fruits);
    }

    @GetMapping("/test3")
    public ResponseEntity<Object> test34() {
        Person p = new Person("a",3);
        Person p1 = new Person("a1",4);
        Person p2 = new Person("a287",32);
        List<Person> list = new ArrayList<>();
        list.add(p);
        list.add(p1);
        list.add(p2);



        JSONArray jsonArray = new JSONArray();
        for (int i =0; i < list.size(); i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("name", list.get(i).getName());
            jsonObject.put("age", list.get(i).getAge());
            jsonArray.put(jsonObject);
        }
        return ResponseEntity.ok(jsonArray);
    }

    @GetMapping("/test4")
    public List<Person> test4() {
        ResponseEntity<Object> s = test34();
        System.out.println(s);
        Object body = s.getBody();

        JSONArray joro = new JSONArray(body.toString());
        List<Person > personstogoto = new ArrayList<>();
        for (int i = 0; i < joro.length(); i++) {
            JSONObject jsonObject1 = joro.getJSONObject(i);
            String name = jsonObject1.getString("name");
            int age = jsonObject1.getInt("age");


            Person person = new Person(name, age);
            personstogoto.add(person);
        }

        return personstogoto;

    }

}

class Person {
    private String name;
    private int age;

    private String rola;

    // Constructor
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    // Convert POJO to JSONObject
    public JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", name);
        jsonObject.put("age", age);
        return jsonObject;
    }

    public void setRola(String rola) {
        this.rola = rola;
    }

    public String getRola() {
        return rola;
    }
}
