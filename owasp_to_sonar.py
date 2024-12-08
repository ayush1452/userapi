import json
import sys

# Define input and output file paths
input_file = "target/dependency-check-report/dependency-check-report.json"  # OWASP Dependency-Check JSON input
output_file = "sonar-issues.json"  # SonarCloud-compatible JSON output
associated_file = "pom.xml"  # File to associate issues (e.g., pom.xml for Maven projects)

# Load OWASP Dependency-Check JSON report
with open(input_file, "r") as f:
    data = json.load(f)

# Read the associated file to calculate valid line lengths
try:
    with open(associated_file, "r") as f:
        lines = f.readlines()
except FileNotFoundError:
    print(f"Error: The associated file '{associated_file}' was not found.")
    sys.exit(1)

# Initialize the rules and issues
rules = []
issues = []

# Process dependencies and vulnerabilities
for dependency in data.get("dependencies", []):
    if "vulnerabilities" in dependency:
        for vulnerability in dependency["vulnerabilities"]:
            # Add a new rule if it doesn't already exist
            rule_id = vulnerability.get("name", "unknown-rule")
            if not any(rule["id"] == rule_id for rule in rules):
                rules.append({
                    "id": rule_id,
                    "name": "Dependency Vulnerability",
                    "description": vulnerability.get("description", "No description provided"),
                    "engineId": "DependencyCheck",
                    "cleanCodeAttribute": "LAWFUL",  # Replace with a valid value
                    "impacts": [
                        {
                            "softwareQuality": "SECURITY",
                            "severity": "HIGH" if vulnerability.get("severity") == "HIGH" else "MEDIUM"
                        }
                    ]
                })

            # Dynamically calculate valid textRange
            start_line = 1  # Example: Issue starts at line 1
            end_line = 1
            start_column = 1
            end_column = min(80, len(lines[start_line - 1].rstrip()))  # Trim trailing whitespace and calculate length

            # Add the issue
            issues.append({
                "engineId": "DependencyCheck",
                "ruleId": rule_id,
                "primaryLocation": {
                    "message": vulnerability.get("description", "No description provided"),
                    "filePath": associated_file,
                    "textRange": {
                        "startLine": start_line,
                        "endLine": end_line,
                        "startColumn": start_column,
                        "endColumn": end_column
                    }
                }
            })

# Create the final structure
output_data = {
    "rules": rules,
    "issues": issues
}

# Write the SonarCloud-compatible JSON file
with open(output_file, "w", encoding="utf-8") as f:
    json.dump(output_data, f, indent=4)

print(f"SonarCloud-compatible JSON file written to {output_file}")
