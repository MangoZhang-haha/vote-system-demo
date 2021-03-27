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

@ApiModel(value = "com-domain-Vote")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "vote")
public class Vote implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 创建人id
     */
    @TableField(value = "creator_id")
    @ApiModelProperty(value = "创建人id")
    private Long creatorId;

    /**
     * 标题
     */
    @TableField(value = "title")
    @ApiModelProperty(value = "标题")
    private String title;

    /**
     * 介绍
     */
    @TableField(value = "introduce")
    @ApiModelProperty(value = "介绍")
    private String introduce;

    /**
     * 投票限制id
     */
    @TableField(value = "vote_limit_id")
    @ApiModelProperty(value = "投票限制id")
    private Long voteLimitId;

    /**
     * 投票类型id
     */
    @TableField(value = "vote_type_id")
    @ApiModelProperty(value = "投票类型id")
    private Long voteTypeId;

    /**
     * 开始时间
     */
    @TableField(value = "start_time")
    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    /**
     * 结束时间
     */
    @TableField(value = "end_time")
    @ApiModelProperty(value = "结束时间")
    private Date endTime;

    /**
     * 是否匿名(0 不匿名 1 匿名)
     */
    @TableField(value = "whether_anonym")
    @ApiModelProperty(value = "是否匿名(0 不匿名 1 匿名)")
    private Boolean whetherAnonym;

    /**
     * 是否可以委托亲友投票
     */
    @TableField(value = "whether_replace_by_relatives")
    @ApiModelProperty(value = "是否可以委托亲友投票")
    private Boolean whetherReplaceByRelatives;

    /**
     * 是否是草稿
     */
    @TableField(value = "whether_draft")
    @ApiModelProperty(value = "是否是草稿")
    private Boolean whetherDraft;

    /**
     * 被通知的业主的id（以 "," 分割）
     */
    @TableField(value = "owner_noticed_ids")
    @ApiModelProperty(value = "被通知的业主的id（以 ',' 分割）")
    private String ownerNoticedIds;

    /**
     * 投票是否截止
     */
    @TableField(exist = false)
    @ApiModelProperty(value = "投票是否截止")
    private Boolean endOrNot;

    /**
     * 访问量
     */
    @TableField(value = "visit_num")
    @ApiModelProperty(value = "访问量")
    private Long visitNum;

    @TableField(value = "deleted")
    @ApiModelProperty(value = "")
    private Boolean deleted;

    @TableField(value = "gmt_create", fill = FieldFill.INSERT)
    @ApiModelProperty(value = "")
    private Date gmtCreate;

    @TableField(value = "gmt_modified", fill = FieldFill.INSERT_UPDATE)
    @ApiModelProperty(value = "")
    private Date gmtModified;

    /**
     * 投票的数量
     */
    @TableField(exist = false)
    private Integer voteNumbers;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_CREATOR_ID = "creator_id";

    public static final String COL_TITLE = "title";

    public static final String COL_INTRODUCE = "introduce";

    public static final String COL_VOTE_LIMIT_ID = "vote_limit_id";

    public static final String COL_VOTE_TYPE_ID = "vote_type_id";

    public static final String COL_START_TIME = "start_time";

    public static final String COL_END_TIME = "end_time";

    public static final String COL_WHETHER_ANONYM = "whether_anonym";

    public static final String COL_WHETHER_REPLACE_BY_RELATIVES = "whether_replace_by_relatives";

    public static final String COL_WHETHER_DRAFT = "whether_draft";

    public static final String COL_OWNER_NOTICED_IDS = "owner_noticed_ids";

    public static final String COL_VISIT_NUM = "visit_num";

    public static final String COL_DELETED = "deleted";

    public static final String COL_GMT_CREATE = "gmt_create";

    public static final String COL_GMT_MODIFIED = "gmt_modified";
}