# Updated structure: each key maps to a custom object with .Deps and .MainRule
$sectionsInfo = @{
    "DEF"      = @{ Deps = @("STARTALL", "EVENTCODE", "STMTCODE", "DECLCODE", "EXPRCODE", "MIDDLEALL", "DEF", "EVENT", "STMT", "DECL", "DECLEXPR", "EXPR", "TYPE", "ENDALL"); MainRule = "Def"; ProgramNode = "DefNode" }
    "EXPR"     = @{ Deps = @("STARTALL", "EXPRCODE", "MIDDLEALL", "EXPR", "ENDALL"); MainRule = "Expr"; ProgramNode = "ExprNode" }
    "TYPE"     = @{ Deps = @("STARTALL", "MIDDLEALL", "TYPE", "ENDALL"); MainRule = "Type"; ProgramNode = "TypeNode" }
    "DECLEXPR" = @{ Deps = @("STARTALL", "EXPRCODE", "MIDDLEALL", "DECLEXPR", "EXPR", "ENDALL"); MainRule = "DeclExpr"; ProgramNode = "ExprNode" }
    "DECL"     = @{ Deps = @("STARTALL", "DECLCODE", "EXPRCODE", "MIDDLEALL", "DECL", "DECLEXPR", "EXPR", "TYPE", "ENDALL"); MainRule = "Decl"; ProgramNode = "StmtNode" }
    "EVENT"    = @{ Deps = @("STARTALL", "EVENTCODE", "EXPRCODE", "MIDDLEALL", "EVENT", "EXPR", "ENDALL"); MainRule = "Events"; ProgramNode = "EventNode" }
    "STMT"     = @{ Deps = @("STARTALL", "STMTCODE", "EXPRCODE", "MIDDLEALL", "STMT", "EXPR", "TYPE", "ENDALL"); MainRule = "Stmt"; ProgramNode = "StmtNode" }
}

$ScriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path
$SECTIONS_FILE = Join-Path $ScriptDir "AFS.atg"
$outputBaseDir = Join-Path $ScriptDir "TestGrammars"

# Create the base output directory if it doesn't exist
if (-Not (Test-Path $outputBaseDir)) {
    New-Item -ItemType Directory -Path $outputBaseDir | Out-Null
}

foreach ($sectionName in $sectionsInfo.Keys) {
    # Output file inside base dir folder
    $outputFile = Join-Path $outputBaseDir ($sectionName + ".atg")

    $sectionsToInclude = $sectionsInfo[$sectionName].Deps
    $mainRule = $sectionsInfo[$sectionName].MainRule

    $programNode = $sectionsInfo[$sectionName].ProgramNode

    Write-Host "Generating $outputFile with sections: $($sectionsToInclude -join ', ')"

    # Clear output file
    "" | Out-File -FilePath $outputFile

    foreach ($sec in $sectionsToInclude) {
        $startPattern = "/* BEGIN $sec */"
        $endPattern = "/* END $sec */"

        $insideSection = $false
        $sectionContent = @()

        # Read the whole section into an array
        Get-Content $SECTIONS_FILE | ForEach-Object {
            if ($_ -like "*$startPattern*") {
                $insideSection = $true
                return
            }
            if ($_ -like "*$endPattern*") {
                $insideSection = $false
                return
            }
            if ($insideSection) {
                $sectionContent += $_
            }
        }

        # Write the section content to the output file
        $sectionContent | Out-File -FilePath $outputFile -Append

        # After MIDDLEALL section, insert the Program = ... line
        if ($sec -eq "MIDDLEALL") {
            "Program = $mainRule<out mainNode> ." | Out-File -FilePath $outputFile -Append
            "" | Out-File -FilePath $outputFile -Append  # blank line
        }
        else {
            "" | Out-File -FilePath $outputFile -Append  # blank line between other sections
        }
    }

    # Replace 'ProgNode' in the mainNode declaration with the correct MainRule type
    (Get-Content $outputFile) | ForEach-Object {
        if ($_ -match 'public\s+\w+\s+mainNode\s*=\s*null;') {
            $_ -replace '\b\w+\b(?=\s+mainNode\s*=\s*null;)', $programNode
        } else {
            $_
        }
    } | Set-Content -Encoding UTF8 $outputFile

    # 🧼 Clean up whitespace: strip leading and trailing spaces from every line
    (Get-Content $outputFile) |
            ForEach-Object { $_.Trim() } |
            Set-Content -Encoding UTF8 $outputFile

    $projectRoot = Split-Path -Parent $ScriptDir  # Assuming $ScriptDir is ./cocor
    $javaOutputDir = Join-Path $projectRoot "src/test/java/setup/$sectionName"

    # Create output directory for Java files if it doesn't exist
    if (-Not (Test-Path $javaOutputDir)) {
        New-Item -ItemType Directory -Path $javaOutputDir | Out-Null
    }

    # Remove old generated files
    $parserFile = Join-Path $javaOutputDir "Parser.java"
    $scannerFile = Join-Path $javaOutputDir "Scanner.java"

    if (Test-Path $parserFile) { Remove-Item $parserFile -Force }
    if (Test-Path $scannerFile) { Remove-Item $scannerFile -Force }

    # Path to the generated ATG file
    $atgFile = Join-Path $outputBaseDir ($sectionName + ".atg")

    # Run Coco/R
    Write-Host "Generating parser and scanner for $atgFile)"
    & java -jar "$ScriptDir/Coco.jar" $atgFile -o $javaOutputDir -package "setup.$sectionName" -frames "$ScriptDir"
}