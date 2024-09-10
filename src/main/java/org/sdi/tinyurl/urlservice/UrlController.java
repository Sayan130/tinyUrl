package org.sdi.tinyurl.urlservice;

import org.sdi.tinyurl.accessor.UrlStore;
import org.sdi.tinyurl.keygenerationservice.GenerateKeys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;


@RestController
public class UrlController {

    @Autowired
    private GenerateKeys generateKeys;

    @Autowired
    private UrlStore urlStore;

    @GetMapping("/{path}")
    public RedirectView getUrl(@PathVariable String path) {
        System.out.println("PAth of uri:"+path);
        String url = urlStore.getUri(path).get();
        RedirectView redirectView = new RedirectView(url);
        return redirectView;
    }

    @PostMapping("/createurl")
    public String createUrl(@RequestBody createUrlRequest request) {
        System.out.println("Printing url:"+request.getUrl());
        String key = generateKeys.getKey();
        urlStore.storeUri(request.getUrl(), key);
        return key;
    }
}
