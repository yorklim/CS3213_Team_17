import json
import os
import shutil
import subprocess
import sys
import zipfile

import xml.etree.ElementTree as ET

ns = {"mvn": "http://maven.apache.org/POM/4.0.0"}
ET.register_namespace("", ns['mvn'])

# Mapping done by analysing source code for jdbc entrypoint
jdbc_map = {
    'citus': ('org.postgresql', 'postgresql', '42.5.1'), # check
    'postgres': ('org.postgresql', 'postgresql', '42.5.1'), # check
    'cockroachdb': ('org.postgresql', 'postgresql', '42.5.1'), # check
    'materialize': ('org.postgresql', 'postgresql', '42.5.1'), # check
    'databend' : ('mysql', 'mysql-connector-java', '8.0.30'), # check
    'tidb' : ('mysql', 'mysql-connector-java', '8.0.30'), # check
    'doris' : ('mysql', 'mysql-connector-java', '8.0.30'), # check
    'mysql' : ('mysql', 'mysql-connector-java', '8.0.30'), # check
    'oceanbase' : ('mysql', 'mysql-connector-java', '8.0.30'), # check
    'datafusion' : ('org.apache.arrow', 'flight-sql-jdbc-driver', '16.1.0'), # check
    'cnosdb': ('org.apache.arrow', 'flight-sql-jdbc-driver', '16.1.0'), # Unsure, no indication
    'sqlite3' : ('org.xerial', 'sqlite-jdbc', '3.47.2.0'), # check
    'h2' : ('com.h2database', 'h2', '2.3.232'), # check
    'hsqldb' : ('org.hsqldb', 'hsqldb', '2.7.1'), # check
    'duckdb' : ('org.duckdb', 'duckdb_jdbc', '1.1.3'), # check
    'presto': ('com.facebook.presto', 'presto-jdbc', '0.283'), # check
    'mariadb' : ('org.mariadb.jdbc', 'mariadb-java-client', '3.1.0'), # check
    'yugabyte': ('com.yugabyte', 'jdbc-yugabytedb', '42.3.5-yb-1'), # check
    'questdb' : ('org.questdb', 'questdb', '6.5.3'), # Connects psql, but has its own jdbc (?)
    'clickhouse': ('ru.yandex.clickhouse', 'clickhouse-jdbc', '0.3.2'), # check
}

def build_dependencies(file_dir, build_all=True):
    """Split into sqlancer own internal dependencies, and external mvn dependencies"""
    assert os.path.isdir(file_dir)

    base_dir = "./src/"

    checked = set()
    packages = set()
    external_deps = set()

    files = [os.path.join(file_dir, i) for i in os.listdir(file_dir)]

    def reduce_main_class(name):
        tmp = []
        for _ in name:
            if _[0].isupper():
                tmp.append(_)
                break
            tmp.append(_)

        return tmp

    # Recursively check imports
    while files:
        f = files.pop()
        checked.add(f)
        if os.path.isfile(f) and f.endswith(".java"):
            with open(f, "r") as _f:
                for line in _f:
                    line = line.strip()
                    if line.startswith("import"):
                        line = line.removeprefix("import ").removeprefix("static ")
                        check = line.split(".")
                        if check[0] == "sqlancer":
                            # Internal deps' classes, will be further analysed
                            cls = reduce_main_class(check)
                            file_path = base_dir+ "/".join(cls).removesuffix(";") + ".java"
                            if file_path not in checked:
                                files.append(file_path)
                        elif check[0] == "java":
                            # Ignore Java Dep
                            continue
                        else:
                            # External deps' classes
                            cls = reduce_main_class(check)
                            external_deps.add(".".join(cls).removesuffix(";"))
                    elif line.startswith("package"):
                        # Include all java files entire package in the same folder (under same package)
                        # This is done because mvn can only include folders but not internal packages
                        package = line.removeprefix("package ").removesuffix(";")
                        packages.add(package)
                        package_path = base_dir + package.replace(".", "/")
                        file_paths = [os.path.join(package_path, i) for i in os.listdir(package_path) if i.endswith(".java")]
                        for file_path in file_paths:
                            if file_path not in checked:
                                files.append(file_path)
                    elif not line:
                        continue
                    else:
                        break
        elif build_all and os.path.isdir(f): # For nested yugabyte
            file_paths = [os.path.join(f, i) for i in os.listdir(f)]
            for file_path in file_paths:
                if file_path not in checked:
                    files.append(file_path)

    return packages, external_deps

class Node:
    def __init__(self):
        self.is_marked = False
        self.children = set()

    def add_child(self, child_node):
        self.children.add(child_node)

    def __repr__(self):
        return f"Node(marked={self.is_marked}, children={len(self.children)})"

def get_mvn_dependencies_tree():
    """Process current pom.xml's dependency tree"""
    subprocess.run(['mvn', 'dependency:tree', '-DoutputFile=./package/tree.txt', '-DoutputType=tgf'], stdout = subprocess.DEVNULL)

    dep_node_map = {}
    id_dep_map = {}
    edge_map = {}
    with open("./package/tree.txt", "r") as f:
        buildNode = True
        for line in f:
            if line == "#\n":
                buildNode = False
                continue

            if buildNode:
                id, dep = line.split(" ")
                dep = ":".join(dep.split(":")[:4])
                id_dep_map[id] = dep
                dep_node_map[dep] = Node()
            else:
                par, chd, scope = line.split(" ")
                if scope == "compile":
                    dep_node_map[id_dep_map[par]].add_child(dep_node_map[id_dep_map[chd]])

    return dep_node_map

def list_classes_in_jar(jar_path):
    """Helper to find all classes in a jar"""
    class_names = []
    with zipfile.ZipFile(jar_path, 'r') as jar:
        for file in jar.namelist():
            if file.endswith(".class"):
                class_name = file.replace("/", ".")[:-6]
                class_names.append(class_name)
    return class_names

def find_maven_metadata(jar_path):
    """Helper to find maven metadata from jar internals"""
    with zipfile.ZipFile(jar_path, 'r') as jar:
        for file in jar.namelist():
            if file.endswith("pom.properties"):
                with jar.open(file) as pom_file:
                    props = {}
                    for line in pom_file:
                        decoded_line = line.decode().strip()
                        if "=" in decoded_line:
                            key, value = decoded_line.split("=", 1)
                            props[key.strip()] = value.strip()
                    return props
    return None

def find_maven_metadata_by_pom(jar_path):
    """Helper to find maven metadata in pom if cannot find in jar"""
    folder = "/".join(jar_path.split("/")[:-1])
    namelist = os.listdir(folder)

    for name in namelist:
        if name.endswith(".pom"):
            tree = ET.parse(os.path.join(folder, name))

            maven_info = {}
            group_id = tree.getroot().find("mvn:groupId", ns)
            artifact_id = tree.getroot().find("mvn:artifactId", ns)
            version = tree.getroot().find("mvn:version", ns)

            if group_id is None: # POM has no ns, thanks duckdb pom
                group_id =  tree.getroot().find("groupId")
                artifact_id = tree.getroot().find("artifactId")
                version = tree.getroot().find("version")

            if group_id is None: # POM has parent instead, thanks presto
                group_id =  tree.getroot().find("mvn:parent/mvn:groupId", ns)
                artifact_id = tree.getroot().find("mvn:parent/mvn:artifactId", ns)
                version = tree.getroot().find("mvn:parent/mvn:version", ns)

            maven_info['groupId'] = group_id.text
            maven_info['artifactId'] = artifact_id.text
            maven_info['version'] = version.text

            return maven_info

    return None

def build_class_maven_map(classpaths):
    """Helper to build map of class to its maven dependency"""
    class_map = {}

    for jar in classpaths:
        maven_info = find_maven_metadata(jar)
        classes = list_classes_in_jar(jar)

        if not maven_info:
            maven_info = find_maven_metadata_by_pom(jar)

        if not maven_info:
            raise Exception(f"CANNOT FIND MAVEN INFO FOR {jar}")

        for cls in classes:
            class_map[cls] = f"{maven_info['groupId']}:{maven_info['artifactId']}:jar:{maven_info['version']}"

    return class_map

def extract_classes():
    """Extracts out a mapping of every class to its maven dependency"""
    subprocess.run(['mvn', 'dependency:build-classpath', '-Dmdep.outputFile=./package/cp.txt'], stdout = subprocess.DEVNULL)

    classpaths = None
    with open("./package/cp.txt", "r") as f:
        classpaths = f.readline().strip().split(":")

    class_dep_map = build_class_maven_map(classpaths)

    return class_dep_map

def mark_nodes(dep_node_map, ext_deps, cls_dep_map):
    """Mark dependency node as needed by the specific sqlancer build"""

    def get_dep(cls):
        if cls not in cls_dep_map:
            raise Exception(f"CANNOT FIND CLASS {cls}")

        return cls_dep_map[cls]

    for c in ext_deps:
        dep = get_dep(c)
        stack = [dep_node_map[dep]]

        while stack:
            cur = stack.pop()
            if cur.is_marked:
                continue
            else:
                cur.is_marked = True
                stack.extend(cur.children)

    return dep_node_map

def prune_tree(dep_node_map):
    """Reduce to only the top level important marked dependencies"""
    marked_nodes = []
    childrens = []

    for dep, node in dep_node_map.items():
        if node.is_marked:
            marked_nodes.append((dep, node))
            childrens.extend(node.children)

    top_level_deps = []
    for dep, node in marked_nodes:
        if node not in childrens:
            top_level_deps.append(dep)

    return top_level_deps

def remove_mvn_deps(tree):
    """Remove all mvn dependencies for easier re-insertion"""
    root = tree.getroot()
    dep_node = root.find("./mvn:dependencies", ns)
    root.remove(dep_node)

def insert_mvn_deps(tree, mvn_metadata, db_name):
    """Insert relevant mvn dependencies identified"""
    profile = tree.getroot().find("./mvn:profiles/mvn:profile[mvn:id='Isolate']", ns)

    db_dependencies = ET.Element("dependencies")
    for dep in mvn_metadata:
        group_id, artifact_id, _, version = dep.split(":")
        db_dependency = ET.Element("dependency")
        ET.SubElement(db_dependency, 'groupId').text = group_id
        ET.SubElement(db_dependency, 'artifactId').text = artifact_id
        ET.SubElement(db_dependency, 'version').text = version
        db_dependencies.append(db_dependency)

    # Also add specific db jdbc
    if db_name is not None:
        group_id, artifact_id, version =jdbc_map[db_name]
        check = f"{group_id}:{artifact_id}:jar:{version}"

        if check not in mvn_metadata: # In case already added
            db_dependency = ET.Element("dependency")
            ET.SubElement(db_dependency, 'groupId').text = group_id
            ET.SubElement(db_dependency, 'artifactId').text = artifact_id
            ET.SubElement(db_dependency, 'version').text = version
            ET.SubElement(db_dependency, 'scope').text = 'runtime'
            db_dependencies.append(db_dependency)

    profile.append(db_dependencies)

def insert_internal_deps(tree, packages):
    """Includes only the files needed"""
    profile = tree.getroot().find("./mvn:profiles/mvn:profile[mvn:id='Isolate']", ns)
    plugin = profile.find("./mvn:build/mvn:plugins/mvn:plugin", ns)
    conf = ET.SubElement(plugin, "configuration")
    includes = ET.SubElement(conf, "includes")

    for package in packages:
        p = ET.Element("include")
        p.text = package.replace(".", "/") + "/*.java"
        includes.append(p)

def save_and_build(tree):
    """Update changes and run building"""
    ET.indent(tree, '  ')
    tree.write('pom.xml', encoding="utf-8", xml_declaration=True)

    subprocess.run(['mvn', 'clean', 'package', '-DskipTests', "-PIsolate"])

def main():
    if len(sys.argv) > 3:
        print("Usage : python3 package.py (db_name / full)")
        return -1

    if len(sys.argv) == 2 and sys.argv[1] == "full":
        subprocess.run(['mvn', 'clean', 'package', '-DskipTests'])
        return 0

    # Process dependencies
    os.makedirs("./package", exist_ok=True)

    if len(sys.argv) == 1:
        # Core build
        db_name = None
        packages, mvn_deps = build_dependencies(f"./src/sqlancer", build_all=False)
    else:
        # Specific db build
        db_name = sys.argv[1]
        packages, mvn_deps = build_dependencies(f"./src/sqlancer/{db_name}")

    dep_node_map = get_mvn_dependencies_tree()
    class_dep_map = extract_classes()
    dep_node_map = mark_nodes(dep_node_map, mvn_deps, class_dep_map)
    mvn_deps = prune_tree(dep_node_map)

    # Edit pom.xml
    src_pom = "pom.xml"
    tmp_pom = "tmp.xml"
    tree = ET.parse(src_pom)
    shutil.copy(src_pom, tmp_pom)
    remove_mvn_deps(tree)
    insert_mvn_deps(tree, mvn_deps, db_name)
    insert_internal_deps(tree, packages)

    # Build & cleanup
    save_and_build(tree)
    shutil.move(tmp_pom, src_pom)

    return 0

if __name__ == "__main__":
    main()
