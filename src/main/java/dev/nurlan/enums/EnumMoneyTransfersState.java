package dev.nurlan.enums;

import java.util.HashMap;
import java.util.Map;

public enum EnumMoneyTransfersState {

    ERROR(0),

    SUCCESS(1),

    RETRY(2),

    ACCEPT(3),

    REVERSE(4);


    private int value;

    private static final Map<Integer, EnumMoneyTransfersState> VALUES = new HashMap<Integer, EnumMoneyTransfersState>();

    static {
        for (EnumMoneyTransfersState type : EnumMoneyTransfersState.values()) {
            VALUES.put(type.value, type);
        }
    }

    EnumMoneyTransfersState(int enumValue) {
        this.value = enumValue;
    }

    public Integer getValue() {
        return value;
    }

    public static EnumMoneyTransfersState getEnum(Integer value) {
        if (value == null)
            return null;

        return VALUES.get(value.intValue());
    }
}
