#!/usr/bin/env pwsh

<#
.SYNOPSIS
复制项目的文件到对应docker路径，便于一键生成镜像。
#>

# 复制SQL文件
Write-Host "begin copy sql"
Copy-Item -Path "../sql/ry_20250425.sql" -Destination "./mysql/db" -Force
Copy-Item -Path "../sql/ry_config_20250224.sql" -Destination "./mysql/db" -Force

# 复制HTML文件
Write-Host "begin copy html"
if (Test-Path "../ruoyi-ui/dist") {
    Remove-Item -Path "./nginx/html/dist" -Recurse -Force -ErrorAction SilentlyContinue
    New-Item -ItemType Directory -Path "./nginx/html/dist" -Force | Out-Null
    Copy-Item -Path "../ruoyi-ui/dist/*" -Destination "./nginx/html/dist" -Recurse -Force
} else {
    Write-Host "Warning: ../ruoyi-ui/dist directory not found"
}

# 复制JAR文件
Write-Host "begin copy ruoyi-gateway"
Copy-Item -Path "../ruoyi-gateway/target/ruoyi-gateway.jar" -Destination "./ruoyi/gateway/jar" -Force

Write-Host "begin copy ruoyi-auth"
Copy-Item -Path "../ruoyi-auth/target/ruoyi-auth.jar" -Destination "./ruoyi/auth/jar" -Force

Write-Host "begin copy ruoyi-visual"
Copy-Item -Path "../ruoyi-visual/ruoyi-monitor/target/ruoyi-visual-monitor.jar" -Destination "./ruoyi/visual/monitor/jar" -Force

Write-Host "begin copy ruoyi-modules-system"
Copy-Item -Path "../ruoyi-modules/ruoyi-system/target/ruoyi-modules-system.jar" -Destination "./ruoyi/modules/system/jar" -Force

Write-Host "begin copy ruoyi-modules-file"
Copy-Item -Path "../ruoyi-modules/ruoyi-file/target/ruoyi-modules-file.jar" -Destination "./ruoyi/modules/file/jar" -Force

Write-Host "begin copy ruoyi-modules-job"
Copy-Item -Path "../ruoyi-modules/ruoyi-job/target/ruoyi-modules-job.jar" -Destination "./ruoyi/modules/job/jar" -Force

Write-Host "begin copy ruoyi-modules-gen"
Copy-Item -Path "../ruoyi-modules/ruoyi-gen/target/ruoyi-modules-gen.jar" -Destination "./ruoyi/modules/gen/jar" -Force

Write-Host "copy completed"