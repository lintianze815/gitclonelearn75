#!/usr/bin/env bash
# GitPocket_75 一键编译运行脚本
# 用法：bash run.sh
set -e

if ! command -v javac >/dev/null 2>&1; then
    echo "未检测到 javac，请先安装 JDK 11 及以上版本。"
    exit 1
fi

echo "正在编译……"
rm -rf out
mkdir -p out
javac -encoding UTF-8 -d out $(find src -name "*.java")
echo "编译完成，启动程序。"
java -cp out com.cuit.gitpocket.Main
