package eu.matteotassetti.javaexample.directory;

import eu.matteotassetti.javaexample.directory.exceptions.DirNotFoundException;
import eu.matteotassetti.javaexample.directory.exceptions.InvalidFileException;
import eu.matteotassetti.javaexample.directory.exceptions.NotADirException;

import java.io.File;
import java.util.Collections;

public class RootDirectory extends ConcreteDirectory{



    /**
     * Default constructor for the root directory
     * @param path the absolute path of the directory
     * @throws InvalidFileException if the given path is not valid
     */
    public RootDirectory(String path) throws InvalidFileException {
        super(new File(path));
    }

    @Override
    public void printDiskUsage(){
        Collections.sort(childrens);
        for (GenericFile child:childrens)
        {
            System.out.println(child.getPrintableString());
        }
    }




}
