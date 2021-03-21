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

@ApiModel(value="com-domain-UnitHouse")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "unit_house")
public class UnitHouse implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="主键")
    private Long id;

    /**
     * 单元表ID
     */
    @TableField(value = "unit_table_id")
    @ApiModelProperty(value="单元表ID")
    private Long unitTableId;

    /**
     * 房屋ID
     */
    @TableField(value = "house_id")
    @ApiModelProperty(value="房屋ID")
    private Long houseId;

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

    public static final String COL_UNIT_TABLE_ID = "unit_table_id";

    public static final String COL_HOUSE_ID = "house_id";

    public static final String COL_DELETED = "deleted";

    public static final String COL_GMT_CREATE = "gmt_create";

    public static final String COL_GMT_MODIFIED = "gmt_modified";
}