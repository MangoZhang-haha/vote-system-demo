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

@ApiModel(value = "com-domain-Owner")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "`owner`")
public class Owner implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 业主id，是业主oid为0
     */
    @TableField(value = "oid")
    @ApiModelProperty(value = "业主id，是业主oid为0")
    private Long oid;

    /**
     * 人脸识别标识
     */
    @TableField(value = "face_id")
    @ApiModelProperty(value = "人脸识别标识")
    private String faceId;

    /**
     * 姓名
     */
    @TableField(value = "owner_name")
    @ApiModelProperty(value = "姓名")
    private String ownerName;

    /**
     * 0 女 1 男
     */
    @TableField(value = "gender")
    @ApiModelProperty(value = "0 女 1 男")
    private String gender;

    /**
     * 手机号
     */
    @TableField(value = "mobilephone")
    @ApiModelProperty(value = "手机号")
    private String mobilephone;

    /**
     * 家庭电话
     */
    @TableField(value = "telephone")
    @ApiModelProperty(value = "家庭电话")
    private byte[] telephone;

    /**
     * 户口类型
     */
    @TableField(value = "account_type")
    @ApiModelProperty(value = "户口类型")
    private Integer accountType;

    /**
     * 多个卡号以 "|" 分割
     */
    @TableField(value = "bank_cards")
    @ApiModelProperty(value = "多个卡号以 '|' 分割")
    private String bankCards;

    /**
     * 最近登陆时间
     */
    @TableField(value = "lastest_login_time")
    @ApiModelProperty(value = "最近登陆时间")
    private Date lastestLoginTime;

    /**
     * 登陆次数
     */
    @TableField(value = "login_times")
    @ApiModelProperty(value = "登陆次数")
    private Long loginTimes;

    /**
     * QQ号
     */
    @TableField(value = "qq_number")
    @ApiModelProperty(value = "QQ号")
    private String qqNumber;

    /**
     * 微信号
     */
    @TableField(value = "wechat_number")
    @ApiModelProperty(value = "微信号")
    private String wechatNumber;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    @ApiModelProperty(value = "邮箱")
    private String email;

    /**
     * 身份证号
     */
    @TableField(value = "id_number")
    @ApiModelProperty(value = "身份证号")
    private String idNumber;

    /**
     * 角色类型（1 业主 2 业主委员）
     */
    @TableField(value = "role_type")
    @ApiModelProperty(value = "角色类型（1 业主 2 业主委员）")
    private Integer roleType;

    /**
     * 头像地址
     */
    @TableField(value = "avatar_url")
    @ApiModelProperty(value = "头像地址")
    private String avatarUrl;

    /**
     * 审核通过
     */
    @ApiModelProperty(value = "审核通过")
    @TableField(value = "approved")
    private Integer approved;

    /**
     * 审核操作人id
     */
    @ApiModelProperty(value = "审核操作人id")
    @TableField(value = "operator_id")
    private Long operatorId;

    /**
     * 账号封停状态(0 审核中 1 审核通过 2 审核未通过)
     */
    @TableField(value = "whether_active")
    @ApiModelProperty(value = "账号封停状态(0 审核中 1 审核通过 2 审核未通过)")
    private Boolean whetherActive;

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

    public static final String COL_OID = "oid";

    public static final String COL_FACE_ID = "face_id";

    public static final String COL_OWNER_NAME = "owner_name";

    public static final String COL_GENDER = "gender";

    public static final String COL_MOBILEPHONE = "mobilephone";

    public static final String COL_TELEPHONE = "telephone";

    public static final String COL_ACCOUNT_TYPE = "account_type";

    public static final String COL_BANK_CARDS = "bank_cards";

    public static final String COL_LASTEST_LOGIN_TIME = "lastest_login_time";

    public static final String COL_LOGIN_TIMES = "login_times";

    public static final String COL_QQ_NUMBER = "qq_number";

    public static final String COL_WECHAT_NUMBER = "wechat_number";

    public static final String COL_EMAIL = "email";

    public static final String COL_ID_NUMBER = "id_number";

    public static final String COL_ROLE_TYPE = "role_type";

    public static final String COL_AVATAR_URL = "avatar_url";

    public static final String COL_APPROVED = "approved";

    public static final String COL_OPERATOR_ID = "operator_id";

    public static final String COL_WHETHER_ACTIVE = "whether_active";

    public static final String COL_DELETED = "deleted";

    public static final String COL_GMT_CREATE = "gmt_create";

    public static final String COL_GMT_MODIFIED = "gmt_modified";
}