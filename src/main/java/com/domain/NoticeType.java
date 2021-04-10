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
@TableName(value = "notice_type")
public class NoticeType {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 投票类型名称
     */
    @TableField(value = "type_name")
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

    public static final String COL_ID = "id";

    public static final String COL_TYPE_NAME = "type_name";

    public static final String COL_DELETED = "deleted";

    public static final String COL_GMT_CREATE = "gmt_create";

    public static final String COL_GMT_MODIFIED = "gmt_modified";
}