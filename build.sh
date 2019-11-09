#!/bin/bash

jarName="MauveS.jar"
cd src/

javac -d ../bin/ serverPackage/*.java

if [ $? -eq 0 ]; then
    echo "[*] Done building package"
    javac -d ../bin/ main.java
    if [ $? -eq 0 ]; then
      echo "[*] Done building main"
      chmod +x ../bin/main.class
      echo "[!] Creating jar file .."
        jar cvf ../export/$jarName ../bin/*.class ../bin/*/*.class
        if [ $? -eq 0 ]; then
          echo "[*] Done building $jarName in export/"
	  echo "[-*-] Done building overall project"
        else
          echo "[!] Error creating jar file"
          return 0
	fi
    else
      echo "[!] Failed building main"
      return 0
    fi
else
  echo "[!] Failed building packages"
  return 0
fi
