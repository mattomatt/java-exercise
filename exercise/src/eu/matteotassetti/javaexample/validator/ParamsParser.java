package eu.matteotassetti.javaexample.validator;

import eu.matteotassetti.javaexample.validator.errors.NotEnoughParamsException;
import eu.matteotassetti.javaexample.validator.errors.ParamsException;
import eu.matteotassetti.javaexample.validator.errors.TooManyParamsException;

/**
 * Created by mattomatt on 24/04/15.
 */
public class ParamsParser {

    public static String getPath(String args[]) throws ParamsException{
        if (args.length==0)
        {
            throw new NotEnoughParamsException();
        }
        else if (args.length>1)
        {
            throw new TooManyParamsException();
        }
        return args[0];
    }
}
