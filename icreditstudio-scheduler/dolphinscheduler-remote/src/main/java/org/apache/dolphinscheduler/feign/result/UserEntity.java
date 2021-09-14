package org.apache.dolphinscheduler.feign.result;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;

/**
 * @author hzh
 */

@Data
public class UserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String ID = "ID";
    public static final String DELETE_FLAG = "DELETE_FLAG";
    public static final String USER_NAME = "USER_NAME";
    public static final String TEL_PHONE = "TEL_PHONE";
    public static final Integer SORT_NUMBER = 0;
    /**
     * id
     */
    private String id;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 工号
     */
    private String userCode;
    /**
     * 性别
     */
    private String userGender;

    /**
     * 生日
     */
    private String userBirth;
    /**
     * 身份证号
     */
    private String idCard;
    /**
     * 入职时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date entryTime;
    /**
     * 离职时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date departureTime;
    /**
     * 邮箱
     */
    private String eMail;
    /**
     * 职位
     */
    private String userPosition;
    /**
     * 联系方式
     */
    private String telPhone;
    /**
     * 排序字段
     */
    private Integer sortNumber;
    /**
     * 删除标志Y.已删除 N.未删除
     */
    //@ExcelProperty(value = "用户状态")
    private String deleteFlag;
    /**
     * 头像
     */
    private InputStream picturePath;

    private String photo = "";
    /**
     * 备注
     */
    @Length(max = 200, message = "用户备注超过最大长度")
    private String userRemark;
    /**
     * 创建时间
     */
    private Long createTime;
    /**
     * 创建者id
     */
    private String createUserId;
    /**
     * 更新时间
     */
    private Long lastUpdateTime;
    /**
     * 更新者id
     */
    private String lastUpdateUserId;
    /**
     * 扩展字段1
     */
    private String extendOne;
    /**
     * 扩展字段2
     */
    private String extendTwo;
    /**
     * 扩展字段3
     */
    private String extendThree;
    /**
     * 扩展字段4
     */
    private String extendFour;
    @TableField(exist = false)
    private String tempOrg;

    private String fontSize;

    private String cssId;

    private String layout;

    private String enableCustomMenu;
}
