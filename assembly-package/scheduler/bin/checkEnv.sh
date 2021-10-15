#!/bin/sh

say() {
    printf 'check command fail \n %s\n' "$1"
}

err() {
    say "$1" >&2
    exit 1
}

check_cmd() {
    command -v "$1" > /dev/null 2>&1
}

need_cmd() {
    if ! check_cmd "$1"; then
        err "need '$1' (your linux command not found)"
    fi
}
echo "<-----start to check used cmd---->"
need_cmd yum
need_cmd java
need_cmd mysql
need_cmd unzip
need_cmd expect
need_cmd telnet
need_cmd tar
need_cmd sed
echo "<-----end to check used cmd---->"
