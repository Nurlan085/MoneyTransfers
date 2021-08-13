package dev.nurlan.model;

import lombok.Data;

@Data
public class Customer extends AbstractModel {

    private String name;
    private String surname;
    private String fname;
    private String mobile;
    private String address;
    private Integer genderId;
    private Integer custTypeId;
    private Integer documentTypeId;

}
