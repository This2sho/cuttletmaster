package PorkCutlet.master;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.util.AntPathMatcher;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.*;

public class AntiPathMatchTest {
    AntPathMatcher pathMatcher = new AntPathMatcher();
    @Test
    public void AntiPathMatchTest() throws Exception {
        //given
        String pattern = "/reviews/{spring:^[0-9]*$}/comments";
        //when
        String url = "/reviews/1423432/comments";
        //then
        assertThat(pathMatcher.match(pattern, url)).isTrue();
    }

    @Test
    public void passwordCheck() throws Exception {
        //given
        Pattern passPattern = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,16}$");
        String password = "134a9bc;dl";

        //when
        Matcher matcher = passPattern.matcher(password);

        //then
        assertThat(matcher.matches()).isTrue();
    }

    @Test
    public void 닉네임체크() throws Exception {
        //given
        Pattern nickPattern = Pattern.compile("^[가-힣ㄱ-ㅎa-zA-Z0-9]{2,12}$");
        String nickName = "1키쿠as012";

        //when
        Matcher matcher = nickPattern.matcher(nickName);

        //then
        assertThat(matcher.matches()).isTrue();
    }
}
