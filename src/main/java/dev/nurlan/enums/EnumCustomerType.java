package dev.nurlan.enums;

import java.util.HashMap;
import java.util.Map;

public enum EnumCustomerType {

    BANK_CUSTOMER(1), NOMINAL_CUSTOMER(2);

    private int value;

    private static final Map<Integer, EnumCustomerType> VALUES = new HashMap<Integer, EnumCustomerType>();

    static {
        for (EnumCustomerType type : EnumCustomerType.values()) {
            VALUES.put(type.value, type);
        }
    }

    EnumCustomerType(int enumValue) {
        this.value = enumValue;
    }

    public Integer getValue() {
        return value;
    }

    public static EnumCustomerType getEnum(Integer value) {
        if (value == null)
            return null;

        return VALUES.get(value.intValue());
    }
}
