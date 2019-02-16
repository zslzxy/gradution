package com.zsl.common.entity;

import com.zsl.common.myenum.Level;
import com.zsl.common.myenum.State;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.checkerframework.checker.fenum.qual.Fenum;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "mall_user",type = "user")
public class User implements Serializable {

    private static final long serialVersionUID = -5809782578272943999L;

    /**
     * 主键，自增，不用于业务逻辑
     */
    @Id
    private Integer id;

    /**
     * 用户表的唯一索引
     */
    @Field(type = FieldType.Keyword)
    private String userId;

    /**
     * 用户名
     */
    @Field(type = FieldType.Text, searchAnalyzer = "ik_max_word", analyzer = "ik_max_word")
    private String userName;

    /**
     * 用户别名
     */
    @Field(type = FieldType.Text, searchAnalyzer = "ik_max_word", analyzer = "ik_max_word")
    private String userAliasName;

    /**
     * 用户密码
     */
    @Field(type = FieldType.Keyword, ignoreFields = "userPassword", index = false)
    private String userPassword;

    /**
     * 用户性别
     */
    @Field(type = FieldType.Keyword, index = false)
    private String userSex;

    /**
     * 用户身份证号
     */
    @Field(type = FieldType.Keyword, searchAnalyzer = "ik_max_word", analyzer = "ik_max_word")
    private String userCardNo;

    /**
     * 用户邮箱
     */
    @Field(type = FieldType.Keyword)
    private String userEmail;

    /**
     * 用户电话号码
     */
    @Field(type = FieldType.Keyword)
    private BigInteger userMobileNo;

    /**
     * 用户的积分
     */
    @Field(type = FieldType.Keyword)
    private Integer userIntegral;

    /**
     * 用户账户剩余的余额
     */
    @Field(type = FieldType.Keyword)
    private BigDecimal userAmount;

    /**
     * 用户的生日
     */
    @Field(type = FieldType.Date,format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
    @DateTimeFormat(pattern= "yyyy-MM-dd HH:mm:ss")
    private Date userBirth;

    /**
     * 用户的头像
     */
    @Field(type = FieldType.Keyword, index = false)
    private String userHeadImage;

    /**
     * 用户的信誉，三个等级，优（正常购物）、中（无法参与秒杀）、差（无法登陆）
     */
    @Field(type = FieldType.Keyword)
    private State userState;

    /**
     * 用户的住址
     */
    @Field(type = FieldType.Text, searchAnalyzer = "ik_max_word", analyzer = "ik_max_word")
    private String userAddress;

    /**
     * 用户的等级
     */
    @Field(type = FieldType.Keyword)
    private Level userLevel;

    /**
     * 新增时间
     */
    @Field(type = FieldType.Date, index = false,format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
    private Date rawAddTime;

    /**
     * 修改时间
     */
    @Field(type = FieldType.Date, index = false,format = DateFormat.custom,pattern = "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd||epoch_millis")
    private Date rawUpdateTime;


}