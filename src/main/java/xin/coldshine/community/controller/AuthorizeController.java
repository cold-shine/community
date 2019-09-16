package xin.coldshine.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xin.coldshine.community.dto.AccessTokenDTO;
import xin.coldshine.community.dto.GithubUser;
import xin.coldshine.community.mapper.UserMapper;
import xin.coldshine.community.model.User;
import xin.coldshine.community.provider.GithubProvider;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * @author cold
 */
@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @Autowired
    private UserMapper userMapper;

    @Value("${github.client.id}")
    private String clientId;
    @Value("${github.client.secret}")
    private String clientSecret;
    @Value("${github.redirect.uri}")
    private String clientRedirectUri;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletResponse response) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientId);
        accessTokenDTO.setClient_secret(clientSecret);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(clientRedirectUri);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubuser = githubProvider.getUser(accessToken);
        if (githubuser != null) {
            //登录成功,写 cookie 和 session
            User user = new User();
            String token = UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(githubuser.getName());
            user.setAccountId(String.valueOf(githubuser.getId()));
            user.setGmtCreater(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreater());
            user.setAvatarUrl(githubuser.getAvatar_url());
            userMapper.insert(user);
            response.addCookie(new Cookie("token",token));
            return "redirect:/";
        } else {
            //登录失败,重新登录
            return "redirect:/";
        }
    }
}