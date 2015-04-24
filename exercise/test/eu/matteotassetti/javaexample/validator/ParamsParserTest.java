package eu.matteotassetti.javaexample.validator;

import eu.matteotassetti.javaexample.validator.errors.NotEnoughParamsException;
import eu.matteotassetti.javaexample.validator.errors.TooManyParamsException;
import junit.framework.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by mattomatt on 24/04/15.
 */
public class ParamsParserTest {

    @Test(expected=NotEnoughParamsException.class)
    public void testNoArgumentsThrowsException() throws Exception {
        String[] args={};
        ParamsParser.getPath(args);
    }

    @Test(expected= TooManyParamsException.class)
    public void testMoreThenOneArgumentsThrowsException() throws Exception {
        String[] args={"p1","p2"};
        ParamsParser.getPath(args);
    }

    @Test
    public void testValidArgument() throws Exception {
        String expectedPath="path";
        String[] args={expectedPath};
        String path=ParamsParser.getPath(args);
        Assert.assertEquals(path,expectedPath);
    }
}