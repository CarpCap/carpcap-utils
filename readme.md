# 自用工具类

maven 声明快照仓库地址

```
    <repositories>
        <repository>
            <name>Central Portal Snapshots</name>
            <id>central-portal-snapshots</id>
            <url>https://central.sonatype.com/repository/maven-snapshots/</url>
            <releases>
                <enabled>false</enabled>
            </releases>
            <snapshots>
                <enabled>true</enabled>
            </snapshots>
        </repository>
    </repositories>
```

maven 引入依赖

```
        <dependency>
            <groupId>com.carpcap</groupId>
            <artifactId>carpcap-utils</artifactId>
            <version>0.0.1-SNAPSHOT</version>
        </dependency>
```