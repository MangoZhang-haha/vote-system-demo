package com.domain.format;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Mango
 * @Date: 2021/3/23 15:13:05
 */
@Data
public class CommunityInfo implements Serializable {

    private static final long serialVersionUID = 9139208768268032323L;

    private Long id;

    private String name;

    private Boolean checked = false;

    private Integer peopleNum = 0;

    private List<BuildingInfo> children;
}
