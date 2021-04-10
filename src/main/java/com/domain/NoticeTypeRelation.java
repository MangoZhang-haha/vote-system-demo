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
@TableName(value = "notice_type_relation")
public class NoticeTypeRelation {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "notice_id")
    private Long noticeId;

    @TableField(value = "notice_type_id")
    private Long noticeTypeId;

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

    public static final String COL_NOTICE_TYPE_ID = "notice_type_id";

    public static final String COL_DELETED = "deleted";

    public static final String COL_GMT_CREATE = "gmt_create";

    public static final String COL_GMT_MODIFIED = "gmt_modified";
}