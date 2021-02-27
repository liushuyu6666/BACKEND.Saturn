package jupiter.backend.core;

import jupiter.backend.shop.Shop;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Service;

import javax.swing.text.html.parser.Entity;
import java.lang.reflect.InvocationTargetException;

@Service
public class CoreService<T> {

    public Object create(Object body) throws ClassNotFoundException,
            IllegalAccessException, InstantiationException, NoSuchMethodException,
            InvocationTargetException {
        Class classTemp = body.getClass();

//        Class classTemp = Class.forName(className);
        return body;

    }

}
