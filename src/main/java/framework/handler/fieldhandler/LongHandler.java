package framework.handler.fieldhandler;

import framework.handler.beanhandler.FieldTypeHandler;

public class LongHandler  implements FieldTypeHandler {

    @Override
    public Object convert(String value) {
        return Long.parseLong(value);
    }
}
