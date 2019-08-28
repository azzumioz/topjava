package ru.javawebinar.topjava.service.jpa;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.repository.JpaUtil;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

public abstract class AbstractUserServiceTestWithClearCache extends AbstractUserServiceTest {

    @Autowired
    protected JpaUtil jpaUtil;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        if (jpaUtil != null) {
            jpaUtil.clear2ndLevelHibernateCache();
        }
    }

}
