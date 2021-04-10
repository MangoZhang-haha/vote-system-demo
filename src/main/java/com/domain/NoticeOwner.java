package com.domain;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "notice_owner")
public class NoticeOwner {
    @TableId(value = "id", type = IdType.INPUT)
    private Long id;

    @TableField(value = "notice_id")
    private Long noticeId;

    @TableField(value = "owner_id")
    private Long ownerId;

    /**
     *  0 未读     1 已读
     */
    @TableField(value = "status")
    private Integer status;

    @TableField(value = "deleted")
    @TableLogic
    private Boolean deleted;

    @TableField(value = "gmt_create",fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private Date gmtCreate;

    @TableField(value = "gmt_modified",fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss" , timezone = "GMT+8")
    private Date gmtModified;

    public static final String COL_ID = "id";

    public static final String COL_NOTICE_ID = "notice_id";

    public static final String COL_OWNER_ID = "owner_id";

    public static final String COL_STATUS = "status";

    public static final String COL_DELETED = "deleted";

    public static final String COL_GMT_CREATE = "gmt_create";

    public static final String COL_GMT_MODIFIED = "gmt_modified";
}