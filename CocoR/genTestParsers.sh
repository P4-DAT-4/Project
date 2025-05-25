#!/usr/bin/env bash

# Make sure bash version >= 4 for associative arrays
if ((BASH_VERSINFO[0] < 4)); then
    echo "Bash 4 or higher is required."
    exit 1
fi

declare -A dependencies

# Define dependencies for each section
dependencies[ALL]=""

dependencies[DEF]="ALL EVENT STMT DECL DECLEXPR EXPR TYPE"
dependencies[EXPR]="ALL"
dependencies[TYPE]="ALL"

dependencies[DECLEXPR]="ALL EXPR"
dependencies[DECL]="ALL TYPE DECLEXPR EXPR"
dependencies[EVENT]="ALL EXPR"
dependencies[STMT]="ALL TYPE EXPR"

SECTIONS_FILE="AFS.atg"

function get_all_deps() {
    local target=$1
    local -n _deps=$2
    local result=()

    # Use associative array to avoid duplicates
    declare -A visited=()

    # Recursive function to collect dependencies
    function collect() {
        local t=$1
        if [[ -z "$t" ]]; then return; fi
        if [[ ${visited[$t]} ]]; then return; fi
        visited[$t]=1

        for dep in ${dependencies[$t]}; do
            collect "$dep"
        done
        result+=("$t")
    }

    collect "$target"
    _deps=("${result[@]}")
}

function generate_atg() {
    local target=$1
    local output="${target}.atg"

    local deps=()
    get_all_deps "$target" deps

    > "$output"
    for sec in "${deps[@]}"; do
        echo "Including section $sec..."
        # Extract section from file, remove BEGIN and END lines
        sed -n "/\/\* BEGIN_${sec} \*\//, /\/\* END_${sec} \*\//p" "$SECTIONS_FILE" | sed '1d;$d' >> "$output"
        echo -e "\n" >> "$output"
    done

    echo "Generated $output with sections: ${deps[*]}"
}

if [[ $# -eq 0 ]]; then
    echo "Usage: $0 SECTION_NAME"
    echo "Available sections: ${!dependencies[@]}"
    exit 1
fi

generate_atg "$1"
