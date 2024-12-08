#!/usr/bin/env python3
import json
import sys

if len(sys.argv) != 3:
    print("Usage: python owasp_to_sonar.py <input_json> <output_json>")
    sys.exit(1)

input_file = sys.argv[1]
output_file = sys.argv[2]

with open(input_file, 'r') as f:
    data = json.load(f)

issues = []

severity_map = {
    "CRITICAL": "BLOCKER",
    "HIGH": "CRITICAL",
    "MEDIUM": "MAJOR",
    "LOW": "MINOR",
    "INFO": "INFO"
}

dependencies = data.get('dependencies', [])
for dep in dependencies:
    file_path = dep.get('fileName', 'unknown_file')
    vulns = dep.get('vulnerabilities', [])
    for v in vulns:
        cve = v.get('name', 'Unknown-CVE')
        description = v.get('description', 'No description available.')
        owasp_sev = v.get('severity', 'MEDIUM').upper()
        sonar_sev = severity_map.get(owasp_sev, 'MAJOR')

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

# Now wrap issues in an object
output_data = {
    "issues": issues
}

with open(output_file, 'w') as out:
    json.dump(output_data, out, indent=2)
