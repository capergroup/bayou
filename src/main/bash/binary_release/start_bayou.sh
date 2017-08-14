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

trap "exit" INT TERM # trap lines make it so taht when this script terminates the background java process does as well
trap "kill 0" EXIT

mkdir -p logs

BAYOU_JAR="$(ls *.jar)"
java -DconfigurationFile=resources/conf/apiSynthesisServerConfig.properties -Dlog4j.configurationFile=resources/conf/apiSynthesisServerLog4j2.xml -jar $BAYOU_JAR &

if [ $# -eq 0 ]
  then
    logs_dir="logs"
  else
    logs_dir=$1
fi

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
export PYTHONPATH=python
python3 python/bayou/server/ast_server.py --save_dir "$SCRIPT_DIR/resources/model" --logs_dir $logs_dir
