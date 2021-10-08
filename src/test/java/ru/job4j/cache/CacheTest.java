package ru.job4j.cache;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class CacheTest {

    @Test
    public void whenSuccessfulAdd() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        assertThat(cache.add(base), is(true));
    }

    @Test
    public void whenMultiplyAdd() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        Base base2 = new Base(1, 2);
        cache.add(base);
        assertThat(cache.add(base2), is(false));
    }

    @Test
    public void whenUpdate() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        Base base2 = new Base(1, 1);
        cache.add(base);
        assertThat(cache.update(base2), is(true));
    }

    @Test
    public void whenUpdateThenUpdate() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        Base base2 = new Base(1, 2);
        cache.add(base);
        cache.update(base);
        assertThat(cache.update(base2), is(true));
    }

    @Test (expected = OptimisticException.class)
    public void whenDifferentVersions() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        Base base2 = new Base(1, 2);
        cache.add(base);
        cache.update(base2);
    }

    @Test
    public void whenDelete() {
        Cache cache = new Cache();
        Base base = new Base(1, 1);
        Base base2 = new Base(1, 1);
        cache.add(base);
        cache.delete(base);
        assertThat(cache.add(base2), is(true));
    }


}