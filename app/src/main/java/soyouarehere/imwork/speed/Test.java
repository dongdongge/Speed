package soyouarehere.imwork.speed;

/**
 * Created by li.xiaodong on 2018/8/2.
 */

public class Test {


    public static void main(String[] args) {

        Person person = Person.getPerson();
        System.err.println(person.a);
        System.err.println(person.b);
    }


}

class Person {

    public static Person person = new Person();
    public static int a = 3;
    public static int b;

    private Person() {
        a = 4;
        b = 2;
    }

    public static Person getPerson() {
        return person;
    }

}



