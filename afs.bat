# PowerShell script: run.ps1

# Set configuration
$jarFile = "AFS.jar"
$inputFile = "input.txt"
$outputFile = "output.dot"

# Run the AFS jar
java -jar $jarFile $inputFile --print $outputFile