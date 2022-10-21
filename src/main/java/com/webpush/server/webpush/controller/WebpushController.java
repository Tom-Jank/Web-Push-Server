package com.webpush.server.webpush.controller;

import com.webpush.server.webpush.service.WebpushService;
import nl.martijndwars.webpush.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.GET, RequestMethod.POST})
@RequestMapping("/api")
public class WebpushController {

    private final WebpushService webpushService;

    @Autowired
    public WebpushController(WebpushService webpushService) {
        this.webpushService = webpushService;
    }

    @PostMapping("/notification/subscribe")
    public void subscribeToNotifications(@RequestBody Subscription subscription) {
        webpushService.subscribe(subscription);
    }

    @PostMapping("/notification/send")
    public void sendNotifications() {
        webpushService.sendNotificationsToAllSubscribers();
    }
}
