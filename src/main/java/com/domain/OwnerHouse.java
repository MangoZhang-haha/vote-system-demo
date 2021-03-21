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

@ApiModel(value="com-domain-OwnerHouse")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "owner_house")
public class OwnerHouse implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="主键")
    private Long id;

    /**
     * 业主ID
     */
    @TableField(value = "owner_id")
    @ApiModelProperty(value="业主ID")
    private Long ownerId;

    /**
     * 房屋ID
     */
    @TableField(value = "house_id")
    @ApiModelProperty(value="房屋ID")
    private Long houseId;

    /**
     * 居住类型
     */
    @TableField(value = "type_of_residence")
    @ApiModelProperty(value="居住类型")
    private Integer typeOfResidence;

    /**
     * 迁入原因
     */
    @TableField(value = "move_in_reason")
    @ApiModelProperty(value="迁入原因")
    private String moveInReason;

    /**
     * 迁入时间
     */
    @TableField(value = "move_in_time")
    @ApiModelProperty(value="迁入时间")
    private Date moveInTime;

    /**
     * 交房时间
     */
    @TableField(value = "delivery_time")
    @ApiModelProperty(value="交房时间")
    private Date deliveryTime;

    @TableField(value = "deleted")
    @ApiModelProperty(value="")
    private Boolean deleted;

    @TableField(value = "gmt_create")
    @ApiModelProperty(value="")
    private Date gmtCreate;

    @TableField(value = "gmt_modified")
    @ApiModelProperty(value="")
    private Date gmtModified;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_OWNER_ID = "owner_id";

    public static final String COL_HOUSE_ID = "house_id";

    public static final String COL_TYPE_OF_RESIDENCE = "type_of_residence";

    public static final String COL_MOVE_IN_REASON = "move_in_reason";

    public static final String COL_MOVE_IN_TIME = "move_in_time";

    public static final String COL_DELIVERY_TIME = "delivery_time";

    public static final String COL_DELETED = "deleted";

    public static final String COL_GMT_CREATE = "gmt_create";

    public static final String COL_GMT_MODIFIED = "gmt_modified";
}