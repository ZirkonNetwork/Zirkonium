#!/usr/bin/env bash

# requires curl & jq
# Credit: https://github.com/Winds-Studio/Leaf

# Usage:
# upstreamCommit --leaf HASH
# flag: --leaf HASH - the commit hash to use for comparing commits between Zirkonium (Leaf/compare/HASH...HEAD)

function getCommits() {
    curl -H "Accept: application/vnd.github.v3+json" https://api.github.com/repos/"$1"/compare/"$2"..."$3" | jq -r '.commits[] | "'"$1"'@\(.sha[:8]) \(.commit.message | split("\r\n")[0] | split("\n")[0])" | sub("\\[ci( |-)skip]"; "[ci/skip]")'
}

(
set -e
PS1="$"

leafHash=$(git diff gradle.properties | awk '/^-leafCommit =/{print $NF}')

# Useless params standardize
# TEMP=$(getopt --long leaf: -o "" -- "$@")
# eval set -- "$TEMP"
while true; do
    case "$1" in
        --leaf)
            leafHash="$2"
            shift 2
            ;;
        *)
            break
            ;;
    esac
done

leaf=""
updated=""
logsuffix=""


# Leaf updates
if [ -n "$leafHash" ]; then
    leaf=$(getCommits "Winds-Studio/Leaf" "$leafHash" "HEAD")

    # Updates found
    if [ -n "$leaf" ]; then
        updated="${updated:+$updated/}Leaf"
        logsuffix="$logsuffix\n\Leaf Changes:\n$leaf"
    fi
fi

disclaimer="Upstream has released updates that appear to apply and compile correctly"
log="Updated Upstream ($updated)\n\n${disclaimer}${logsuffix}"

git add gradle.properties

echo -e "$log" | git commit -F -

) || exit 1