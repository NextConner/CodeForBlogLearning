### Mysql 相关（1）-基本了解
>  数据库底层存储的核心都是基于数据模型的，先关注它的数据模型，才能从理论上分析出这个数据库的适用场景

---

#### 基本架构

- 

![白板](img\白板.jpg)



##### 一条SQL语句的执行过程

- **连接**：客户端通过各自的账号密码连接到SQL服务端
  - 如果连接数过多会导致内存飙升导致数据库OOM被，因此既要**限制创建的连接数**，也要**复用已有连接**
  
  - *show processonlist* 可以查看当前连接状态，默认由参数*wait_timeout* 控制连接断开的时间
  - Mysql 5.7 之后，想复用连接，可以通过 **mysql_reset_connection** 初始化连接资源，不需要重新鉴权
  
-  **鉴权**：预先校验当前用户是否有访问目标表或者执行目标操作的权限
  - 无权访问表或者无权执行操作
- **查询缓存**：如果表开启了查询缓存，优先命中缓存会直接返回结果
  - 表的**更新语句**会导致该表的查询缓存失效，因此写多读少的表不适合开启查询缓存
  - ***MYSQL8.0 删除了这个功能***
- **分析器**：进行词法分析和语法分析
  - 这一部分进行的工作是将SQL语句按照规则分析是否合法
  - 如果**访问了表不存在的字段**，也会在这一阶段提示错误，而不是在执行阶段
- **优化器**：生成执行计划，选择最佳索引
  - 执行计划表示当前语句的执行成本等信息：扫描行数，是否使用了索引，连接表信息等
  - 也可以通过 *EXPLAIN* 关键字查看一条语句的执行计划
  - 多表连接查询会决定最优的连接顺序
  - 表存在多个索引也会决定使用最佳索引执行



---

#### Mysql 更新语句执行过程

##### Redo Log 和 Binlog 

- Mysql 数据最终会持久化到磁盘，但是磁盘IO的效率相对内存是很低的，因此如果每次执行更新语句都直接落盘会导致效率低下；
- 解决的办法就是先**快速**记录需要执行的更新语句，然后某一时刻统一落盘
- 这个过程在Mysql 中就是**先写 Redo Log**， 这是属于 InnoDB 引擎独有的
  - InnoDB Redo Log 是固定大小为 1GB，它记录的是执行的操作：给名为 admin 的用户修改别名叫 tom 
  - 可以类比为一个**环形数组**，从头部**write_position** 开始写入，**check_point** 将该位置更新到 Binlog ，然后擦除，写入新数据
  - 可以简单理解为具有头尾指针的数组，如果头指针追上了尾指针，复位到起始位置循环 写
- Server 层则是对应有 **Binlog** ，与 RedoLog 不同，binlog 是追加写，写满自动切换下一个文件，不会覆盖
- **执行过程**：
  - 执行器查找ID=2 的行，从内存加载或者从磁盘加载再写入到内存
  - 执行器对数据行进行操作，例如 N -> N+1
  - 引擎将数据更新到内存，同时记录redo log ，此时 redo log 的记录处于 **prepare** 状态（事务未提交）
  -  执行其生成这个操作的 binlog ，并落盘
  - 执行器调用引擎的提交事务接口，引擎把 redo log 状态更新为 **commit** ，更新完成
  - 写 redo log 的 prepare -> commit  过程，是两阶段的提交



---

#### 事务

##### 基本特性

- 原子性：**Atomicity** ，事务操作不可拆分
- 一致性：**Consistency**，事务开始之前和执行之后，不会破坏数据库的完整性，写入资料符合预设约束
- 隔离性：**Isolation** ，允许多个事务并发进行读写修改，根据隔离级别控制事务数据之间的访问
- 持久性：**Durability**，事务执行成功，写入数据就是永久的

##### 隔离性

###### 隔离级别

- **read uncommited**：读未提交，事务未提交时的变更也可以被其他事务读取到
  - 可能导致脏读，不可重复读，幻读
- **read commited**：读已提交，事务提交之后，才可以被其他事务读取到自身的修改
  - 可能导致不可重读
- **repeatable read**：可重复读，事务执行期间看到的数据和事务启动时看到的数据一致
  - 意味着事务以启动那一刻的数据快照为准，不会因为执行过程中其他事务的提交而读到新的数据结果
- **serializable**：串行化，读写操作都会加锁，事务顺序执行，不允许并发事务



###### 事务隔离的实现 - TODO

- 当前读：
  - 事务在执行更新操作时，执行一次**当前读**，获取**已提交**的最新数据版本，再去执行更新，避免丢失其他事务的已提交修改

- 快照读
  - 在**可重复读，读提交**的隔离级别下，事务的读基于创建的一致性视图读，前者在事务启动时创建；后者每次执行语句之前创建

- MVCC
  - 对于一行数据，会有允许并发事务的访问，而存在多个版本，每个事务ID+自身数据构成一个唯一版本：row_trx_id 




---

#### 索引
##### 是什么

- 为了加快数据查询速度的一种数据结构
- 以N叉树的形式存储，主键索引路径节点存储主键ID，叶子节点存储数据行，同时为了维持范围查询的速度，叶子节点还维护了指向相邻节点的引用，因此主键索引也叫聚簇索引；
- 非主键索引称为*二级索引*，叶子节点维护的主键所在的位置，然后再通过主键查找所在的数据行，这个过程也叫做回表
- 回表会导致多一次查询过程，解决的这个问题可以考虑建立联合索引，在索引覆盖查询数据的情况下，可以直接通过索引表查到所需的数据（例如建立name,address,email 的联合索引，SELECT address,email WHERE name = 'test' 就可以直接通过查询索引表获得目标数据 ）或者将被索引字段作为主键，就是尽量使用主键索引

##### InnoDB的索引模型

- 表根据主键顺序以索引形式存放的，这种存储方式的表称为索引组织表，数据都是存储在**B+树**中

##### 页分裂与页合并

- B+树为了维护数据的有序性，在极端情况下，如果插入数据时当前页已满，需要重新申请一个页，并将插入位置之后的部分数据挪动到新的数据页，这个过程就是页分裂，性能会受到影响，而且分裂后的页各自存储的数据量下降，空间利用率降低
- 反之，由于删除数据重新调整树结构，会发生页合并
- 关联问题分析：**自增主键的使用是绝对的吗？**
     - 使用自增主键的情况下，可以认为每次插入都是都是顺序追加，因此不会发生页分裂的情况；
     - 但是业务字段作为主键，不一定遵循绝对自增的规律，无法保证绝对有序（例如身份证号）

##### 覆盖索引

- 只通过搜索索引树就可以获得目标字段信息时，就可以不用回表
- 是提高查询性能常用的一个手段

##### 最左前缀

- 多个字段的联合索引，也可以覆盖最左侧单个索引或顺序多个索引的查询条件

##### 索引下推

- 在满足最左前缀匹配的前提下，会按照索引到的主键，一次次回表查询数据行是否符合完整的索引条件
- `Mysql5.6` 引入索引下推 ， 如果检索字段都是索引字段，且满足最左匹配， 会先判断索引数的节点是否满足索引查询条件，最后再回表查询符合条件的数据行，减少回表次数

---

#### 锁

##### 锁分类 ： 根据加锁范围

###### 全局锁：整库锁定

-  *全局锁命令*：`Flush tables with read lock（FTWRL）`, 会进入整库只读状态，阻塞所有的：**更新数据，更新表定义，事务提交等语句**
- 使用场景：
  - **全库逻辑备份**；
  - 另一种方式是通过在**支持一致性读的引擎**上执行 `mysqldump -single-transaction` ，在导数据之前启动一个事务，确保拿到一致性视图，同时由于**MVCC**，数据也可以正常更新
  - `set global readonly=true`同样可以全库只读锁定数据更新，但是不优先考虑这种方式
    - 在框架或者系统中，可能存在通过是否只读判断主从库的逻辑
    - `FTWRL` 执行后如果客户端异常断开，全局锁会自动释放；而 `readonly` 方式设置的全局锁不会，风险较高

###### 表级锁：

- **表锁**：
  - `lock tables ... read/write` 语句锁定表的读或者读写操作，通过 `unlock tables` 解锁
  - 但是`lock tables` 除了会限制其他线程可执行的操作，还会限制本线程的操作
- **元数据锁(meat data lock : MDL)**
  - 也分读锁和写锁，MDL锁不需要显式加锁，对表执行DML语句时，会自动加MDL读锁，读锁不互斥；
  - 但是执行DDL语句会加上MDL写锁，确保更新表结构是个互斥的操作
  - MDL读写锁之间互斥，因此要小心并发事务的DML和DDL，可能因为MDL读写锁互斥而导致死锁;；避免这种情况，需要处理可能出现的长事务，避免事务长期持有MDL读锁，导致MDL写锁 block；但如果是热点表，访问频繁，需要考虑多次超时重试获取MDL写锁，避免阻塞业务操作

###### 行级锁

- **两段锁协议** ： InnoDB 引擎中，行锁是需要的时候获取，但是需要等到**事务提交之后才释放**

###### 死锁和死锁检测

- **死锁**：并发事务如果持有对方需要的行锁且不释放，就容易导致死锁
  - 可以通过`innodb_lock_wait_timeout` 设置等待锁超时时间，超时放弃获得锁；但是需要考虑合理的超时时间，等待时间过长影响业务吞吐量；等待超时时间太短，可能误伤单纯的等待锁而不是死锁
  - 也可以设置`inodb_deadlock_detect=on`发起死锁检测 ，每个发起事务的线程都会检测是否会由于自己的加入导致死锁；但是在大量线程并发的情况下，会因为太多的检测操作消耗CPU，虽然不会发生死锁，但业务效率同样会受到影响
  - 特殊情况下，可以将并发大的记录行拆分成多份，提高并发度降低锁竞争，类似于分段锁的概念

---

#### 事务隔离

##### MVCC ： 多版本并发控制

- InnoDB 引擎中，每个事务都会被分配一个严格递增的事务ID`transaction_id`；
- 同时每行数据可能是存在多个版本的，每次事务更新数据的时候都会成一个新的数据版本同时绑定当前事务的ID，记为 `ROW_TRX_ID`
- 最终的结果就是：ROW A 可能存在 `A_trx_id1 , A_trx_id_2, A_trx_id_N` 多个版本的情况，它们会被记录到 `undo log`中用做事务回滚

##### 事务快照

###### 可重复读

- 在此隔离级别下，**事务启动时**会创建一个**一致性视图**，只承认自身生成的数据版本或早于自身事务创建的**最后**一个版本的数据
- InnoDB 为每个事务构造了一个数组，保存`启动了但尚未提交的事务ID`，版本可见性就是基于事务自身的 `row_trx_id`与这个数组的比较来确定的
  - 活跃事务数组：[90，91，92，93，94，95]
  - 当前事务id : 93
  - 如果当前事务执行更新语句时，94事务以提交，当前事务执行一次**当前读**，获得数据的最新已提交版本，即便该事务在自身之后创建；加锁的select 语句也可以达到**当前读**的效果；（如果不进行当前读，则会丢失94事务的修改）

######  读提交

- 此隔离级别下，事务内每个语句执行前，都会计算一个新的**一致性视图**，获得当前数据**已提交的最新版本**



