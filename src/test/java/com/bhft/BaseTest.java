package com.bhft;

import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseTest {
    protected SoftAssertions softAssertions;
    public final Logger log = LoggerFactory.getLogger(BaseTest.class);

    @BeforeEach
    public void softAssert() {
        softAssertions = new SoftAssertions();
    }

    @AfterEach
    public void softAssertionsAll() {
        softAssertions.assertAll();
    }
}
