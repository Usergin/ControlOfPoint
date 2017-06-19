package data.local.parser;

import io.reactivex.Completable;

import javax.xml.bind.JAXBException;
import java.io.File;

/**
 * Created by OldMan on 19.06.2017.
 */
public interface Parser {
    Object getObject(String name, Class c) throws JAXBException;
    void saveObject(String name, Object o) throws JAXBException;
}
