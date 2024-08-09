package framework.handler.fieldhandler;

import framework.interfaces.FieldTypeHandler;

public class BooleanHandler implements FieldTypeHandler {

    @Override
    public Object convert(String value) {
        return Boolean.parseBoolean(value);
    }
}
