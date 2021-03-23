package com.domain.format;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Mango
 * @Date: 2021/3/23 15:36:42
 */
@Data
public class HouseInfo implements Serializable {

    private static final long serialVersionUID = 8948557445367913295L;

    private Long id;

    private String name;

    private Boolean checked = false;

    private Long ownerID;
}
