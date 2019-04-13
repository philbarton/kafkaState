#!/bin/bash
kafka-topics.sh --zookeeper localhost:2181 --describe --topic $1 
