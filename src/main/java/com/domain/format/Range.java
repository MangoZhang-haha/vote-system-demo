package com.domain.format;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Mango
 * @Date: 2021/3/23 15:11:26
 */
@Data
public class Range implements Serializable {

    private static final long serialVersionUID = -6263678427157831929L;

    private List<CommunityInfo> range;
}
