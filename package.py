import subprocess
import sys

import xml.etree.ElementTree as ET

dependency_map = {
    'citus': [('org.postgresql', 'postgresql', '42.5.1')],
    'postgres': [('org.postgresql', 'postgresql', '42.5.1')],
    'yugabyte': [
        ('org.postgresql', 'postgresql', '42.5.1'),
        ('com.yugabyte', 'jdbc-yugabytedb', '42.3.5-yb-1', 'runtime')
    ],
    'materialize': [('org.postgresql', 'postgresql', '42.5.1')],
    'presto': [
        ('com.ing.data', 'cassandra-jdbc-wrapper', '4.7.0'),
        ('com.facebook.presto', 'presto-jdbc', '0.283', 'runtime')
    ],
    'clickhouse': [('ru.yandex.clickhouse', 'clickhouse-jdbc', '0.3.2')],
    'cnosdb': [
        ('com.arangodb', 'arangodb-java-driver', '6.9.0'),
        ('org.apache.commons', 'commons-csv', '1.9.0'),
        ('ru.yandex.clickhouse', 'clickhouse-jdbc', '0.3.2')
    ],
    'hsqldb' : [('org.hsqldb', 'hsqldb', '2.7.1', 'runtime')],
    'duckdb' : [('org.duckdb', 'duckdb_jdbc', '1.1.3', 'runtime')],
    'questdb' : [('org.questdb', 'questdb', '6.5.3', 'runtime')],
    'databend' : [('mysql', 'mysql-connector-java', '8.0.30', 'runtime')],
    'mysql' : [('mysql', 'mysql-connector-java', '8.0.30', 'runtime')],
    'datafusion' : [('org.apache.arrow', 'flight-sql-jdbc-driver', '16.1.0', 'runtime')],
    'h2' : [('com.h2database', 'h2', '2.3.232', 'runtime')],
    'mariadb' : [('org.mariadb.jdbc', 'mariadb-java-client', '3.1.0', 'runtime')],
    'oceanbase' : [('mysql', 'mysql-connector-java', '8.0.30', 'runtime')],
    'sqlite3' : [('org.xerial', 'sqlite-jdbc', '3.47.2.0', 'runtime')],
    'tidb' : [('mysql', 'mysql-connector-java', '8.0.30', 'runtime')],
    'doris' : [('mysql', 'mysql-connector-java', '8.0.30', 'runtime')]
}
ns = {"mvn": "http://maven.apache.org/POM/4.0.0"}
ET.register_namespace("", ns['mvn'])

def clean_dep(tree):
    dependencies = tree.getroot().find("./mvn:dependencies", ns)
    # Very ugly, but required to maintain pom.xml file
    to_remove = sorted(list(set([dep[1] for deps in dependency_map.values() for dep in deps])))
    removed_dep = []

    for remove_dep in to_remove:
        print(f"Removing {remove_dep}")
        dep = dependencies.find(f"./mvn:dependency[mvn:artifactId='{remove_dep}']", ns)
        removed_dep.append(dep)
        dependencies.remove(dep)

    return dependencies, removed_dep

def insert_dep(profile, db):
    db_dependencies = ET.Element("dependencies")
    for dep in dependency_map[db]:
        if len(dep) == 3:
            group_id, artifact_id, version = dep
            scope = None
        else:
            group_id, artifact_id, version, scope = dep
        print(f"Inserting {artifact_id}")
        db_dependency = ET.Element("dependency")
        ET.SubElement(db_dependency, 'groupId').text = group_id
        ET.SubElement(db_dependency, 'artifactId').text = artifact_id
        ET.SubElement(db_dependency, 'version').text = version
        if scope:
            ET.SubElement(db_dependency, 'scope').text = scope
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
