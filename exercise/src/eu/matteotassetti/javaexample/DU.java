package eu.matteotassetti.javaexample;

import eu.matteotassetti.javaexample.directory.ConcreteDirectory;
import eu.matteotassetti.javaexample.directory.RootDirectory;
import eu.matteotassetti.javaexample.directory.exceptions.DirNotFoundException;
import eu.matteotassetti.javaexample.directory.exceptions.InvalidFileException;
import eu.matteotassetti.javaexample.directory.exceptions.NotADirException;
import eu.matteotassetti.javaexample.directory.exceptions.NotAnAbsolutePathException;
import eu.matteotassetti.javaexample.validator.ParamsParser;
import eu.matteotassetti.javaexample.validator.errors.NotEnoughParamsException;
import eu.matteotassetti.javaexample.validator.errors.ParamsException;
import eu.matteotassetti.javaexample.validator.errors.TooManyParamsException;

/**
 * Created by mattomatt on 24/04/15.
 */
public class DU {

    public static void main(String args[])
    {
        String path= null;
        try {
            path = ParamsParser.getPath(args);
        } catch (NotEnoughParamsException e) {
            System.out.println("Not Enough parameters!");
            printUsage();
        } catch (TooManyParamsException e) {
            System.out.println("Too many parameters!");
            printUsage();
        } catch (ParamsException e){
            printUsage();
        }
        if (path!=null) {
            ConcreteDirectory rootDirectory=null;
            try {
                rootDirectory = new RootDirectory(path);
            } catch (DirNotFoundException e) {
                System.out.println("InvalidPath " + path + " do not exists");
            } catch (NotADirException e) {
                System.out.println("InvalidPath " + path + " should link to a directory, not a file!");
            } catch (NotAnAbsolutePathException e) {
                System.out.println("InvalidPath " + path + " is not absolute. Path should be absolute");
            } catch (InvalidFileException e) {
                printUsage();
            }
            if (rootDirectory!=null)
            {
                rootDirectory.printDiskUsage();
            }
        }
    }

    private static void printUsage() {
        System.out.println("Usage: du absolutepathtodir");
    }
}
