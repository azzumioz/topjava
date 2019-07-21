package ru.javawebinar.topjava.service;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.web.meal.MealRestController;

public class RuleTest implements TestRule {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);

    @Override
    public Statement apply(Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                long startTime = System.nanoTime();
                base.evaluate();
                log.info("Test {} execution time = {} ms", description.getTestClass().getName(), (System.nanoTime() - startTime) / 1000000);
            }
        };
    }
}
