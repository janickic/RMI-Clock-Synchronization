# Clock synchonization using RMI

Compile java code
```
javac *.java
```

Start rmi register on server
```
rmiregister &
```

Start server code
```
java Server
```

Start client code (Server ip as args)
```
java Client 127.0.0.1
```