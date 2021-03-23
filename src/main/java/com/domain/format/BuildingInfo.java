package com.domain.format;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Mango
 * @Date: 2021/3/23 15:15:04
 */
@Data
public class BuildingInfo implements Serializable {

    private static final long serialVersionUID = 6679938764412776855L;

    private Long id;

    private String name;

    private Boolean checked = false;

    private Integer peopleNum = 0;

    private List<UnitInfo> children;
}
