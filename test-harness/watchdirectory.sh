#!/bin/bash
while [ "$(ls -A "$1")" != "" ]
do
    echo "Waiting for Lucee to pickup lex file..."
    sleep 5
done