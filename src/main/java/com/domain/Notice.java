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
@TableName(value = "notice")
public class Notice {
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

    @TableField(exist = false)
    private Long typeID;

    @TableField(exist = false)
    private String typeName;

    /**
     * 图片url
     */
    @TableField(value = "pic_url")
    private String picUrl;

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

    public static final String COL_ANO = "ano";

    public static final String COL_TITLE = "title";

    public static final String COL_CONTENT = "content";

    public static final String COL_PIC_URL = "pic_url";

    public static final String COL_DELETED = "deleted";

    public static final String COL_GMT_CREATE = "gmt_create";

    public static final String COL_GMT_MODIFIED = "gmt_modified";
}