package eu.matteotassetti.javaexample.directory;

import eu.matteotassetti.javaexample.directory.exceptions.InvalidFileException;

import java.io.File;

/**
 * Created by mattomatt on 24/04/15.
 */
public class ConcreteFile implements GenericFile {

    File file;
    Long size;

    public ConcreteFile(File file) throws InvalidFileException
    {
        this.file=file;
    }

    @Override
    public Long getSize() {
        return file.length();
    }

    @Override
    public String getName() {
        return file.getName();
    }

    @Override
    public Boolean isDirectory() {
        return false;
    }


    @Override
    public int compareTo(Object o) {
        if (o instanceof ConcreteFile) {
            ConcreteFile cf=(ConcreteFile) o;
            if (cf.getSize() - this.getSize()  > 0) return 1;
            else if (this.getSize() - cf.getSize() == 0) return 0;
            return -1;
        }
        else return 0;
    }
}
