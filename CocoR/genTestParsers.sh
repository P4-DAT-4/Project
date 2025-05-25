#!/bin/bash

set -e

declare -A sections_mainrule=(
  [DEF]="Def"
  [EXPR]="Expr"
  [TYPE]="Type"
  [DECLEXPR]="DeclExpr"
  [DECL]="Decl"
  [EVENT]="Events"
  [STMT]="Stmt"
  [STMTBLOCK]="StmtBlock"
  [DECLBLOCK]="DeclBlock"
)

declare -A sections_programnode=(
  [DEF]="DefNode"
  [EXPR]="ExprNode"
  [TYPE]="TypeNode"
  [DECLEXPR]="ExprNode"
  [DECL]="StmtNode"
  [EVENT]="EventNode"
  [STMT]="StmtNode"
  [STMTBLOCK]="StmtNode"
  [DECLBLOCK]="StmtNode"
)

declare -A sections_deps=(
  [DEF]="STARTALL DEFCODE EVENTCODE STMTCODE DECLCODE EXPRCODE MIDDLEALL DEF EVENT STMT DECL DECLEXPR EXPR TYPE ENDALL"
  [EXPR]="STARTALL EXPRCODE MIDDLEALL EXPR ENDALL"
  [TYPE]="STARTALL MIDDLEALL TYPE ENDALL"
  [DECLEXPR]="STARTALL EXPRCODE MIDDLEALL DECLEXPR EXPR ENDALL"
  [DECL]="STARTALL DECLCODE EXPRCODE MIDDLEALL DECL DECLEXPR EXPR TYPE ENDALL"
  [EVENT]="STARTALL EVENTCODE EXPRCODE MIDDLEALL EVENT EXPR ENDALL"
  [STMT]="STARTALL STMTCODE EXPRCODE MIDDLEALL STMT EXPR TYPE ENDALL"
  [STMTBLOCK]="STARTALL STMTCODE EXPRCODE MIDDLEALL STMT EXPR TYPE ENDALL"
  [DECLBLOCK]="STARTALL DECLCODE EXPRCODE MIDDLEALL DECL DECLEXPR EXPR TYPE ENDALL"
)

script_dir="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
sections_file="$script_dir/AFS.atg"
output_base="$script_dir/TestGrammars"

mkdir -p "$output_base"

for section in "${!sections_mainrule[@]}"; do
  deps=${sections_deps[$section]}
  main_rule=${sections_mainrule[$section]}
  program_node=${sections_programnode[$section]}
  output_file="$output_base/$section.atg"
  java_output_dir="$script_dir/../src/test/java/setup/$section"

  echo "Generating $output_file with sections: $deps"

  > "$output_file"

  for dep in $deps; do
    awk -v start="/* BEGIN $dep */" -v end="/* END $dep */" '
      $0 ~ start { found=1; next }
      $0 ~ end { found=0; next }
      found { print }
    ' "$sections_file" >> "$output_file"

    if [[ "$dep" == "MIDDLEALL" ]]; then
      echo "" >> "$output_file"
      echo "Program = $main_rule<out mainNode> ." >> "$output_file"
    fi
    echo "" >> "$output_file"
  done

  # Replace mainNode type
  sed -i -E "s/public[[:space:]]+[A-Za-z_][A-Za-z0-9_]*[[:space:]]+mainNode[[:space:]]*=[[:space:]]*null;/public $program_node mainNode = null;/" "$output_file"

  # Trim leading/trailing blank lines
  awk 'BEGIN { skip=1 }
    skip && NF { skip=0 }
    !skip { lines[++count]=$0 }
    END {
      for (i = 1; i <= count && lines[i] ~ /^$/; i++) {}
      for (j = count; j >= i && lines[j] ~ /^$/; j--) {}
      for (k = i; k <= j; k++) print lines[k]
    }' "$output_file" > "$output_file.tmp" && mv "$output_file.tmp" "$output_file"

  mkdir -p "$java_output_dir"
  rm -f "$java_output_dir/Parser.java" "$java_output_dir/Scanner.java"

  echo "Generating parser and scanner for $section"
  java -jar "$script_dir/Coco.jar" "$output_file" -o "$java_output_dir" -package "setup.$section" -frames "$script_dir"
done
