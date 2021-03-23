package com.domain.format;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Mango
 * @Date: 2021/3/23 15:16:22
 */
@Data
public class UnitInfo implements Serializable {

    private static final long serialVersionUID = 7071247865133785937L;

    private Long unitTableID;

    private String name;

    private Boolean checked = false;

    private Integer peopleNum = 0;

    private List<HouseInfo> children;
}
