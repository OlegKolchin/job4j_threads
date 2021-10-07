package ru.job4j;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CASCountTest {

    @Test
    public void defaultTest() {
        CASCount count = new CASCount(new AtomicReference<>(0));
        count.increment();
        count.increment();
        assertThat(count.get(), is(2));
    }
}