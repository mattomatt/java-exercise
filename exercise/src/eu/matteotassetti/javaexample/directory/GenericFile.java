package eu.matteotassetti.javaexample.directory;

/**
 * Created by mattomatt on 24/04/15.
 */
public interface GenericFile extends Comparable {
    Long getSize();
    String getName();
    Boolean isDirectory();

    String getPrintableString();
}
