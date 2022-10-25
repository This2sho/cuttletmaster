package PorkCutlet.master.controller;

import java.net.MalformedURLException;
import java.util.Enumeration;
import java.util.Iterator;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import PorkCutlet.master.ImageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@RequestMapping("/images")
@Slf4j
public class ImageController {
	private final ImageUtils imageUtils;

	@GetMapping("/{fileName}")
	@ResponseBody
	public Resource getImage(@PathVariable String fileName, HttpServletRequest request) throws MalformedURLException {
		Cookie[] cookies = request.getCookies();
		HttpSession session = request.getSession();
		Enumeration<String> attributeNames = session.getAttributeNames();
		Iterator<String> stringIterator = attributeNames.asIterator();
		while (stringIterator.hasNext()) {
			String next = stringIterator.next();
			log.info("session attribute name = {}", next);
		}
		for (Cookie cookie : cookies) {
			log.info("cookie name = {}", cookie.getName());
		}
		return new UrlResource("file:" + imageUtils.getFullPath(fileName));
	}
}
