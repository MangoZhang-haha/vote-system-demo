package com.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel(value = "com-domain-Notice")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "notice")
public class Notice implements Serializable {
    public static final String COL_DELETE = "delete";
    public static final String COL_TYPE = "type";
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 公告编号(A + id, id不满9位补0)
     */
    @TableField(value = "ano")
    private String ano;

    /**
     * 公告标题
     */
    @TableField(value = "title")
    private String title;

    /**
     * 公告内容
     */
    @TableField(value = "content")
    private String content;

    /**
     * 图片url
     */
    @TableField(value = "pic_url")
    private String picUrl;

    /**
     * 创建人id
     */
    @TableField(value = "creator_id")
    private Long creatorId;

    /**
     * 0:未审核， 1：审核通过 ，  2：审核不通过
     */
    @TableField(value = "approved")
    private Integer approved;

    /**
     * 审核员id
     */
    @TableField(value = "operator_id")
    private Long operatorId;

    /**
     * 审核时间
     */
    @TableField(value = "operate_time")
    private Date operateTime;

    /**
     * 发布时间
     */
    @TableField(value = "publish_time")
    private Date publishTime;

    @TableField(exist = false)
    private Long typeID;

    @TableField(exist = false)
    private String typeName;

    @TableField(value = "deleted")
    @TableLogic
    private Boolean deleted;

    @TableField(value = "gmt_create",fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private Date gmtCreate;

    @TableField(value = "gmt_modified",fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private Date gmtModified;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_ANO = "ano";

    public static final String COL_TITLE = "title";

    public static final String COL_CONTENT = "content";

    public static final String COL_PIC_URL = "pic_url";

    public static final String COL_CREATOR_ID = "creator_id";

    public static final String COL_APPROVED = "approved";

    public static final String COL_OPERATOR_ID = "operator_id";

    public static final String COL_OPERATE_TIME = "operate_time";

    public static final String COL_PUBLISH_TIME = "publish_time";

    public static final String COL_DELETED = "deleted";

    public static final String COL_GMT_CREATE = "gmt_create";

    public static final String COL_GMT_MODIFIED = "gmt_modified";
}