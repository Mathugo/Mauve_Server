#!/bin/bash
port="433"

function help()
{
	echo "# ------ Mauve Server ------ #"
	echo "[!] If you want to specify PORT please add argument to this script"
	echo "[!] Exemple : ./exe.sh 4433"
	echo "[*] Defaut PORT -> $port"
}

if [ "$#" -eq 0 ];then
	help
	echo "[*] Launching server with defaults parameters : $port"
elif [ "$#" -eq 1 ];then
	port=$1
	echo "[*] Launching Server on port $port"
else
	echo "[!] Error too many arguments"
	help
	return 0
fi

cd bin/
java main $port
