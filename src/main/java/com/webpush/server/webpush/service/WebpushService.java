package com.webpush.server.webpush.service;

import nl.martijndwars.webpush.Subscription;

public interface WebpushService {

    void subscribe(Subscription subscription);

    boolean checkIfAlreadySubscribed(Subscription subscription);

    void sendNotification(Subscription subscription, String message);

    void sendNotificationsToAllSubscribers();
}
