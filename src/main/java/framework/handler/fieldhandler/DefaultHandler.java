package framework.handler.fieldhandler;

import framework.interfaces.FieldTypeHandler;

public class DefaultHandler implements FieldTypeHandler {

    @Override
    public Object convert(String value) {
        return value;
    }
}
