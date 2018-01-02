#!/usr/bin/env bash

mvn package
mkdir build
cp ./target/UT-CAS.jar ./build/
cp ./login-utcas.sh ./build/
cp ./login-utcas.sh ./build/logout-utcas.sh
cp ./src/main/resources/conf.properties ./build/