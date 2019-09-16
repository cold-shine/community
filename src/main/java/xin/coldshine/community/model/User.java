package xin.coldshine.community.model;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long gmtCreater;
    private Long gmtModified;
    private String avatarUrl;


}
