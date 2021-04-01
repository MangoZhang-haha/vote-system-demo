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

@ApiModel(value = "com-domain-VoteEv")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "vote_ev")
public class VoteEv implements Serializable {
    public static final String COL_APPLY_STATIS = "apply_statis";
    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键ID")
    private Long id;

    /**
     * 投票项目id
     */
    @TableField(value = "vote_id")
    @ApiModelProperty(value = "投票项目id")
    private Long voteId;

    /**
     * V+id（id不满9位补零）
     */
    @TableField(value = "application_id")
    @ApiModelProperty(value = "V+id（id不满9位补零）")
    private String applicationId;

    /**
     * 审核状态0 审核中 1 已通过 2 未通过
     */
    @TableField(value = "apply_status")
    @ApiModelProperty(value = "审核状态0 审核中 1 已通过 2 未通过")
    private Integer applyStatus;

    /**
     * 逻辑删除状态
     */
    @TableField(value = "deleted")
    @ApiModelProperty(value = "逻辑删除状态")
    private Boolean deleted;

    /**
     * 创建时间
     */
    @TableField(value = "gmt_create")
    @ApiModelProperty(value = "创建时间")
    private Date gmtCreate;

    /**
     * 修改时间
     */
    @TableField(value = "gmt_modified")
    @ApiModelProperty(value = "修改时间")
    private Date gmtModified;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_VOTE_ID = "vote_id";

    public static final String COL_APPLICATION_ID = "application_id";

    public static final String COL_APPLY_STATUS = "apply_status";

    public static final String COL_DELETED = "deleted";

    public static final String COL_GMT_CREATE = "gmt_create";

    public static final String COL_GMT_MODIFIED = "gmt_modified";
}