package dev.nurlan.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespStatus {

    private Integer statusCode;
    private String statusMessage;

    private static final Integer SUCCESS_CODE = 1;
    private static final String SUCCES_MESSAGE = "success";

    public static RespStatus getSuccessMessage() {
        return new RespStatus(SUCCESS_CODE, SUCCES_MESSAGE);
    }

}
