package com.domain;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value="com-domain-VoteRecords")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "vote_records")
public class VoteRecords implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="主键")
    private Long id;

    /**
     * 投票id
     */
    @TableField(value = "vote_id")
    @ApiModelProperty(value="投票id")
    private Long voteId;

    /**
     * 投票人id
     */
    @TableField(value = "owner_id")
    @ApiModelProperty(value="投票人id")
    private Long ownerId;

    /**
     * 候选人表id
     */
    @TableField(value = "vote_candidate_table_id")
    @ApiModelProperty(value="候选人表id")
    private Long voteCandidateTableId;

    @TableField(value = "deleted")
    @ApiModelProperty(value="")
    private Boolean deleted;

    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
    @ApiModelProperty(value="")
    private Date gmtCreate;

    @TableField(value = "gmt_modified", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value="")
    private Date gmtModified;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_VOTE_ID = "vote_id";

    public static final String COL_OWNER_ID = "owner_id";

    public static final String COL_VOTE_CANDIDATE_TABLE_ID = "vote_candidate_table_id";

    public static final String COL_DELETED = "deleted";

    public static final String COL_GMT_CREATE = "gmt_create";

    public static final String COL_GMT_MODIFIED = "gmt_modified";
}