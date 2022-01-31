# issue3302
### Prerequisite
- Add google-services.json
### Summary
- TLDR: error `Caused by: java.net.UnknownHostException: Unable to resolve host "firestore.googleapis.com": No address associated with hostname` is encountered
### Steps to repro (These are steps from issue 2637, I immediately encountered the issue at step 6 after 4 mins)
1. Disable LTE data, just keep Wifi connected
2. Kill app, open app (around 16:23:35)
3. Wait for app to receive update from network (~16:24:03)
4. Navigate home, open google chrome to put the app in background
5. Force idle (adb shell dumpsys deviceidle force-idle)
6. Keep looking at logcat to see updates every minute (~16:25:02, 16:26:02, 16:27:02, 16:28:02)
7. Wait until there's a connection error that kicks off exponential backoff (~16:28:15 we see a connection error)
8. Open app after 8 minutes (~16:36:10) 
- Note: I was just looking at the exp backoff delay retries (it was as high as 84000s, and as soon as I saw 59947 ms I opened the app)
9. Wait with the app open until we finally get a network update at (~16:37:48)
- Note: this is about 1 minute after opening up the app, which matches the last backoff timer :O
