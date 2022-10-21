### Web-Push notifications using VAPID in Java Spring

* [General info] (#general-info)
* [Technologies] (#technologies)
* [Useful resources for Push notifications] (#useful-resources-for-push-notifications
* [Important details] (#important-details)

## General info

This is a server for triggering Push notifications written in Java. It is designed to work with Angular 14 service worker.
You can find the repo to client app here under this link: https://github.com/Tom-Jank/Web-Push-Client.

## Technologies

* Java 17
* Spring Boot 2.7.5
* Maven
* Bouncy Castle
* Web-Push

## Useful info regarding Push notifications

For learning Push notifications you kinda need to learn about frontend implementation, therefore I strongly recommend these angular tutorials:
https://angular.io/guide/service-worker-intro
https://blog.angular-university.io/angular-push-notifications/
And/Or some valid PWA course that covers these topics eg. https://www.udemy.com/course/angular-pwa-course/

There is not much resources regarding backend implementations when it comes to Java. Writing this implementation I was looking into different
github repositories but found nothing good. The only valid resource that actually helped me was this: https://hilla.dev/blog/send-web-push-notifications-java/.

## Important details

This line is very, very, very important. I spent a good amount of time wondering why my code doesn't work and I thought it was a frontend bug.
It turned out it wasn't and in order for angular Service Worker to receive and corectly process a trigger Push notification from the server it has
to be sent exactly in this convention. We send this JSON as a string, yet we have to keep this structure.

```
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
```
