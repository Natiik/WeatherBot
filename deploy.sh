mvn clean package
ssh root@65.108.88.95 "killall java"
echo "old bot stopped"
ssh root@65.108.88.95 "rm WeatherBot-1.0-SNAPSHOT-jar-with-dependencies.jar"
echo "old bot removed"
scp target/WeatherBot-1.0-SNAPSHOT-jar-with-dependencies.jar  root@65.108.88.95:/root/
echo "new bot copied"
ssh root@65.108.88.95 "java -jar WeatherBot-1.0-SNAPSHOT-jar-with-dependencies.jar" &
echo "new bot started"
