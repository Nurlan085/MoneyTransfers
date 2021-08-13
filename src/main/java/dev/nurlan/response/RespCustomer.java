package dev.nurlan.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RespCustomer {

    @JsonProperty(value = "custId")
    private Long id;
    private String name;
    private String surname;
    private String fname;
    private String mobile;
    private String address;
    private Integer genderId;
    private Integer custTypeId;
    private Integer documentTypeId;
    private RespStatus status;
}
