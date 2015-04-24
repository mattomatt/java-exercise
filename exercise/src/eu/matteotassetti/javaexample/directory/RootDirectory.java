package eu.matteotassetti.javaexample.directory;

import eu.matteotassetti.javaexample.directory.exceptions.DirNotFoundException;
import eu.matteotassetti.javaexample.directory.exceptions.InvalidFileException;
import eu.matteotassetti.javaexample.directory.exceptions.NotADirException;

import java.io.File;

public class RootDirectory extends ConcreteDirectory{



    /**
     * Default constructor for the root directory
     * @param path the absolute path of the directory
     * @throws InvalidFileException if the given path is not valid
     */
    public RootDirectory(String path) throws InvalidFileException {
        super(new File(path));
        if (!file.exists()) throw new DirNotFoundException();
        else if (!file.isDirectory()) throw new NotADirException();
    }

    @Override
    public void printDiskUsage(){
        for (GenericFile child:childrens)
        {
            System.out.println(child.getName()+"\t\t"+child.getSize());
        }
    }




}
