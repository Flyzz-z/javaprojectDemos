package xyz.flyzz.securityjwt.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private BigInteger id;

    private String userName;

    /*
    *   其他需要数据
    *
     */
}
