package com.webpush.server.webpush.service.implementation;

import com.webpush.server.webpush.service.WebpushService;
import nl.martijndwars.webpush.Notification;
import nl.martijndwars.webpush.PushService;
import nl.martijndwars.webpush.Subscription;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.jose4j.lang.JoseException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Service
public class WebpushServiceImpl implements WebpushService {

    private PushService pushService;

    private final List<Subscription> subscriptions = new ArrayList<>();

    @PostConstruct
    private void init() {
        Security.addProvider(new BouncyCastleProvider());
        try {
            String vapidPublicKey = "BDhnhtne-c-e2K9UjZ21Krv5hC4b4RlctTP0tme6lkhoOgYKKKupGkJRm_tzF8DsiuhmfsV-ZbXZeoDfzrHkZ9M";
            String vapidPrivateKey = ""; //shorter one
            pushService = new PushService(vapidPublicKey, vapidPrivateKey);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void subscribe(Subscription subscription) {
        if(checkIfAlreadySubscribed(subscription)) {
            this.subscriptions.remove(subscription);
        }
        else {
            this.subscriptions.add(subscription);
        }
    }

    @Override
    public boolean checkIfAlreadySubscribed(Subscription subscription) {
        return subscriptions.stream().anyMatch(sub -> Objects.equals(sub.endpoint, subscription.endpoint));
    }

    @Override
    public void sendNotification(Subscription subscription, String message) {
        try {

            pushService.send(new Notification(subscription, message));

        } catch (JoseException |
                 GeneralSecurityException |
                 IOException |
                 ExecutionException |
                 InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendNotificationsToAllSubscribers() {

        var json = """
                {
                    "notification": {
                        "title": "Angular News",
                        "body": "Newsletter Available!",
                        "icon": "assets/main-page-logo-small-hat.png",
                        "vibrate": [100, 50, 100],
                        "data": {
                            "dateOfArrival": "",
                            "primaryKey": 1
                        },
                        "actions": [{
                            "action": "explore",
                            "title": "Go to the site"
                        }]
                    }
                }
                """;

        subscriptions.forEach(subscription -> sendNotification(subscription, json));
    }
}
