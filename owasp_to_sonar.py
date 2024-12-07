#!/usr/bin/env python3
import json
import sys

"""
Usage:
    python owasp_to_sonar.py <input_json> <output_json>

Example:
    python owasp_to_sonar.py target/dependency-check-report/dependency-check-report.json sonar-issues.json
"""

if len(sys.argv) != 3:
    print("Usage: python owasp_to_sonar.py <input_json> <output_json>")
    sys.exit(1)

input_file = sys.argv[1]
output_file = sys.argv[2]

with open(input_file, 'r') as f:
    data = json.load(f)

issues = []

# The OWASP Dependency-Check JSON structure typically includes "dependencies" with "vulnerabilities".
# Adjust the parsing code as necessary based on the actual schema you have.
#
# Example schema snippet:
# {
#   "scanInfo": {...},
#   "projectInfo": {...},
#   "dependencies": [
#       {
#           "fileName": "path/to/dependency.jar",
#           "vulnerabilities": [
#               {
#                   "name": "CVE-XXXX-XXXX",
#                   "description": "A vulnerability description.",
#                   "severity": "HIGH",
#                   "cwe": "CWE-79"
#               }
#           ]
#       }
#   ]
# }

dependencies = data.get('dependencies', [])

# Map OWASP severity to Sonar severity
severity_map = {
    "CRITICAL": "BLOCKER",
    "HIGH": "CRITICAL",
    "MEDIUM": "MAJOR",
    "LOW": "MINOR",
    "INFO": "INFO"
}

for dep in dependencies:
    file_path = dep.get('fileName', 'unknown_file')
    vulns = dep.get('vulnerabilities', [])
    for v in vulns:
        cve = v.get('name', 'Unknown-CVE')
        description = v.get('description', 'No description available.')
        owasp_sev = v.get('severity', 'MEDIUM').upper()  # Default to MEDIUM if not present
        sonar_sev = severity_map.get(owasp_sev, 'MAJOR')

        # Create a generic Sonar issue
        issue = {
            "engineId": "owasp-dependency-check",
            "ruleId": cve,
            "type": "VULNERABILITY",
            "severity": sonar_sev,
            "primaryLocation": {
                "message": description,
                "filePath": file_path
            }
        }
        issues.append(issue)

# Write the converted issues to the output file
with open(output_file, 'w') as out:
    json.dump(issues, out, indent=2)
