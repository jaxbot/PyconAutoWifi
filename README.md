# Deprecated, but useful example

I built this to automatically log in to the captive portal at PyCon 2015, because any time a user went to a different conference room, they had to accept the terms all over again.

This projects can be used as an example of automatically signing in to a captive portal whenever the WiFi state changes. The actual code for the Cisco routers there is nightmarish and hacked together, but the rest is pretty alright.

## Text from Pycon

This code will automatically simulate a "j'accepte" press for the WiFi in the PyCon 2015 venue.

Sideload the [APK here](https://github.com/jaxbot/PyconAutoWifi/releases/tag/0.0.1), then you should be good to go. Uninstall after the conference. This just adds a broadcast receiver for whenever the system connects to WiFi to send a few GET/POST requests if the SSID matches "PyCon 2015".

The code is pretty nightmarish. Fixes welcome. Feedback via @jaxbot or jaxbot at gmail.


