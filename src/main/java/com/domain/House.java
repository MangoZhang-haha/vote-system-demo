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

@ApiModel(value = "com-domain-House")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "house")
public class House implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 房屋编号(H + id, id不满9位补0)
     */
    @ApiModelProperty(value = "房屋编号(H + id, id不满9位补0)")
    private String hno;

    /**
     * 门牌号
     */
    @TableField(value = "house_number")
    @ApiModelProperty(value = "门牌号")
    private String houseNumber;

    /**
     * 房屋面积
     */
    @TableField(value = "area_house")
    @ApiModelProperty(value = "房屋面积")
    private Double areaHouse;

    /**
     * 所在楼层
     */
    @TableField(value = "floor")
    @ApiModelProperty(value = "所在楼层")
    private Integer floor;

    /**
     * 房屋类型
     */
    @TableField(value = "type")
    @ApiModelProperty(value = "房屋类型")
    private Integer type;

    /**
     * 产权年限
     */
    @TableField(value = "term_of_property_right")
    @ApiModelProperty(value = "产权年限")
    private String termOfPropertyRight;

    /**
     * 投票权数
     */
    @TableField(value = "vote_weight")
    @ApiModelProperty(value = "投票权数")
    private Integer voteWeight;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;

    @TableField(value = "deleted")
    @ApiModelProperty(value = "")
    private Boolean deleted;

    @TableField(value = "gmt_create")
    @ApiModelProperty(value = "")
    private Date gmtCreate;

    @TableField(value = "gmt_modified")
    @ApiModelProperty(value = "")
    private Date gmtModified;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_HNO = "hno";

    public static final String COL_HOUSE_NUMBER = "house_number";

    public static final String COL_AREA_HOUSE = "area_house";

    public static final String COL_FLOOR = "floor";

    public static final String COL_TYPE = "type";

    public static final String COL_TERM_OF_PROPERTY_RIGHT = "term_of_property_right";

    public static final String COL_VOTE_WEIGHT = "vote_weight";

    public static final String COL_REMARK = "remark";

    public static final String COL_DELETED = "deleted";

    public static final String COL_GMT_CREATE = "gmt_create";

    public static final String COL_GMT_MODIFIED = "gmt_modified";
}