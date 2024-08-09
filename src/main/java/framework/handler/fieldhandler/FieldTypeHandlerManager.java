package framework.handler.fieldhandler;

import framework.handler.beanhandler.FieldTypeHandler;

import java.util.HashMap;
import java.util.Map;

public class FieldTypeHandlerManager {

    private final Map<Class<?>, FieldTypeHandler> handlers = new HashMap<>();
    private final FieldTypeHandler defaultHandler = new DefaultHandler();

    public FieldTypeHandlerManager() {
        handlers.put(int.class, new IntegerHandler());
        handlers.put(Integer.class, new IntegerHandler());
        handlers.put(long.class, new LongHandler());
        handlers.put(Long.class, new LongHandler());
        handlers.put(boolean.class, new BooleanHandler());
        handlers.put(Boolean.class, new BooleanHandler());
    }

    public Object convertToFieldType(Class<?> fieldType, String value) {
        return handlers.getOrDefault(fieldType, defaultHandler).convert(value);
    }
}
