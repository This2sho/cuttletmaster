package PorkCutlet.master;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;

@Component
public class UserPatternUtil {
	Pattern passwordPattern = Pattern.compile("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*\\W).{8,16}$");
	Pattern nickNamePattern = Pattern.compile("^[가-힣ㄱ-ㅎa-zA-Z0-9]{2,12}$");
	Pattern loginIdPattern = Pattern.compile("^[a-zA-Z0-9]{4,12}$");

	public boolean loginIdMatches(String loginId) {
		Matcher loginIdMatcher = loginIdPattern.matcher(loginId);
		return loginIdMatcher.matches();
	}

	public boolean loginIdMatches(String loginId, BindingResult bindingResult) {
		Matcher loginIdMatcher = loginIdPattern.matcher(loginId);
		if (loginIdMatcher.matches())
			return true;
		bindingResult.reject("notAllowPattern", "아이디는 특수문자를 제외한 영어, 숫자로 4~12자로 작성해주세요.");
		return false;
	}

	public boolean passwordMatches(String password) {
		Matcher passwordMatcher = passwordPattern.matcher(password);
		return passwordMatcher.matches();
	}

	public boolean passwordMatches(String password, BindingResult bindingResult) {
		Matcher passwordMatcher = passwordPattern.matcher(password);
		if (passwordMatcher.matches())
			return true;
		bindingResult.reject("notAllowPattern", "비밀번호는 영문자, 숫자, 특수문자(_제외)를 모두 조합한 8~16자로 작성해주세요.");
		return false;
	}

	public boolean nickNameMatches(String nickName) {
		Matcher nickNameMatcher = nickNamePattern.matcher(nickName);
		return nickNameMatcher.matches();
	}

	public boolean nickNameMatches(String nickName, BindingResult bindingResult) {
		Matcher nickNameMatcher = nickNamePattern.matcher(nickName);
		if (nickNameMatcher.matches())
			return true;
		bindingResult.reject("notAllowPattern", "닉네임은 특수문자를 제외한 영어, 숫자, 한글로 2~12자로 작성해주세요.");
		return false;
	}
}
