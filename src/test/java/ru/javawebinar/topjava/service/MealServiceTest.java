package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.MealTestData.*;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {
    private int user_id=USER_ID;
    private int admin_id=ADMIN_ID;

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(100002,user_id);
        assertThat(meal).isEqualTo(Objects.requireNonNull(getData(user_id)).get(0));
    }

    @Test(expected = NotFoundException.class)
    public void getAlien() {
        Meal meal = service.get(100002,admin_id);
        assertThat(meal).isEqualTo(Objects.requireNonNull(getData(admin_id)).get(0));
    }

    @Test
    public void delete() {
        List<Meal> mealsList = new ArrayList<>(Objects.requireNonNull(getData(user_id)));
        mealsList.remove(mealsList.get(0));
        service.delete(100002,user_id);
        assertThat(service.getAll(user_id)).containsAll(mealsList);
    }

    @Test(expected = NotFoundException.class)
    public void deleteAlien() {
        service.delete(100002,admin_id);
    }

    @Test
    public void getBetweenDateTimes() {
        LocalDateTime start = LocalDateTime.of(2019,2,24,13,0);
        LocalDateTime end = LocalDateTime.of(2019,2,25,13,0);

        List<Meal> actual = service.getBetweenDateTimes(start,end,user_id);
        List<Meal> expected = Objects.requireNonNull(getData(user_id)).stream()
                .filter(meal -> meal.getDateTime().isAfter(start))
                .filter(meal -> meal.getDateTime().isBefore(end))
                .collect(Collectors.toList());

        assertThat(actual).containsAll(expected);

    }

    @Test
    public void getAll() {
        List<Meal> mealsList = new ArrayList<>(Objects.requireNonNull(getData(user_id)));
        assertThat(service.getAll(user_id)).containsAll(mealsList);
    }

    @Test
    public void update() {
        Meal expected = service.get(100002,user_id);
        expected.setCalories(200);
        expected.setDateTime(LocalDateTime.of(2015,2,24,13,0));
        expected.setDescription("new eat");

        service.update(expected,user_id);

        Meal actual = service.get(100002,user_id);

        assertThat(actual).isEqualTo(expected);
    }

    @Test(expected = NotFoundException.class)
    public void updateAlien() {
        Meal expected = service.get(100002,user_id);
        expected.setCalories(200);
        expected.setDateTime(LocalDateTime.of(2015,2,24,13,0));
        expected.setDescription("new eat");

        service.update(expected,admin_id);

        Meal actual = service.get(100002,user_id);

        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void create() {
        Meal expected = newMeal;

        service.create(expected,user_id);

        Meal actual = service.get(expected.getId(),user_id);

        assertThat(actual).isEqualTo(expected);

    }
}