package eu.matteotassetti.javaexample.directory;

import eu.matteotassetti.javaexample.directory.exceptions.InvalidFileException;

import java.io.File;

/**
 * Created by mattomatt on 24/04/15.
 */
public class ConcreteFile implements GenericFile {

    Long size;
    String name;

    public ConcreteFile(File file) throws InvalidFileException
    {
        this.name=file.getAbsolutePath();
        if (file.isFile()) this.size=file.length();
    }

    @Override
    public Long getSize() {
        return size;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Boolean isDirectory() {
        return false;
    }

    @Override
    public String getPrintableString() {
        return (isDirectory()?"DIR\t":"FILE")+"\t"+this.getName()+"\t\t("+this.getSize()/1024+" KB)";
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
