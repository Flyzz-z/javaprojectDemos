package xyz.flyzz.springsecurity.entity.dao;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("security_user")
public class UserDo {

    @TableId("id")
    private Long id;

    @TableField("user_name")
    private String userName;

    @TableField("password")
    private String password;

}
