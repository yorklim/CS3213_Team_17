import subprocess
import sys

import xml.etree.ElementTree as ET

dependency_map = {
    'postgres': [('org.postgresql', 'postgresql', '42.5.1')],
    'yugabyte': [('org.postgresql', 'postgresql', '42.5.1')],
    'materialize': [('org.postgresql', 'postgresql', '42.5.1')],
    'presto': [('com.ing.data', 'cassandra-jdbc-wrapper', '4.7.0')],
    'clickhouse': [('ru.yandex.clickhouse', 'clickhouse-jdbc', '0.3.2')],
    'cnosdb': [
        ('com.arangodb', 'arangodb-java-driver', '6.9.0'),
        ('org.apache.commons', 'commons-csv', '1.9.0')
    ]
}
ns = {"mvn": "http://maven.apache.org/POM/4.0.0"}
ET.register_namespace("", ns['mvn'])

def clean_dep(tree):
    dependencies = tree.getroot().find("./mvn:dependencies", ns)
    to_remove = set([artifact_id for dep in dependency_map.values() for _, artifact_id, _ in dep])
    removed_dep = []

    for remove_dep in to_remove:
        print(f"Removing {remove_dep}")
        dep = dependencies.find(f"./mvn:dependency[mvn:artifactId='{remove_dep}']", ns)
        removed_dep.append(dep)
        dependencies.remove(dep)

    return dependencies, removed_dep

def insert_dep(profile, db):
    db_dependencies = ET.Element("dependencies")
    for group_id, artifact_id, version in dependency_map[db]:
        db_dependency = ET.Element("dependency")
        ET.SubElement(db_dependency, 'groupId').text = group_id
        ET.SubElement(db_dependency, 'artifactId').text = artifact_id
        ET.SubElement(db_dependency, 'version').text = version
        db_dependencies.append(db_dependency)
    profile.append(db_dependencies)

def cleanup(tree, profile, to_add, dependencies):
    if profile.find("dependencies", ns) is not None:
        profile.remove(profile.find("dependencies", ns))
    for add_dep in to_add:
        dependencies.append(add_dep)
    ET.indent(tree, '  ')
    tree.write('pom.xml', encoding="utf-8", xml_declaration=True)

def main():
    # Build if no db specific
    if len(sys.argv) < 2:
        subprocess.run(['mvn', 'clean', 'package', '-DskipTests'])
        return 1

    tree = ET.parse('pom.xml')
    db = sys.argv[1]

    # Remove unnecessary dependencies
    dependencies, removed = clean_dep(tree)

    # Get profile to edit
    profile = tree.getroot().find("./mvn:profiles/mvn:profile[mvn:id='Isolate']", ns)

    # If db has specific dependency, add it
    if db in dependency_map:
        insert_dep(profile, db)

    # Update pom.xml and compile
    ET.indent(tree, '  ')
    tree.write('pom.xml', encoding="utf-8", xml_declaration=True)
    subprocess.run(['mvn', 'clean', 'package', '-DskipTests', f"-Ddb={db}"])

    # Cleanup (prevent changes to pom.xml on git & prepare pom.xml for next use)
    cleanup(tree, profile, removed, dependencies)

    return 1

if __name__ == "__main__":
    main()
