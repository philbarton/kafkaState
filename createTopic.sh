#!/bin/bash
kafka-topics.sh --zookeeper localhost:2181 --create --topic $1 --partitions 20 --replication-factor 3 --config retention.ms=1680000
