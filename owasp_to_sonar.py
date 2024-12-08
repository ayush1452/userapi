#!/usr/bin/env python3
import json
import sys
import os

if len(sys.argv) != 3:
    print("Usage: python owasp_to_sonar.py <input_json> <output_json>")
    sys.exit(1)

input_file = sys.argv[1]
output_file = sys.argv[2]

# Verify input file exists
if not os.path.exists(input_file):
    print(f"Error: Input file {input_file} does not exist")
    sys.exit(1)

try:
    with open(input_file, 'r') as f:
        data = json.load(f)
except json.JSONDecodeError as e:
    print(f"Error: Invalid JSON in input file: {e}")
    sys.exit(1)

issues = []

severity_map = {
    "CRITICAL": "BLOCKER",
    "HIGH": "CRITICAL",
    "MEDIUM": "MAJOR",
    "LOW": "MINOR",
    "INFO": "INFO"
}

# Add validation for required fields
if 'dependencies' not in data:
    print("Error: No 'dependencies' field found in input JSON")
    sys.exit(1)

for dep in data['dependencies']:
    file_path = dep.get('fileName', 'unknown_file')
    vulns = dep.get('vulnerabilities', [])

    for v in vulns:
        cve = v.get('name', 'Unknown-CVE')
        description = v.get('description', 'No description available.')

        # Handle nested source objects for description
        if isinstance(description, dict) and 'description' in description:
            description = description['description']

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

try:
    with open(output_file, 'w') as out:
        json.dump(output_data, out, indent=2)
    print(f"Successfully converted {len(issues)} issues to Sonar format")
except Exception as e:
    print(f"Error writing output file: {e}")
    sys.exit(1)