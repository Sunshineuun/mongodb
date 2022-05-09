> Mongodb的实现

1. Mongodb save findById 的实现。

2. Mongodb agg 聚合操作的实现

   -  聚合操作完成。但是怎么找到最大A字段值的这条记录的其它字段。

   - agg 的操作，mongo 中的操作逻辑是对结果进行`Aggregation`一些类操作，操作顺序由定义时的先后顺序决定。并且将结果集执行下一个`Aggregation`。

     > db.getCollection('order').aggregate([
     >
     > {"$match":{"shop_name":"A"}},
     >
     > {"$sort":{"create_time":1}},   ## 1表示升序，-1表示降序
     >
     > {"$group":{"_id":"$userId",            ## 类似MySql的group by userId
     >
     > "create_time ":{"$first":"$create_time "},
     >
     > "price":{"$first":"$price"} }},
     >
     > {"$limit":1}  ## 查询结果返回1条
     >
     > ])
     >
     > 如上述代码的例子：mongo 会依次执行，数组中的顺序不同，带来的结果也将是不同的。
     > 
3. 可以将 Mongo Compass 将查询语句转换为 Java 代码
4. MongoTemplate 中的 insertAll 和 bulkOps 的探索。
```java
/**
 * inserAll he  bulkOps。<br>
 * 1. 在只插入的情况下，insertAll 可以达到 1000w/h，bulkOps 可以达到1500w/h。（具体还要看文本量的大小）<br>
 * 2. insertAll 只支持插入，bulkOps支持更新。<br>
 */
interface MongoTemplate{
  
  void insertAll();
  void bulkOps();
}
```