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

8.Kibana测试
## 检索索引

GET bank/_search
{
  "query": {
    "match_all": {}
  },
  "sort": [
    {
      "balance": {
        "order": "desc"
      }
    }
  ],
  "from": 0,
  "size": 10,
  "_source": ["balance","firstname",""]
}


## 分词匹配

GET bank/_search
{
  "query": {
    "match": {
      "address": "Place"
    }
  }
}

## 完整匹配

GET bank/_search
{
  "query": {
    "match_phrase": {
      "address": "mill road"
    }
  }
}

## 多词匹配

GET bank/_search
{
  "query": {
    "multi_match": {
      "query": "mill",
      "fields": ["address","city"]
    }
  }
}

## 布尔查询(must:必须满足;must_not:必须不满足;should:尽量匹配，匹配不上也行)

GET bank/_search
{
  "query": {
    "bool": {
      "must": [
        {"match": {
          "gender": "M"
        }},
        {"match": {
          "address": "Mill"
        }}
      ],
      "must_not": [
        {"match": {
          "age": "28"
        }}
      ],
      "should": [
        {"match": {
          "lastname": "Wallace"
        }}
      ]
    }
  }
}

## 范围查询

GET bank/_search
{
  "query": {
    "bool": {
      "must": [
        {"range": {
          "age": {
            "gte": 10,
            "lte": 30
          }
        }}
      ]
    }
  }
}

## 利用filter进行过滤查询，此时不会计算相关得分，直接进行过滤

GET bank/_search
{
  "query": {
    "bool": {
      "filter": {
        "range": {
          "age": {
            "gte": 10,
            "lte": 30
          }
        }
      }
    }
  }
}

## 利用term进行精准匹配，适合于数值及布尔型(非text)，对于字符串进行全文匹配，不是很适合

GET bank/_search
{
  "query": {
    "term": {
      "age": {
        "value": 28
      }
    }
  }
}

## 利用字段.keyword进行精准匹配，必须完全相等

GET bank/_search
{
  "query": {
    "match": {
      "address.keyword": "789 Madison"
    }
  }
}

## 利用match_phrase进行全文匹配，此时的查询条件不会被分词，只要含有这个完整的条件才会检索到

GET bank/_search
{
  "query": {
    "match_phrase": {
      "address": "789 Madison"
    }
  }
}

## 搜索address中包含mill的所有人的年龄分布及平均年龄

GET bank/_search
{
  "query": {
    "match": {
      "address": "mill"
    }
  },
  "aggs": {
    "groupAge": {
      "terms": {
        "field": "age",
        "size": 10
      }
    },
    "ageAvg":{
      "avg": {
        "field": "age"
      }
    }
  }
}

## 按照年龄进行分组聚合，并且统计这些年龄段的这些人的平均薪资

GET bank/_search
{
  "query": {
    "match_all": {}
  },
  "size": 0, 
  "aggs": {
    "groupAge": {
      "terms": {
        "field": "age",
        "size": 100
      },
      "aggs": {
        "ageAvg": {
          "avg": {
            "field": "age"
          }
        }
      }
    }
  }
}

## 查出所有年龄分布，并且这些年龄段中男女的平均薪资，以及这个年龄段的整体平均薪资

GET bank/_search
{
  "query": {
    "match_all": {}
  },
  "size": 0,
  "aggs": {
    "groupAge": {
      "terms": {
        "field": "age",
        "size": 100
      },
      "aggs": {
        "groupGender": {
          "terms": {
            "field": "gender.keyword",
            "size": 10
          },
          "aggs": {
            "avgBalance": {
              "avg": {
                "field": "balance"
              }
            }
          }
        },
        "avgBalance":{
          "avg": {
            "field": "balance"
          }
        }
      }
    }
  }
}

GET user/_mapping

## 创建索引，并指定映射

PUT /user
{
  "mappings": {
    "properties": {
      "name":{"type": "text"},
      "account":{"type": "keyword"},
      "passwprd":{"type": "keyword"},
      "sex":{"type": "boolean"},
      "age":{"type": "integer"},
      "email":{"type": "keyword"},
      "phone":{"type": "keyword"}
    }
  }
}

## 添加新的字段映射

PUT /user/_mapping
{
  "properties":{
    "schools":{
      "type":"nested"
    }
  }
}



## 测试ik分词器

POST _analyze
{
  "analyzer": "ik_max_word",
  "text":"国家电网公司"
}

