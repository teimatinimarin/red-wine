#!/bin/sh
java -Dfile.encoding=UTF-8 --add-opens java.base/java.lang=openwebbeans.impl --module-path ./bin:./lib --module redwine.sensor/com.beuwa.redwine.sensor.Main
