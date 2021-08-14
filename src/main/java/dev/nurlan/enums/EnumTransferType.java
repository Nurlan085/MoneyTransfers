package dev.nurlan.enums;

import java.util.HashMap;
import java.util.Map;

public enum EnumTransferType {

    CARD_TO_CARD(1),

    CARD_TO_NO_ACCOUNT(2);


    private int value;

    private static final Map<Integer, EnumTransferType> VALUES = new HashMap<>();

    static {
        for (EnumTransferType type : EnumTransferType.values()) {
            VALUES.put(type.value, type);
        }
    }

    EnumTransferType(int enumValue) {
        this.value = enumValue;
    }

    public Integer getValue() {
        return value;
    }

    public static EnumTransferType getEnum(Integer value) {
        if (value == null)
            return null;

        return VALUES.get(value.intValue());
    }
}
