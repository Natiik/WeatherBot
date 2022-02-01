mvn clean package
ssh root@65.108.88.95 "killall java"
echo "old bot stopped"
ssh root@65.108.88.95 "rm WeatherBot.jar"
echo "old bot removed"
scp target/WeatherBot.jar  root@65.108.88.95:/root/
echo "new bot copied"
ssh root@65.108.88.95 "java -jar WeatherBot.jar --spring.config.additional-location=/root/lalala.properties" &
echo "new bot started"
