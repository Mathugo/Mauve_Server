#!/bin/bash

jarName="MauveS.jar"
cd src/

javac -d ../bin/ serverPackage/*.java

if [ $? -eq 0 ]; then
    echo "[*] Done building package"
    javac -d ../bin/ main.java
    if [ $? -eq 0 ]; then
      echo "[*] Done building"
      chmod +x ../bin/main.class
      echo "[!] Creating jar file .."
      jar cvf ../$jarName ../bin/*.class ../bin/*/*.class
        if [ $? -eq 0 ]; then
          echo "[*] Done"
        else
          echo "[!] Error creating jar file"
        fi
    else
      echo "[!] Failed building main"
    fi
else
  echo "[!] Failed building packages"
fi
