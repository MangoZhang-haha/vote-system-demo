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

@ApiModel(value = "com-domain-Community")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "community")
public class Community implements Serializable {
    public static final String COL_CUSTOMER_SERVICE = "customer_service";
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 小区编号(C + ID，ID不满6位补0)
     */
    @ApiModelProperty(value = "小区编号(C + ID，ID不满6位补0)")
    private String cno;

    /**
     * 小区名称
     */
    @TableField(value = "community_name")
    @ApiModelProperty(value = "小区名称")
    private String communityName;

    /**
     * 所在地区(省 市 县)
     */
    @TableField(value = "region")
    @ApiModelProperty(value = "所在地区(省 市 县)")
    private String region;

    /**
     * 详细地址
     */
    @TableField(value = "address_detail")
    @ApiModelProperty(value = "详细地址")
    private String addressDetail;

    /**
     * 占地面积
     */
    @TableField(value = "area_covered")
    @ApiModelProperty(value = "占地面积")
    private Double areaCovered;

    /**
     * 建筑面积
     */
    @TableField(value = "floorage")
    @ApiModelProperty(value = "建筑面积")
    private Double floorage;

    /**
     * 公共场所面积
     */
    @TableField(value = "area_public")
    @ApiModelProperty(value = "公共场所面积")
    private Double areaPublic;

    /**
     * 绿化面积
     */
    @TableField(value = "area_afforested")
    @ApiModelProperty(value = "绿化面积")
    private Double areaAfforested;

    /**
     * 楼宇数量
     */
    @TableField(value = "buildings")
    @ApiModelProperty(value = "楼宇数量")
    private Integer buildings;

    /**
     * 客服电话
     */
    @TableField(value = "customer_service_phone")
    @ApiModelProperty(value = "客服电话")
    private String customerServicePhone;

    /**
     * 负责人姓名
     */
    @TableField(value = "person_in_charge_name")
    @ApiModelProperty(value = "负责人姓名")
    private String personInChargeName;

    /**
     * 负责人电话
     */
    @TableField(value = "person_in_charge_phone")
    @ApiModelProperty(value = "负责人电话")
    private String personInChargePhone;

    /**
     * 备注
     */
    @TableField(value = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;

    /**
     * 小区背景图url
     */
    @TableField(value = "background_image_url")
    @ApiModelProperty(value = "小区背景图url")
    private String backgroundImageUrl;

    /**
     * 小区logo url
     */
    @TableField(value = "logo_url")
    @ApiModelProperty(value = "小区logo url")
    private String logoUrl;

    /**
     * 逻辑删除状态 0未删除 1已删除
     */
    @TableField(value = "deleted")
    @ApiModelProperty(value = "逻辑删除状态 0未删除 1已删除")
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

    public static final String COL_CNO = "cno";

    public static final String COL_COMMUNITY_NAME = "community_name";

    public static final String COL_REGION = "region";

    public static final String COL_ADDRESS_DETAIL = "address_detail";

    public static final String COL_AREA_COVERED = "area_covered";

    public static final String COL_FLOORAGE = "floorage";

    public static final String COL_AREA_PUBLIC = "area_public";

    public static final String COL_AREA_AFFORESTED = "area_afforested";

    public static final String COL_BUILDINGS = "buildings";

    public static final String COL_CUSTOMER_SERVICE_PHONE = "customer_service_phone";

    public static final String COL_PERSON_IN_CHARGE_NAME = "person_in_charge_name";

    public static final String COL_PERSON_IN_CHARGE_PHONE = "person_in_charge_phone";

    public static final String COL_REMARK = "remark";

    public static final String COL_BACKGROUND_IMAGE_URL = "background_image_url";

    public static final String COL_LOGO_URL = "logo_url";

    public static final String COL_DELETED = "deleted";

    public static final String COL_GMT_CREATE = "gmt_create";

    public static final String COL_GMT_MODIFIED = "gmt_modified";
}