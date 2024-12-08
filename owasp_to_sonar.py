import json
import sys

# Define input and output file paths
input_file = "target/dependency-check-report/dependency-check-report.json"
output_file = "sonar-issues.json"
associated_file = "pom.xml"

# Load the OWASP Dependency-Check JSON report
with open(input_file, "r") as f:
    data = json.load(f)

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
                    "cleanCodeAttribute": "SECURE",
                    "impacts": [
                        {
                            "softwareQuality": "SECURITY",
                            "severity": "HIGH" if vulnerability.get("severity") == "HIGH" else "MEDIUM"
                        }
                    ]
                })

            # Add the issue
            issues.append({
                "engineId": "DependencyCheck",
                "ruleId": rule_id,
                "primaryLocation": {
                    "message": vulnerability.get("description", "No description provided"),
                    "filePath": associated_file,
                    "textRange": {
                        "startLine": 1,
                        "endLine": 1,
                        "startColumn": 1,
                        "endColumn": 80
                    }
                }
            })

# Create the final structure
output_data = {
    "rules": rules,
    "issues": issues
}

# Write the output JSON file
with open(output_file, "w", encoding="utf-8") as f:
    json.dump(output_data, f, indent=4)

print(f"SonarCloud-compatible JSON file written to {output_file}")
