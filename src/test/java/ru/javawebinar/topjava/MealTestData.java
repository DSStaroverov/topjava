package ru.javawebinar.topjava;


import ru.javawebinar.topjava.model.Meal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class MealTestData {
    public static final int USER_ID = UserTestData.USER_ID;
    public static final int ADMIN_ID = UserTestData.ADMIN_ID;
    public static final Meal newMeal =
            new Meal(LocalDateTime.of(2010,02,24,14,00),"newEatLunch",1500);

    public static final List<Meal> mealsUserTestList = Arrays.asList(
            new Meal(100002, LocalDateTime.of(2019,02,24,14,00),"lunch",500),
            new Meal(100003, LocalDateTime.of(2019,02,24,19,00),"dinner",800),
            new Meal(100004, LocalDateTime.of(2019,02,24,9,00),"breakfast",600),
            new Meal(100005, LocalDateTime.of(2019,02,23,14,00),"lunch",500),
            new Meal(100006, LocalDateTime.of(2019,02,23,19,00),"dinner",500),
            new Meal(100007, LocalDateTime.of(2019,02,23,9,00),"breakfast",500)
    );
    public static final List<Meal> mealsAdminTestList = Arrays.asList(
            new Meal(100008, LocalDateTime.of(2019,02,23,14,00),"lunch",500),
            new Meal(100009, LocalDateTime.of(2019,02,23,19,00),"dinner",800),
            new Meal(100010, LocalDateTime.of(2019,02,23,9,00),"breakfast",600)

    );


             public static List<Meal> getData(int userId){
                 if(userId==USER_ID){
                     return new ArrayList<>(mealsUserTestList);
                 }else if(userId==ADMIN_ID){
                     return new ArrayList<>(mealsAdminTestList);
                 }else{
                    return Collections.emptyList();
                 }
             }
}
