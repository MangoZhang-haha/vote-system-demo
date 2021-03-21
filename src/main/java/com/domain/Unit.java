package com.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value="com-domain-Unit")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "unit")
public class Unit implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="主键")
    private Long id;

    /**
     * 楼宇id
     */
    @TableField(value = "building_id")
    @ApiModelProperty(value="楼宇id")
    private Long buildingId;

    /**
     * 单元号 (1    2 )
     */
    @TableField(value = "unit")
    @ApiModelProperty(value="单元号 (1    2 )")
    private Integer unit;

    /**
     * 单元名称(一单元    二单元)
     */
    @TableField(value = "unit_name")
    @ApiModelProperty(value="单元名称(一单元    二单元)")
    private String unitName;

    /**
     * 该单元层数
     */
    @TableField(value = "floors")
    @ApiModelProperty(value="该单元层数")
    private Integer floors;

    /**
     * 逻辑删除 0未删除 1已删除
     */
    @TableField(value = "deleted")
    @ApiModelProperty(value="逻辑删除 0未删除 1已删除")
    private Boolean deleted;

    /**
     * 创建时间
     */
    @TableField(value = "gmt_create")
    @ApiModelProperty(value="创建时间")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @TableField(value = "gmt_modified")
    @ApiModelProperty(value="修改时间")
    private Date gmtModified;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_BUILDING_ID = "building_id";

    public static final String COL_UNIT = "unit";

    public static final String COL_UNIT_NAME = "unit_name";

    public static final String COL_FLOORS = "floors";

    public static final String COL_DELETED = "deleted";

    public static final String COL_GMT_CREATE = "gmt_create";

    public static final String COL_GMT_MODIFIED = "gmt_modified";
}