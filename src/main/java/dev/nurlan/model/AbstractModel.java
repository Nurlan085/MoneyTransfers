package dev.nurlan.model;

import lombok.Data;

import java.util.Date;

@Data
abstract class AbstractModel {

    private Long id;
    private Date dataDate;
    private Integer active;
}
