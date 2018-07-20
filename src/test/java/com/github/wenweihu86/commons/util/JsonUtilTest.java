package com.github.wenweihu86.commons.util;

import com.github.wenweihu86.commmons.util.JsonUtil;
import lombok.Getter;
import lombok.Setter;
import org.junit.Test;

import java.util.Date;

public class JsonUtilTest {

    @Setter
    @Getter
    public static class Person {
        private String name;
        private Integer age;
        private Integer gender;
        private Date birthday;
    }

    @Test
    public void testToJson() {
        Person person = new Person();
        person.setName("Jack");
        person.setAge(50);
        person.setGender(1);
        person.setBirthday(new Date());
        String personJson = JsonUtil.toJson(person);
        System.out.println(personJson);
        Person newPerson = JsonUtil.fromJson(personJson, Person.class);
        System.out.println(newPerson.birthday);
    }
}
