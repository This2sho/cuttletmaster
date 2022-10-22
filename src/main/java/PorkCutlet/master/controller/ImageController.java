package PorkCutlet.master.controller;

import PorkCutlet.master.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.MalformedURLException;

@Controller
@RequiredArgsConstructor
@RequestMapping("/images")
public class ImageController {
    private final ImageUtils imageUtils;

    @GetMapping("/{fileName}")
    @ResponseBody
    public Resource getImage(@PathVariable String fileName) throws MalformedURLException {
        return new UrlResource("file:" + imageUtils.getFullPath(fileName));
    }
}
