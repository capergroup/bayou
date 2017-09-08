#!/bin/bash

# Copyright 2017 Rice University
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
rm -rf $SCRIPT_DIR/tmp
mkdir $SCRIPT_DIR/tmp


cd ../maven_3_3_9/bayou
VER="$(printf 'VERSION=${project.version}\n0\n' | mvn org.apache.maven.plugins:maven-help-plugin:2.1.1:evaluate | grep '^VERSION' | cut -c9-)" # get the project version number... e.g. 1.1.0
mvn clean package
cp target/bayou-$VER-jar-with-dependencies.jar $SCRIPT_DIR/tmp
cp -r ../../../src/main/python $SCRIPT_DIR/tmp
cp -r ../../../src/main/resources $SCRIPT_DIR/tmp
cp ../../../src/main/bash/binary_release/*.sh $SCRIPT_DIR/tmp
cp -r target/bayou-$VER-jar-with-dependencies.jar $SCRIPT_DIR/tmp
cp -r ../../../doc/external/example_inputs $SCRIPT_DIR/tmp
cd $SCRIPT_DIR/tmp
mv bayou-$VER-jar-with-dependencies.jar bayou-$VER.jar

zip -r bayou-$VER.zip *
mv bayou-$VER.zip $SCRIPT_DIR

rm -rf $SCRIPT_DIR/tmp
