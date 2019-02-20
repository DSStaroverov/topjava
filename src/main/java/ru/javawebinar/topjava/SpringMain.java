package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static ru.javawebinar.topjava.web.SecurityUtil.setAuthUserId;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            MealRestController mealRestController = appCtx.getBean(MealRestController.class);

            adminUserController.create(new User(null, "adminName", "email@mail.ru", "password", Role.ROLE_ADMIN));
            adminUserController.create(new User(null, "userName2", "user@mail.ru", "password", Role.ROLE_USER));
            adminUserController.create(new User(null, "userName1", "user1@mail.ru", "password", Role.ROLE_USER));
            System.out.println("--------actions------");
            //adminUserController.delete(2);
            setAuthUserId(2);
            System.out.println(mealRestController.getAll());
            System.out.println(mealRestController.get(7));
            mealRestController.save(new Meal(7,2, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));

            System.out.println(mealRestController.getAll());


        }
    }
}
