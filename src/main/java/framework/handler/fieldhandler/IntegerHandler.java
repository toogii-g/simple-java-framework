package framework.handler.fieldhandler;

import framework.handler.beanhandler.FieldTypeHandler;

public class IntegerHandler implements FieldTypeHandler {

    @Override
    public Object convert(String value) {
        return Integer.parseInt(value);
    }
}
