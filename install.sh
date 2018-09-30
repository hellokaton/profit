#!/bin/sh

get_latest_release() {
  curl --silent "https://api.github.com/repos/$1/releases/latest" | grep '"tag_name":' | sed -E 's/.*"([^"]+)".*/\1/'
}

https://api.github.com/repos/biezhi/profit/releases/latest

LATEST_RELEASE=$(curl -L -s -H 'Accept: application/json' https://github.com/biezhi/make-money/releases/latest)


getLatest() {
  REPO="biezhi/make-money"
  SUFFIX="tar.gz"
  curl --silent https://api.github.com/repos/$REPO/releases/latest | \
    python -c "import sys; from json import loads as l; x = l(sys.stdin.read()); print(''.join(s['browser_download_url'] for s in x['assets'] if s['name'].endswith('.$SUFFIX')))"
}

get_latest_release "biezhi/make-money"
tar -zxvf make-money.tar.gz
cd make-money
sh tool start
