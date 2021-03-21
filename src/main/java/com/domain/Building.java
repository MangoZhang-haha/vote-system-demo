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

@ApiModel(value="com-domain-Building")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "building")
public class Building implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="主键")
    private Long id;

    /**
     * 楼宇编号(B + id, id不满6位补0)
     */
    @ApiModelProperty(value="楼宇编号(B + id, id不满6位补0)")
    private String bno;

    /**
     * 楼宇名称 ( 1# 8#)
     */
    @TableField(value = "building_name")
    @ApiModelProperty(value="楼宇名称 ( 1# 8#)")
    private String buildingName;

    /**
     * 楼宇类型
     */
    @TableField(value = "type")
    @ApiModelProperty(value="楼宇类型")
    private Integer type;

    /**
     * 单元数量
     */
    @TableField(value = "units")
    @ApiModelProperty(value="单元数量")
    private Integer units;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value="备注")
    private String remark;

    /**
     * 逻辑删除状态 0未删除 1 已删除
     */
    @TableField(value = "deleted")
    @ApiModelProperty(value="逻辑删除状态 0未删除 1 已删除")
    private Boolean deleted;

    /**
     * 创建修改时间
     */
    @TableField(value = "gmt_create")
    @ApiModelProperty(value="创建修改时间")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @TableField(value = "gmt_modified")
    @ApiModelProperty(value="修改时间")
    private Date gmtModified;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_BNO = "bno";

    public static final String COL_BUILDING_NAME = "building_name";

    public static final String COL_TYPE = "type";

    public static final String COL_UNITS = "units";

    public static final String COL_REMARK = "remark";

    public static final String COL_DELETED = "deleted";

    public static final String COL_GMT_CREATE = "gmt_create";

    public static final String COL_GMT_MODIFIED = "gmt_modified";
}