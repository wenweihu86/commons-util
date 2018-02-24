package com.github.wenweihu86.commons.util;

import com.github.wenweihu86.commmons.util.IdGenerator;
import org.junit.Test;

public class IdGeneratorTest {

    @Test
    public void testGetId() {
        IdGenerator idGenerator = IdGenerator.instance();
        for (int i = 0; i < 10; i++) {
            System.out.println(idGenerator.getId());
        }
    }
}
