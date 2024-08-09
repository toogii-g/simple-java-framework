package framework.handler.fieldhandler;

import framework.interfaces.FieldTypeHandler;

public class LongHandler  implements FieldTypeHandler {

    @Override
    public Object convert(String value) {
        return Long.parseLong(value);
    }
}
