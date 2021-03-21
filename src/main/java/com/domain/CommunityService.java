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

@ApiModel(value="com-domain-CommunityService")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "community_service")
public class CommunityService implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value="主键")
    private Long id;

    /**
     * 小区id
     */
    @TableField(value = "community_id")
    @ApiModelProperty(value="小区id")
    private Long communityId;

    /**
     * 服务id
     */
    @TableField(value = "service_id")
    @ApiModelProperty(value="服务id")
    private Long serviceId;

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

    public static final String COL_COMMUNITY_ID = "community_id";

    public static final String COL_SERVICE_ID = "service_id";

    public static final String COL_DELETED = "deleted";

    public static final String COL_GMT_CREATE = "gmt_create";

    public static final String COL_GMT_MODIFIED = "gmt_modified";
}