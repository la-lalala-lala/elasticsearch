# elasticsearch

#### 使用docker安装

1. 拉取镜像
```shell script
  docker pull registry.cn-hangzhou.aliyuncs.com/elasticsearch/elasticsearch:7.4.0
```

2.先启动简洁版的容器(主要是提前打探好容器内的目录，方便复制到宿主机)
```shell script
    docker run --name elasticsearch -p 9200:9200 -p 9300:9300 \
    -e "discovery.type=single-node" \
    -e ES_JAVA_OPTS="-Xms64m -Xmx128m" \
    -e "TAKE_FILE_OWNERSHIP=true" \
    -d dd156dd42341
```
            
3.将docker容器内部的目录复制到宿主机
```shell script
    docker cp 886d56ccb3a2:/usr/share/elasticsearch/config/ /opt/elasticsearch
    docker cp 886d56ccb3a2:/usr/share/elasticsearch/data/ /opt/elasticsearch
    docker cp 886d56ccb3a2:/usr/share/elasticsearch/logs/ /opt/elasticsearch
    docker cp 886d56ccb3a2:/usr/share/elasticsearch/plugins/ /opt/elasticsearch
```

            
4.启动 elasticsearch 容器
```shell script
    docker run --name elasticsearch -p 9200:9200 -p 9300:9300 \
    -e "discovery.type=single-node" \
    -e ES_JAVA_OPTS="-Xms64m -Xmx128m" \
    -e "TAKE_FILE_OWNERSHIP=true" \
    -v /opt/elasticsearch/config:/usr/share/elasticsearch/config \
    -v /opt/elasticsearch/data:/usr/share/elasticsearch/data \
    -v /opt/elasticsearch/plugins:/usr/share/elasticsearch/plugins \
    -v /opt/elasticsearch/logs:/usr/share/elasticsearch/logs \
    -d dd156dd42341
```

5.检查是否启动成功
```shell script
[root@VM-0-9-centos elasticsearch]# docker ps
CONTAINER ID   IMAGE          COMMAND                  CREATED          STATUS          PORTS                                            NAMES
532b3a640c5d   dd156dd42341   "/usr/local/bin/dock…"   30 seconds ago   Up 29 seconds   0.0.0.0:9200->9200/tcp, 0.0.0.0:9300->9300/tcp   elasticsearch
```

6.打开web9200端口，确认启动成功
```json
{
  "name" : "532b3a640c5d",
  "cluster_name" : "docker-cluster",
  "cluster_uuid" : "QOOJ_1OXT9WfeO2KZ9VWMg",
  "version" : {
    "number" : "7.4.0",
    "build_flavor" : "default",
    "build_type" : "docker",
    "build_hash" : "22e1767283e61a198cb4db791ea66e3f11ab9910",
    "build_date" : "2019-09-27T08:36:48.569419Z",
    "build_snapshot" : false,
    "lucene_version" : "8.2.0",
    "minimum_wire_compatibility_version" : "6.8.0",
    "minimum_index_compatibility_version" : "6.0.0-beta1"
  },
  "tagline" : "You Know, for Search"
}
```

7.安装Kibana
```shell script
# 拉取镜像
docker pull kibana:7.4.0
docker run -d -e ELASTICSEARCH_HOSTS=http://118.24.198.239:9200 -p 8080:5601 --name kibana  0328df36f79f
```

