package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.MealServiceTest;

@ActiveProfiles({Profiles.JDBC, Profiles.POSTGRES_DB})
public class JdbcMealStorageTest extends MealServiceTest {
}
