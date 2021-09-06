package xyz.flyzz.securityjwt.pojo.dao;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigInteger;

@Data
@TableName("demo2_user")
public class UserDO {

    @TableId("id")
    BigInteger id;

    @TableField("user_name")
    String userName;

    @TableField("password")
    String password;
    /*
    *  与数据库中user各字段对应
    *
    * */
}
