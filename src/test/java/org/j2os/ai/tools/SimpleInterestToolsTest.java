package org.j2os.ai.tools;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SimpleInterestToolsTest {

    private SimpleInterestTools tools;

    @BeforeEach
    void setUp() {
        tools = new SimpleInterestTools();
    }

    @Test
    void shouldCalculateSimpleInterestCorrectly() {
        String result = tools.calculateSimpleInterest(
                1000,
                3,
                6
        );

        assertEquals(
                "Principal: EUR 1000.00; Simple interest: EUR 15.00; Total: EUR 1015.00",
                result
        );
    }

    @Test
    void shouldRejectNegativePrincipal() {
        assertThrows(
                IllegalArgumentException.class,
                () -> tools.calculateSimpleInterest(-1000, 3, 6)
        );
    }

    @Test
    void shouldRejectRateGreaterThan100() {
        assertThrows(
                IllegalArgumentException.class,
                () -> tools.calculateSimpleInterest(1000, 101, 6)
        );
    }

    @Test
    void shouldRejectDurationGreaterThan1200Months() {
        assertThrows(
                IllegalArgumentException.class,
                () -> tools.calculateSimpleInterest(1000, 3, 1201)
        );
    }
}
