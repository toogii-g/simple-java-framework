package framework.util;

import framework.handler.fieldhandler.FieldTypeHandlerManager;

public class FieldTypeConverter {

    private final FieldTypeHandlerManager handlerManager = new FieldTypeHandlerManager();

    public Object convertToFieldType(Class<?> fieldType, String value) {
        return handlerManager.convertToFieldType(fieldType, value);
    }
}
