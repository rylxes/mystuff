#!/bin/bash
#TODO: support more configs


if [[ -v back_url ]]; then
  echo "Set back url to: " + back_url
  jq ".backUrl=env.back_url" /usr/share/nginx/html/config.json > /usr/share/nginx/html/config.json.tmp && mv /usr/share/nginx/html/config.json.tmp /usr/share/nginx/html/config.json
fi
echo "Starting nginx..."
nginx -g "daemon off;"
