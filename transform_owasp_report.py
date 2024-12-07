import json
import os

# Input and output file paths
input_file = "target/dependency-check-report.json"
output_file = "target/dependency-check-sonar-report.json"

# Load OWASP Dependency-Check report
with open(input_file, "r") as f:
    data = json.load(f)

# Create SonarQube issues report structure
sonar_report = {"issues": []}

# Iterate through OWASP findings and transform them
for dependency in data.get("dependencies", []):
    for vulnerability in dependency.get("vulnerabilities", []):
        sonar_issue = {
            "engineId": "owasp-dependency-check",
            "ruleId": vulnerability.get("name"),
            "severity": "CRITICAL" if vulnerability.get("cvssScore", 0) >= 7 else "MAJOR",
            "type": "VULNERABILITY",
            "primaryLocation": {
                "message": vulnerability.get("description", "No description available."),
                "filePath": dependency.get("fileName", "unknown"),
                "textRange": {
                    "startLine": 1,
                    "endLine": 1,
                },
            },
        }
        sonar_report["issues"].append(sonar_issue)

# Save the transformed report
with open(output_file, "w") as f:
    json.dump(sonar_report, f, indent=4)

print(f"Transformed report saved to {output_file}")
