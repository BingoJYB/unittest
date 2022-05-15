package com.bingo.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.bingo.converter.ConverterUtil.convertCelsiusToFahrenheit;
import static com.bingo.converter.ConverterUtil.convertFahrenheitToCelsius;

import java.util.Arrays;
import java.util.stream.Stream;

import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

class ConverterUtilTest {

    int[][] celsiusFahrenheitMapping = new int[][] { { 10, 50 }, { 40, 104 }, { 0, 32 } };

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
}
