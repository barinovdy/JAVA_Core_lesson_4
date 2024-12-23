package ru.gb;

//Урок 4. Обработка исключений
//В класс покупателя добавить перечисление с гендерами,
// добавить в сотрудника свойство «пол» со значением
// созданного перечисления. Добавить геттеры, сеттеры.
//Добавить в основную программу перечисление с праздниками
// (нет праздника, Новый Год, 8 марта, 23 февраля),
// написать метод, принимающий массив сотрудников,
// поздравляющий всех сотрудников с Новым Годом,
// женщин с 8 марта, а мужчин с 23 февраля, если сегодня
// соответствующий день.

import ru.gb.enums.Gender;
import ru.gb.enums.Holiday;
import ru.gb.exception.AmountException;
import ru.gb.exception.CustomerException;
import ru.gb.exception.ProductException;
import ru.gb.person.Customer;
import ru.gb.person.Employee;
import ru.gb.shop.Item;
import ru.gb.shop.Order;

import java.util.ArrayList;
import java.util.List;

import static ru.gb.enums.Holiday.*;

public class Main {

    static final Customer[] people = {
            new Customer("Daria", 26, "8(583)7584539", Gender.FEMALE),
            new Customer("Aleksandr", 24, "8(935)8674973", Gender.MALE),
            new Customer("Ludmila", 47, "8(482)7496387", Gender.FEMALE),
    };

    static final Employee[] employees = {
            new Employee("Ivanov","Ivan","Ivanovich", "chief","8(123)6478398", 30000, 35, Gender.MALE),
            new Employee("Sergeev","Sergey","Sergeevich", "manager","8(435)3466734", 20000, 30, Gender.MALE),
            new Employee("Petrova","Daria","Ivanovna", "manager","8(856)8562965", 18000, 28, Gender.FEMALE),
            new Employee("Dal","Irina","Viktorovna", "accountant","8(275)1956738", 20000, 25, Gender.FEMALE),

    };

    static final Item[] items = {
            new Item("Ball", 100),
            new Item("Sandwich", 1000),
            new Item("Table", 10000),
            new Item("Car", 100000),
            new Item("Rocket", 10000000)
    };

    public static void main(String[] args) {


        celebrate(employees, MARCH_8);


        /**
         * Classwork
         */
        Order[] orders = new Order[5];

        Object[][] info = {
                {people[0], items[0], 1},
                {people[1], items[1], -1},
                {people[0], items[2], 150},
                {people[1], new Item("Flower", 10), 1},
                {new Customer("Fedor", 40, "+3-444-555-66-77", Gender.MALE), items[3], 1},
        };

        int capacity = 0;
        int i = 0;
        while (capacity != orders.length - 1 || i != info.length) {
            try {
                orders[capacity] = buy((Customer) info[i][0], (Item) info[i][1], (int) info[i][2]);
                capacity++;
            } catch (ProductException e) {
                e.printStackTrace();
            } catch (AmountException e) {
                orders[capacity++] = buy((Customer) info[i][0], (Item) info[i][1], 1);
            } catch (CustomerException e) {
                throw new RuntimeException(e);
            } finally {
                System.out.println("Orders made: " + capacity);
            }
            ++i;
        }

    }

    public static Order buy(Customer who, Item what, int howMuch) {
        if (!isInArray(people, who))
            throw new CustomerException("Unknown customer: " + who);
        if (!isInArray(items, what))
            throw new ProductException("Unknown item: " + what);
        if (howMuch < 0 || howMuch > 100)
            throw new AmountException("Incorrect amount: " + howMuch);
        return new Order(who, what, howMuch);
    }

    private static boolean isInArray(Object[] arr, Object o) {
        for (Object value : arr) if (value.equals(o)) return true;
        return false;
    }

    private static void celebrate(Employee[] employees, Holiday holiday) {
        for (int i = 0; i < employees.length; i++) {
            switch (holiday) {
                case NEW_YEAR:
                    System.out.println(employees[i].getName() + ", happy New Year!");
                    break;
                case FEBRUARY_23:
                    if (employees[i].getGender() == Gender.MALE)
                        System.out.println(employees[i].getName() + ", happy February 23rd!");
                    break;
                case MARCH_8:
                    if (employees[i].getGender() == Gender.FEMALE)
                        System.out.println(employees[i].getName() + ", happy march 8th!");
                    break;
                default:
                    System.out.println(employees[i].getName() + ", celebrate this morning!");
                    }
            }
        }
}