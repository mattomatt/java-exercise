package eu.matteotassetti.javaexample.directory;

import eu.matteotassetti.javaexample.directory.exceptions.DirNotFoundException;
import eu.matteotassetti.javaexample.directory.exceptions.InvalidFileException;
import eu.matteotassetti.javaexample.directory.exceptions.NotADirException;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Created by mattomatt on 24/04/15.
 */
public class ConcreteDirectory extends ConcreteFile {

    List<GenericFile> childrens;

    public ConcreteDirectory(java.io.File file) throws InvalidFileException {
        super(file);
        if (!file.exists()) throw new DirNotFoundException();
        else if (!file.isDirectory()) throw new NotADirException();
        childrens=new ArrayList<GenericFile>();
        java.io.File[] files=file.listFiles();
        for (java.io.File childFile:files)
        {
            if (childFile.isFile()) childrens.add(new ConcreteFile(childFile));
            if (childFile.isDirectory()) childrens.add(new ConcreteDirectory(childFile));
        }
    }

    @Override
    public Long getSize() {
        if (size==null) {
            Long len = 0l;
            for (GenericFile child : childrens) {
                len = len + child.getSize();
            }
            size=len;
        }
        return size;
    }

    @Override
    public Boolean isDirectory() {
        return true;
    }

    public void printDiskUsage(){
        for (GenericFile child:childrens)
        {
            if(child.isDirectory())
            {
                System.out.println(child.getName()+"\t\t"+child.getSize());
            }
        }
    }
}
