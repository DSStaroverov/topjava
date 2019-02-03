package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
//        .toLocalDate();
//        .toLocalTime();
    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        /*
        List<UserMealWithExceed> filteredList = new ArrayList<>();
        Map<LocalDate, Integer> tmpMap = new HashMap<>();

        for (UserMeal meal:mealList) {
            tmpMap.put(meal.getDateTime().toLocalDate(),tmpMap.getOrDefault(meal.getDateTime().toLocalDate(),0)+meal.getCalories());
        }
        for (UserMeal meal:mealList) {
            if(TimeUtil.isBetween(meal.getDateTime().toLocalTime(),startTime,endTime)){
                if(tmpMap.get(meal.getDateTime().toLocalDate())<=caloriesPerDay) {
                    filteredList.add(new UserMealWithExceed(meal, false));
                }else {
                    filteredList.add(new UserMealWithExceed(meal, true));
                }
            }
        }
        */
        Map<LocalDate, Integer> streamMap = mealList.stream()
                .collect(Collectors.toMap(meal -> meal.getDateTime().toLocalDate(), UserMeal::getCalories, Integer::sum));

        return mealList.stream()
                .filter(x -> TimeUtil.isBetween(x.getDateTime().toLocalTime(), startTime, endTime))
                .map(x -> (new UserMealWithExceed(x, streamMap.get(x.getDateTime().toLocalDate()) > caloriesPerDay)))
                .collect(Collectors.toList());

    }
}
