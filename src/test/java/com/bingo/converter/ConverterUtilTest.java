package com.bingo.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.bingo.converter.ConverterUtil.convertCelsiusToFahrenheit;
import static com.bingo.converter.ConverterUtil.convertFahrenheitToCelsius;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class ConverterUtilTest {

    int[][] celsiusFahrenheitMapping = new int[][] { { 10, 50 }, { 40, 104 }, { 0, 32 } };

    public static int[][] data() {
        return new int[][] { { 10, 50 }, { 40, 104 }, { 0, 32 } };
    }

    @TestFactory
    Stream<DynamicTest> ensureThatCelsiumConvertsToFahrenheit() {
        return Arrays.stream(celsiusFahrenheitMapping).map(entry -> {
            // access celsius and fahrenheit from entry
            int celsius = entry[0];
            int fahrenheit = entry[1];
            return DynamicTest.dynamicTest(celsius + " C is " + fahrenheit + " F",
                    () -> assertEquals(fahrenheit, convertCelsiusToFahrenheit(celsius)));
        });

    }

    @TestFactory
    Stream<DynamicTest> ensureThatFahrenheitToCelsiumConverts() {
        return Arrays.stream(celsiusFahrenheitMapping).map(entry -> {
            // access celsius and fahrenheit from entry
            int celsius = entry[0];
            int fahrenheit = entry[1];
            return DynamicTest.dynamicTest(fahrenheit + " F is " + celsius + " C",
                    () -> assertEquals(celsius, convertFahrenheitToCelsius(fahrenheit)));
        });
    }

    @ParameterizedTest(name = "{index} called with: {0}")
    @MethodSource(value = "data")
    void testWithCelsiumConvertsToFahrenheit(int[] data) {
        int celsius = data[0];
        int expected = data[1];
        assertEquals(expected, convertCelsiusToFahrenheit(celsius));
    }

    @ParameterizedTest(name = "{index} called with: {0}")
    @MethodSource(value = "data")
    void testWithFahrenheitToCelsiumConverts(int[] data) {
        int fahrenheit = data[1];
        int expected = data[0];
        assertEquals(expected, convertFahrenheitToCelsius(fahrenheit));
    }
}
