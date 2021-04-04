package saturn.backend.core;

import org.springframework.stereotype.Service;

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
