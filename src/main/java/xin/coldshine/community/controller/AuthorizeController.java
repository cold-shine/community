package xin.coldshine.community.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import xin.coldshine.community.dto.AccessTokenDTO;
import xin.coldshine.community.dto.GithubUser;
import xin.coldshine.community.provider.GithubProvider;

/**
 * @author cold
 */
@Controller
public class AuthorizeController {
    @Autowired
    private GithubProvider githubProvider;

    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state) {
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id("89085e8c8de3ac01c978");
        accessTokenDTO.setClient_secret("4b536fe5ed4b2609db37b7fe1e1b33a4ce0927b0");
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri("http://localhost:8887/callback");
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser user = githubProvider.getUser(accessToken);
        System.out.println(user.getName());
        return "index";
    }
}