#!/bin/bash

# get username
# get password
# todo use apple key-chain to store password
# more info https://www.netmeister.org/blog/keychain-passwords.html

# extract command from script file name
COMMAND=$(echo `basename "$0"` | rev | cut -d"/" -f1 | rev | cut -d"-" -f1)

echo $COMMAND


# ask user to know if I should start browser or continue with bash script

# start selenium
java -jar ./target/UT-CAS.jar ./conf.properties ${COMMAND}
# stop selenium

# curl to internet.ut.ac.ir based on command