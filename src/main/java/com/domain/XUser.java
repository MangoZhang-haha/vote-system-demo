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

/**
 * 用户表
 */
@ApiModel(value = "com-domain-XUser")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "x_user")
public class XUser implements Serializable {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "主键")
    private Long id;

    /**
     * 用户名
     */
    @TableField(value = "username")
    @ApiModelProperty(value = "用户名")
    private String username;

    /**
     * 密码
     */
    @TableField(value = "password")
    @ApiModelProperty(value = "密码")
    private String password;

    /**
     * 面部识别id
     */
    @TableField(value = "face_id")
    @ApiModelProperty(value = "面部识别id")
    private String faceId;

    /**
     * 手机号
     */
    @TableField(value = "phone")
    @ApiModelProperty(value = "手机号")
    private String phone;

    /**
     * 身份证号
     */
    @TableField(value = "id_number")
    @ApiModelProperty(value = "身份证号")
    private String idNumber;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    @ApiModelProperty(value = "邮箱")
    private String email;

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
     * 真实姓名
     */
    @TableField(value = "real_name")
    @ApiModelProperty(value = "真实姓名")
    private String realName;

    /**
     * 登录次数
     */
    @TableField(value = "login_times")
    @ApiModelProperty(value = "登录次数")
    private Long loginTimes;

    /**
     * 上次登录时间
     */
    @TableField(value = "lastest_login_time")
    @ApiModelProperty(value = "上次登录时间")
    private Date lastestLoginTime;

    /**
     * 头像地址
     */
    @TableField(value = "avatar")
    @ApiModelProperty(value = "头像地址")
    private String avatar;

    /**
     * 盐值
     */
    @TableField(value = "salty")
    @ApiModelProperty(value = "盐值")
    private Integer salty;

    /**
     * 账号状态
     */
    @TableField(value = "active")
    @ApiModelProperty(value = "账号状态")
    private Boolean active;

    /**
     * 已删除
     */
    @TableField(value = "deleted")
    @ApiModelProperty(value = "已删除")
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

    public static final String COL_USERNAME = "username";

    public static final String COL_PASSWORD = "password";

    public static final String COL_FACE_ID = "face_id";

    public static final String COL_PHONE = "phone";

    public static final String COL_ID_NUMBER = "id_number";

    public static final String COL_EMAIL = "email";

    public static final String COL_QQ_NUMBER = "qq_number";

    public static final String COL_WECHAT_NUMBER = "wechat_number";

    public static final String COL_REAL_NAME = "real_name";

    public static final String COL_LOGIN_TIMES = "login_times";

    public static final String COL_LASTEST_LOGIN_TIME = "lastest_login_time";

    public static final String COL_AVATAR = "avatar";

    public static final String COL_SALTY = "salty";

    public static final String COL_ACTIVE = "active";

    public static final String COL_DELETED = "deleted";

    public static final String COL_GMT_CREATE = "gmt_create";

    public static final String COL_GMT_MODIFIED = "gmt_modified";
}