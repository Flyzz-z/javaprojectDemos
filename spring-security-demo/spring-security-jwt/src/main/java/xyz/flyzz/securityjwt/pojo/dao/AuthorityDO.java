package xyz.flyzz.securityjwt.pojo.dao;


import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigInteger;

@Data
@TableName("demo2_authority")
public class AuthorityDO {
    @TableId("id")
    BigInteger id;

    @TableField("name")
    String name;
}
