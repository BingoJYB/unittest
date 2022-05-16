package com.bingo.di;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Constructor;
import java.util.Arrays;

import javax.inject.Inject;

import org.junit.jupiter.api.Test;

class ServiceTest {

    @Test
    void ensureJSR330Constructor() {
        Constructor<?>[] construtors = Service.class.getConstructors();
        assertEquals(1, Arrays.stream(construtors)
                .filter(constructor -> constructor.getAnnotation(Inject.class) != null).count(),
                "validates that the Service class only has one constructor annotated with @Inject");
    }

}
