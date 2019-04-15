#!/bin/bash
kafka-console-producer.sh \
	--broker-list localhost:9092 \
	--topic changes \
	--property "parse.key=true" --property "key.separator=:"