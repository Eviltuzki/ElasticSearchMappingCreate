package top.eviltuzki.plugin.es;

import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue() throws ClassNotFoundException {
        String mapping = MappingBuilder.builder(Entity.class);
        System.out.println(mapping);
    }
}
