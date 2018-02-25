package com.github.wenweihu86.commons.util;

import com.github.wenweihu86.commmons.util.JsonUtil;
import lombok.Getter;
import lombok.Setter;
import org.junit.Assert;
import org.junit.Test;

public class JsonUtilTest {

    @Setter
    @Getter
    public static class Person {
        private String name;
        private Integer age;
        private Integer gender;
    }

    @Test
    public void testToJson() {
        Person person = new Person();
        person.setName("Jack");
        person.setAge(50);
        person.setGender(1);
        String personJson = JsonUtil.toJson(person);
        System.out.println(personJson);
        Person newPerson = JsonUtil.fromJson(personJson, Person.class);
        Assert.assertTrue(newPerson.getName().equals(person.getName()));
    }
}
