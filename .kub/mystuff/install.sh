#!/usr/bin/env bash
helm install --name=mystuff --namespace=webtree --set neo4j.neo4jPassword=$(head /dev/urandom | tr -dc A-Za-z0-9 | head -c 13 ; echo '') .