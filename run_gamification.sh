#!/bin/bash
export MYSQL_HOST=$(docker inspect codemad_db | python ip.py)
cd gamification-impl
mvn spring-boot:run
