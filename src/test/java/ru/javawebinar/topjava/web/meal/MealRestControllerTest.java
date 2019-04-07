package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.TestUtil.readFromJson;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.util.MealsUtil.createWithExcess;

class MealRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = MealRestController.REST_URL;

    @Test
    void getAll() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void testGet() throws Exception {
        mockMvc.perform(get(REST_URL+"/"+MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL +"/"+ MEAL1_ID))
                .andDo(print())
                .andExpect(status().isNoContent());
        List <Meal> meals = new ArrayList<>(MEALS);
        meals.remove(MEAL1);
        meals.sort(Comparator.comparing(Meal::getDateTime).reversed());
        assertMatch(mealService.getAll(USER_ID), meals);
    }

    @Test
    void testCreate() throws Exception {
        Meal newMeal = getCreated();
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newMeal)))
                .andExpect(status().isCreated());

        Meal returned = readFromJson(action, Meal.class);
        newMeal.setId(returned.getId());
        List <Meal> meals = new ArrayList<>(MEALS);
        meals.add(newMeal);
        meals.sort(Comparator.comparing(Meal::getDateTime).reversed());
        assertMatch(mealService.getAll(USER_ID),meals);
    }

    @Test
    void testUpdate() throws Exception {
        Meal updated = getUpdated();
        mockMvc.perform(put(REST_URL+"/"+MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
        assertMatch(mealService.get(MEAL1_ID,USER_ID),updated);
    }

    @Test
    void testGetBetween() throws Exception {
        mockMvc.perform(get(REST_URL + "/filter?startDate=2015-05-30T07:00&endDate=2015-05-31T11:00:00"))
                .andExpect(status().isOk())
                .andDo(print());
                //.andExpect(contentJson(createWithExcess(MEAL4, true), createWithExcess(MEAL1, false)));
    }
}