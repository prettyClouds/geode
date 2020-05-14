/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package org.apache.geode.redis.internal;

import org.apache.geode.redis.internal.ParameterRequirements.EvenParameterRequirements;
import org.apache.geode.redis.internal.ParameterRequirements.ExactParameterRequirements;
import org.apache.geode.redis.internal.ParameterRequirements.MaximumParameterRequirements;
import org.apache.geode.redis.internal.ParameterRequirements.MinimumParameterRequirements;
import org.apache.geode.redis.internal.ParameterRequirements.ParameterRequirements;
import org.apache.geode.redis.internal.ParameterRequirements.SpopParameterRequirements;
import org.apache.geode.redis.internal.ParameterRequirements.UnspecifiedParameterRequirements;
import org.apache.geode.redis.internal.executor.AuthExecutor;
import org.apache.geode.redis.internal.executor.DBSizeExecutor;
import org.apache.geode.redis.internal.executor.DelExecutor;
import org.apache.geode.redis.internal.executor.EchoExecutor;
import org.apache.geode.redis.internal.executor.ExistsExecutor;
import org.apache.geode.redis.internal.executor.ExpireAtExecutor;
import org.apache.geode.redis.internal.executor.ExpireExecutor;
import org.apache.geode.redis.internal.executor.FlushAllExecutor;
import org.apache.geode.redis.internal.executor.KeysExecutor;
import org.apache.geode.redis.internal.executor.PExpireAtExecutor;
import org.apache.geode.redis.internal.executor.PExpireExecutor;
import org.apache.geode.redis.internal.executor.PTTLExecutor;
import org.apache.geode.redis.internal.executor.PersistExecutor;
import org.apache.geode.redis.internal.executor.PingExecutor;
import org.apache.geode.redis.internal.executor.QuitExecutor;
import org.apache.geode.redis.internal.executor.RenameExecutor;
import org.apache.geode.redis.internal.executor.ScanExecutor;
import org.apache.geode.redis.internal.executor.ShutDownExecutor;
import org.apache.geode.redis.internal.executor.TTLExecutor;
import org.apache.geode.redis.internal.executor.TimeExecutor;
import org.apache.geode.redis.internal.executor.TypeExecutor;
import org.apache.geode.redis.internal.executor.UnkownExecutor;
import org.apache.geode.redis.internal.executor.hash.HDelExecutor;
import org.apache.geode.redis.internal.executor.hash.HExistsExecutor;
import org.apache.geode.redis.internal.executor.hash.HGetAllExecutor;
import org.apache.geode.redis.internal.executor.hash.HGetExecutor;
import org.apache.geode.redis.internal.executor.hash.HIncrByExecutor;
import org.apache.geode.redis.internal.executor.hash.HIncrByFloatExecutor;
import org.apache.geode.redis.internal.executor.hash.HKeysExecutor;
import org.apache.geode.redis.internal.executor.hash.HLenExecutor;
import org.apache.geode.redis.internal.executor.hash.HMGetExecutor;
import org.apache.geode.redis.internal.executor.hash.HMSetExecutor;
import org.apache.geode.redis.internal.executor.hash.HScanExecutor;
import org.apache.geode.redis.internal.executor.hash.HSetExecutor;
import org.apache.geode.redis.internal.executor.hash.HSetNXExecutor;
import org.apache.geode.redis.internal.executor.hash.HValsExecutor;
import org.apache.geode.redis.internal.executor.hll.PFAddExecutor;
import org.apache.geode.redis.internal.executor.hll.PFCountExecutor;
import org.apache.geode.redis.internal.executor.hll.PFMergeExecutor;
import org.apache.geode.redis.internal.executor.list.LIndexExecutor;
import org.apache.geode.redis.internal.executor.list.LInsertExecutor;
import org.apache.geode.redis.internal.executor.list.LLenExecutor;
import org.apache.geode.redis.internal.executor.list.LPopExecutor;
import org.apache.geode.redis.internal.executor.list.LPushExecutor;
import org.apache.geode.redis.internal.executor.list.LPushXExecutor;
import org.apache.geode.redis.internal.executor.list.LRangeExecutor;
import org.apache.geode.redis.internal.executor.list.LRemExecutor;
import org.apache.geode.redis.internal.executor.list.LSetExecutor;
import org.apache.geode.redis.internal.executor.list.LTrimExecutor;
import org.apache.geode.redis.internal.executor.list.RPopExecutor;
import org.apache.geode.redis.internal.executor.list.RPushExecutor;
import org.apache.geode.redis.internal.executor.list.RPushXExecutor;
import org.apache.geode.redis.internal.executor.pubsub.PsubscribeExecutor;
import org.apache.geode.redis.internal.executor.pubsub.PublishExecutor;
import org.apache.geode.redis.internal.executor.pubsub.PunsubscribeExecutor;
import org.apache.geode.redis.internal.executor.pubsub.SubscribeExecutor;
import org.apache.geode.redis.internal.executor.pubsub.UnsubscribeExecutor;
import org.apache.geode.redis.internal.executor.set.SAddExecutor;
import org.apache.geode.redis.internal.executor.set.SCardExecutor;
import org.apache.geode.redis.internal.executor.set.SDiffExecutor;
import org.apache.geode.redis.internal.executor.set.SDiffStoreExecutor;
import org.apache.geode.redis.internal.executor.set.SInterExecutor;
import org.apache.geode.redis.internal.executor.set.SInterStoreExecutor;
import org.apache.geode.redis.internal.executor.set.SIsMemberExecutor;
import org.apache.geode.redis.internal.executor.set.SMembersExecutor;
import org.apache.geode.redis.internal.executor.set.SMoveExecutor;
import org.apache.geode.redis.internal.executor.set.SPopExecutor;
import org.apache.geode.redis.internal.executor.set.SRandMemberExecutor;
import org.apache.geode.redis.internal.executor.set.SRemExecutor;
import org.apache.geode.redis.internal.executor.set.SScanExecutor;
import org.apache.geode.redis.internal.executor.set.SUnionExecutor;
import org.apache.geode.redis.internal.executor.set.SUnionStoreExecutor;
import org.apache.geode.redis.internal.executor.sortedset.GeoAddExecutor;
import org.apache.geode.redis.internal.executor.sortedset.GeoDistExecutor;
import org.apache.geode.redis.internal.executor.sortedset.GeoHashExecutor;
import org.apache.geode.redis.internal.executor.sortedset.GeoPosExecutor;
import org.apache.geode.redis.internal.executor.sortedset.GeoRadiusByMemberExecutor;
import org.apache.geode.redis.internal.executor.sortedset.GeoRadiusExecutor;
import org.apache.geode.redis.internal.executor.sortedset.ZAddExecutor;
import org.apache.geode.redis.internal.executor.sortedset.ZCardExecutor;
import org.apache.geode.redis.internal.executor.sortedset.ZCountExecutor;
import org.apache.geode.redis.internal.executor.sortedset.ZIncrByExecutor;
import org.apache.geode.redis.internal.executor.sortedset.ZLexCountExecutor;
import org.apache.geode.redis.internal.executor.sortedset.ZRangeByLexExecutor;
import org.apache.geode.redis.internal.executor.sortedset.ZRangeByScoreExecutor;
import org.apache.geode.redis.internal.executor.sortedset.ZRangeExecutor;
import org.apache.geode.redis.internal.executor.sortedset.ZRankExecutor;
import org.apache.geode.redis.internal.executor.sortedset.ZRemExecutor;
import org.apache.geode.redis.internal.executor.sortedset.ZRemRangeByLexExecutor;
import org.apache.geode.redis.internal.executor.sortedset.ZRemRangeByRankExecutor;
import org.apache.geode.redis.internal.executor.sortedset.ZRemRangeByScoreExecutor;
import org.apache.geode.redis.internal.executor.sortedset.ZRevRangeByScoreExecutor;
import org.apache.geode.redis.internal.executor.sortedset.ZRevRangeExecutor;
import org.apache.geode.redis.internal.executor.sortedset.ZRevRankExecutor;
import org.apache.geode.redis.internal.executor.sortedset.ZScanExecutor;
import org.apache.geode.redis.internal.executor.sortedset.ZScoreExecutor;
import org.apache.geode.redis.internal.executor.string.AppendExecutor;
import org.apache.geode.redis.internal.executor.string.BitCountExecutor;
import org.apache.geode.redis.internal.executor.string.BitOpExecutor;
import org.apache.geode.redis.internal.executor.string.BitPosExecutor;
import org.apache.geode.redis.internal.executor.string.DecrByExecutor;
import org.apache.geode.redis.internal.executor.string.DecrExecutor;
import org.apache.geode.redis.internal.executor.string.GetBitExecutor;
import org.apache.geode.redis.internal.executor.string.GetExecutor;
import org.apache.geode.redis.internal.executor.string.GetRangeExecutor;
import org.apache.geode.redis.internal.executor.string.GetSetExecutor;
import org.apache.geode.redis.internal.executor.string.IncrByExecutor;
import org.apache.geode.redis.internal.executor.string.IncrByFloatExecutor;
import org.apache.geode.redis.internal.executor.string.IncrExecutor;
import org.apache.geode.redis.internal.executor.string.MGetExecutor;
import org.apache.geode.redis.internal.executor.string.MSetExecutor;
import org.apache.geode.redis.internal.executor.string.MSetNXExecutor;
import org.apache.geode.redis.internal.executor.string.PSetEXExecutor;
import org.apache.geode.redis.internal.executor.string.SetBitExecutor;
import org.apache.geode.redis.internal.executor.string.SetEXExecutor;
import org.apache.geode.redis.internal.executor.string.SetExecutor;
import org.apache.geode.redis.internal.executor.string.SetNXExecutor;
import org.apache.geode.redis.internal.executor.string.SetRangeExecutor;
import org.apache.geode.redis.internal.executor.string.StrlenExecutor;
import org.apache.geode.redis.internal.executor.transactions.DiscardExecutor;
import org.apache.geode.redis.internal.executor.transactions.ExecExecutor;
import org.apache.geode.redis.internal.executor.transactions.MultiExecutor;
import org.apache.geode.redis.internal.executor.transactions.TransactionExecutor;
import org.apache.geode.redis.internal.executor.transactions.UnwatchExecutor;
import org.apache.geode.redis.internal.executor.transactions.WatchExecutor;

/**
 * The redis command type used by the server. Each command is directly from the redis protocol.
 */
public enum RedisCommandType {

  /***************************************
   *************** Keys ******************
   ***************************************/

  AUTH(new AuthExecutor(), new MinimumParameterRequirements(2)),
  DEL(new DelExecutor(), new MinimumParameterRequirements(2)),
  EXISTS(new ExistsExecutor(), new MinimumParameterRequirements(2)),
  EXPIRE(new ExpireExecutor(), new ExactParameterRequirements(3)),
  EXPIREAT(new ExpireAtExecutor(), new ExactParameterRequirements(3)),
  FLUSHALL(new FlushAllExecutor(), new MaximumParameterRequirements(2)),
  FLUSHDB(new FlushAllExecutor(), new MaximumParameterRequirements(2)),
  KEYS(new KeysExecutor(), new ExactParameterRequirements(2)),
  PERSIST(new PersistExecutor(), new ExactParameterRequirements(2)),
  PEXPIRE(new PExpireExecutor()),
  PEXPIREAT(new PExpireAtExecutor()),
  PTTL(new PTTLExecutor(), new ExactParameterRequirements(2)),
  RENAME(new RenameExecutor(), new ExactParameterRequirements(3)),
  SCAN(new ScanExecutor(), new MinimumParameterRequirements(2)),
  TTL(new TTLExecutor(), new ExactParameterRequirements(2)),
  TYPE(new TypeExecutor(), new ExactParameterRequirements(2)),

  /***************************************
   ************** Strings ****************
   ***************************************/

  APPEND(new AppendExecutor(), new ExactParameterRequirements(3)),
  BITCOUNT(new BitCountExecutor(),
      new ExactParameterRequirements(2).or(new ExactParameterRequirements(4))),
  BITOP(new BitOpExecutor(), new MinimumParameterRequirements(4)),
  BITPOS(new BitPosExecutor(),
      new MinimumParameterRequirements(3).and(new MaximumParameterRequirements(5))),
  DECR(new DecrExecutor(), new ExactParameterRequirements(2)),
  DECRBY(new DecrByExecutor(), new ExactParameterRequirements(3)),
  GET(new GetExecutor(), new ExactParameterRequirements(2)),
  GETBIT(new GetBitExecutor(), new ExactParameterRequirements(3)),
  GETRANGE(new GetRangeExecutor(), new ExactParameterRequirements(4)),
  GETSET(new GetSetExecutor(), new ExactParameterRequirements(3)),
  INCR(new IncrExecutor(), new ExactParameterRequirements(2)),
  INCRBY(new IncrByExecutor(), new ExactParameterRequirements(3)),
  INCRBYFLOAT(new IncrByFloatExecutor(), new ExactParameterRequirements(3)),
  MGET(new MGetExecutor(), new MinimumParameterRequirements(2)),
  MSET(new MSetExecutor(), new MinimumParameterRequirements(3).and(new OddParameterRequirements())),
  MSETNX(new MSetNXExecutor(),
      new MinimumParameterRequirements(3).and(new OddParameterRequirements())),
  PSETEX(new PSetEXExecutor(), new ExactParameterRequirements(4)),
  SETEX(new SetEXExecutor(), new ExactParameterRequirements(4)),
  SET(new SetExecutor(), new MinimumParameterRequirements(3)),
  SETBIT(new SetBitExecutor(), new ExactParameterRequirements(4)),
  SETNX(new SetNXExecutor(), new ExactParameterRequirements(3)),
  SETRANGE(new SetRangeExecutor(), new ExactParameterRequirements(4)),
  STRLEN(new StrlenExecutor(), new ExactParameterRequirements(2)),

  /***************************************
   **************** Hashes ***************
   ***************************************/

  HDEL(new HDelExecutor(), new MinimumParameterRequirements(3)),
  HEXISTS(new HExistsExecutor(), new ExactParameterRequirements(3)),
  HGET(new HGetExecutor(), new ExactParameterRequirements(3)),
  HGETALL(new HGetAllExecutor(), new ExactParameterRequirements(2)),
  HINCRBY(new HIncrByExecutor(), new ExactParameterRequirements(4)),
  HINCRBYFLOAT(new HIncrByFloatExecutor(), new ExactParameterRequirements(4)),
  HKEYS(new HKeysExecutor(), new ExactParameterRequirements(2)),
  HLEN(new HLenExecutor(), new ExactParameterRequirements(2)),
  HMGET(new HMGetExecutor(), new MinimumParameterRequirements(3)),
  HMSET(new HMSetExecutor(),
      new MinimumParameterRequirements(4).and(new EvenParameterRequirements())),
  HSCAN(new HScanExecutor(), new MinimumParameterRequirements(3)),
  HSET(new HSetExecutor(),
      new MinimumParameterRequirements(4).and(new EvenParameterRequirements())),
  HSETNX(new HSetNXExecutor(), new ExactParameterRequirements(4)),
  HVALS(new HValsExecutor(), new ExactParameterRequirements(2)),

  /***************************************
   *********** HyperLogLogs **************
   ***************************************/

  PFADD(new PFAddExecutor(), new MinimumParameterRequirements(2)),
  PFCOUNT(new PFCountExecutor(), new MinimumParameterRequirements(2)),
  PFMERGE(new PFMergeExecutor(), new MinimumParameterRequirements(3)),

  /***************************************
   *************** Lists *****************
   ***************************************/

  LINDEX(new LIndexExecutor(), new MinimumParameterRequirements(3)),
  LINSERT(new LInsertExecutor()),
  LLEN(new LLenExecutor(), new MinimumParameterRequirements(2)),
  LPOP(new LPopExecutor(), new MinimumParameterRequirements(2)),
  LPUSH(new LPushExecutor(), new MinimumParameterRequirements(3)),
  LPUSHX(new LPushXExecutor(), new MinimumParameterRequirements(3)),
  LRANGE(new LRangeExecutor(), new MinimumParameterRequirements(4)),
  LREM(new LRemExecutor(), new MinimumParameterRequirements(4)),
  LSET(new LSetExecutor(), new MinimumParameterRequirements(4)),
  LTRIM(new LTrimExecutor(), new MinimumParameterRequirements(4)),
  RPOP(new RPopExecutor(), new MinimumParameterRequirements(2)),
  RPUSH(new RPushExecutor(), new MinimumParameterRequirements(3)),
  RPUSHX(new RPushXExecutor(), new MinimumParameterRequirements(3)),

  /***************************************
   **************** Sets *****************
   ***************************************/

  SADD(new SAddExecutor(), new MinimumParameterRequirements(3)),
  SCARD(new SCardExecutor(), new ExactParameterRequirements(2)),
  SDIFF(new SDiffExecutor(), new MinimumParameterRequirements(2)),
  SDIFFSTORE(new SDiffStoreExecutor(), new MinimumParameterRequirements(3)),
  SISMEMBER(new SIsMemberExecutor(), new ExactParameterRequirements(3)),
  SINTER(new SInterExecutor(), new MinimumParameterRequirements(2)),
  SINTERSTORE(new SInterStoreExecutor(), new MinimumParameterRequirements(3)),
  SMEMBERS(new SMembersExecutor(), new ExactParameterRequirements(2)),
  SMOVE(new SMoveExecutor(), new ExactParameterRequirements(4)),
  SPOP(new SPopExecutor(),
      new MinimumParameterRequirements(2).and(new MaximumParameterRequirements(3))
          .and(new SpopParameterRequirements())),
  SRANDMEMBER(new SRandMemberExecutor(), new MinimumParameterRequirements(2)),
  SUNION(new SUnionExecutor(), new MinimumParameterRequirements(2)),
  SUNIONSTORE(new SUnionStoreExecutor(), new MinimumParameterRequirements(3)),
  SSCAN(new SScanExecutor(), new MinimumParameterRequirements(3)),
  SREM(new SRemExecutor(), new MinimumParameterRequirements(3)),

  /***************************************
   ************* Sorted Sets *************
   ***************************************/

  ZADD(new ZAddExecutor(),
      new MinimumParameterRequirements(4).and(new EvenParameterRequirements())),
  ZCARD(new ZCardExecutor(), new ExactParameterRequirements(2)),
  ZCOUNT(new ZCountExecutor(), new ExactParameterRequirements(4)),
  ZINCRBY(new ZIncrByExecutor(), new ExactParameterRequirements(4)),
  ZLEXCOUNT(new ZLexCountExecutor(), new ExactParameterRequirements(4)),
  ZRANGE(new ZRangeExecutor(), new ExactParameterRequirements(4)
      .or(new ExactParameterRequirements(5).and(new StringParameterRequirements(4, "WITHSCORES")))),
  ZRANGEBYLEX(new ZRangeByLexExecutor(),
      new MinimumParameterRequirements(4).and(new MaximumParameterRequirements(7))),
  ZRANGEBYSCORE(new ZRangeByScoreExecutor(),
      new MinimumParameterRequirements(4).and(new MaximumParameterRequirements(8))),
  ZREVRANGE(new ZRevRangeExecutor(),
      new ExactParameterRequirements(4).or(new ExactParameterRequirements(5))),
  ZRANK(new ZRankExecutor(), new ExactParameterRequirements(3)),
  ZREM(new ZRemExecutor(), new MinimumParameterRequirements(3)),
  ZREMRANGEBYLEX(new ZRemRangeByLexExecutor(), new ExactParameterRequirements(4)),
  ZREMRANGEBYRANK(new ZRemRangeByRankExecutor(), new ExactParameterRequirements(4)),
  ZREMRANGEBYSCORE(new ZRemRangeByScoreExecutor(), new ExactParameterRequirements(4)),
  ZREVRANGEBYSCORE(new ZRevRangeByScoreExecutor(), new MinimumParameterRequirements(4)),
  ZREVRANK(new ZRevRankExecutor(), new ExactParameterRequirements(3)),
  ZSCAN(new ZScanExecutor(),
      new MinimumParameterRequirements(3).and(new MaximumParameterRequirements(7))),
  ZSCORE(new ZScoreExecutor(), new ExactParameterRequirements(3)),

  /***************************************
   ********** Publish Subscribe **********
   ***************************************/

  SUBSCRIBE(new SubscribeExecutor()),
  PUBLISH(new PublishExecutor()),
  UNSUBSCRIBE(new UnsubscribeExecutor()),
  PSUBSCRIBE(new PsubscribeExecutor()),
  PUNSUBSCRIBE(new PunsubscribeExecutor()),

  /**************************************
   * Geospatial commands ****************
   **************************************/

  GEOADD(new GeoAddExecutor()),
  GEOHASH(new GeoHashExecutor()),
  GEOPOS(new GeoPosExecutor()),
  GEODIST(new GeoDistExecutor()),
  GEORADIUS(new GeoRadiusExecutor()),
  GEORADIUSBYMEMBER(new GeoRadiusByMemberExecutor()),

  /***************************************
   ************ Transactions *************
   ***************************************/

  DISCARD(new DiscardExecutor()),
  EXEC(new ExecExecutor()),
  MULTI(new MultiExecutor()),
  UNWATCH(new UnwatchExecutor()),
  WATCH(new WatchExecutor()),

  /***************************************
   *************** Server ****************
   ***************************************/

  DBSIZE(new DBSizeExecutor()),
  ECHO(new EchoExecutor()),
  TIME(new TimeExecutor()),
  PING(new PingExecutor()),
  QUIT(new QuitExecutor()),
  SHUTDOWN(new ShutDownExecutor()),
  UNKNOWN(new UnkownExecutor());

  private final Executor executor;
  private final ParameterRequirements parameterRequirements;

  private RedisCommandType(Executor executor) {
    this(executor, new UnspecifiedParameterRequirements());
  }

  private RedisCommandType(Executor executor, ParameterRequirements parameterRequirements) {
    this.executor = executor;
    this.parameterRequirements = parameterRequirements;
  }

  public void executeCommand(Command command, ExecutionHandlerContext executionHandlerContext) {
    parameterRequirements.checkParameters(command, executionHandlerContext);
    executor.executeCommand(command, executionHandlerContext);
  }

  public boolean isTransactional() {
    return executor instanceof TransactionExecutor;
  }
}
