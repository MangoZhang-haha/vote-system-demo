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

@ApiModel(value = "com-domain-Services")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "services")
public class Services implements Serializable {
    public static final String COL_ICON__URL = "icon__url";
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 服务名称
     */
    @TableField(value = "service_name")
    @ApiModelProperty(value = "服务名称")
    private String serviceName;

    /**
     * 服务图标地址
     */
    @TableField(value = "icon_url")
    @ApiModelProperty(value = "服务图标地址")
    private String iconUrl;

    /**
     * 创建人id
     */
    @TableField(value = "creator_id")
    @ApiModelProperty(value = "创建人id")
    private Long creatorId;

    @TableField(value = "deleted")
    @ApiModelProperty(value = "")
    private Boolean deleted;

    @TableField(value = "gmt_create")
    @ApiModelProperty(value = "")
    private Date gmtCreate;

    @TableField(value = "gmt_modified")
    @ApiModelProperty(value = "")
    private Date gmtModified;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_SERVICE_NAME = "service_name";

    public static final String COL_ICON_URL = "icon_url";

    public static final String COL_CREATOR_ID = "creator_id";

    public static final String COL_DELETED = "deleted";

    public static final String COL_GMT_CREATE = "gmt_create";

    public static final String COL_GMT_MODIFIED = "gmt_modified";
}