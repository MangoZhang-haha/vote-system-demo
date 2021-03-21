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

@ApiModel(value="com-domain-VoteType")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "vote_type")
public class VoteType implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="主键")
    private Long id;

    /**
     * 投票类型名称
     */
    @TableField(value = "vote_type_name")
    @ApiModelProperty(value="投票类型名称")
    private String voteTypeName;

    /**
     * 投票类型标识
     */
    @TableField(value = "type")
    @ApiModelProperty(value="投票类型标识")
    private Integer type;

    /**
     * 是否启用
     */
    @TableField(value = "whether_active")
    @ApiModelProperty(value="是否启用")
    private Boolean whetherActive;

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

    public static final String COL_VOTE_TYPE_NAME = "vote_type_name";

    public static final String COL_TYPE = "type";

    public static final String COL_WHETHER_ACTIVE = "whether_active";

    public static final String COL_DELETED = "deleted";

    public static final String COL_GMT_CREATE = "gmt_create";

    public static final String COL_GMT_MODIFIED = "gmt_modified";
}