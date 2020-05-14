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


public class RedisConstants {

  public static final int NUM_DEFAULT_KEYS = 3;

  /*
   * Responses
   */
  public static final String QUIT_RESPONSE = "OK";
  public static final String COMMAND_QUEUED = "QUEUED";


  /*
   * Error responses
   */
  static final String PARSING_EXCEPTION_MESSAGE =
      "The command received by GeodeRedisServer was improperly formatted";
  public static final String SERVER_ERROR_MESSAGE =
      "The server had an internal error please try again";
  static final String SERVER_ERROR_UNKNOWN_RESPONSE = "Unkown response";
  static final String SERVER_ERROR_SHUTDOWN = "The server is shutting down";
  static final String ERROR_UNSUPPORTED_OPERATION_IN_TRANSACTION =
      "This command is not supported within a transaction";
  static final String ERROR_TRANSACTION_EXCEPTION =
      "This transcation cannot be initiated, make sure the command is executed against a replicate region or your data is collocated. If you are using persistent regions, make sure transactions are enabled";
  public static final String ERROR_NOT_NUMERIC = "Illegal non numeric argument";
  public static final String ERROR_INVALID_ARGUMENT_UNIT_NUM =
      "Either illegal non numeric argument or invalid unit" +
          "(please use either km/m/ft/mi)";
  public static final String ERROR_UNKOWN_COMMAND = "Unable to process unknown command";
  public static final String ERROR_COMMIT_CONFLICT =
      "There has been a conflict with another transaction";
  public static final String ERROR_REGION_CREATION =
      "This key could not be created. Gemfire does not allow certain characters to used in keys";
  public static final String ERROR_UNWATCH =
      "Keys cannot be watched or unwatched because GemFire watches all keys by default for transactions";
  public static final String ERROR_WATCH =
      "Keys cannot be watched or unwatched because GemFire watches all keys by default for transactions";
  public static final String ERROR_ILLEGAL_GLOB = "Incorrect syntax for given glob regex";
  public static final String ERROR_OUT_OF_RANGE = "The number provided is out of range";
  public static final String ERROR_INVALID_LATLONG = "Invalid longitude-latitude pair";
  public static final String ERROR_NESTED_MULTI = "The MULTI command cannot be nested";
  public static final String ERROR_NAN_INF_INCR = "increment would produce NaN or Infinity";
  public static final String ERROR_NO_PASS =
      "Attempting to authenticate when no password has been set";
  public static final String ERROR_INVALID_PWD =
      "Attemping to authenticate with an invalid password";
  public static final String ERROR_NOT_AUTH = "Must authenticate before sending any requests";
  public static final String ERROR_ZSET_MEMBER_NOT_FOUND = "could not decode requested zset member";
  public static final String ERROR_WRONG_TYPE =
      "Operation against a key holding the wrong kind of value";
  public static final String ERROR_NOT_INTEGER = "value is not an integer or out of range";
  public static final String ERROR_OVERFLOW = "increment or decrement would overflow";
  public static final String ERROR_NO_SUCH_KEY = "no such key";
  public static final String ERROR_SYNTAX = "syntax error";
  public static final String ERROR_INVALID_EXPIRE_TIME = "invalid expire time in set";

  public static class ArityDef {

    /*
     * General
     */
    public static final int DBSIZE_ARITY = 0;
    public static final String AUTH =
        "The wrong number of arguments or syntax was provided, the format for the AUTH command is \"AUTH password\"";
    public static final String DBSIZE = null;
    public static final String DEL =
        "The wrong number of arguments or syntax was provided, the format for the DEL command is \"DEL key [key ...]\"";
    public static final String ECHO =
        "The wrong number of arguments or syntax was provided, the format for the ECHO command is \"ECHO message\"";
    public static final String EXISTS =
        "The wrong number of arguments or syntax was provided, the format for the EXISTS command is \"EXISTS key\"";
    public static final String EXPIREAT =
        "The wrong number of arguments or syntax was provided, the format for the EXPIREAT command is \"EXPIREAT key timestamp\"";
    public static final String EXPIRE =
        "The wrong number of arguments or syntax was provided, the format for the EXPIRE command is \"EXPIRE key seconds\"";
    public static final String FLUSHALL = null;
    public static final String KEYS =
        "The wrong number of arguments or syntax was provided, the format for the KEYS command is \"KEYS pattern\"";
    public static final String PERSIST =
        "The wrong number of arguments or syntax was provided, the format for the PERSIST command is \"PERSIST key\"";
    public static final String PEXPIREAT =
        "The wrong number of arguments or syntax was provided, the format for the PEXPIREAT command is \"PEXPIREAT key milliseconds-timestamp\"";
    public static final String PEXPIRE =
        "The wrong number of arguments or syntax was provided, the format for the PEXPIRE command is \"PEXPIRE key milliseconds\"";
    public static final String PING = null;
    public static final String PTTL =
        "The wrong number of arguments or syntax was provided, the format for the PTTL command is \"PTTL key\"";
    public static final String QUIT = null;
    public static final String SCAN =
        "The wrong number of arguments or syntax was provided, the format for the SCAN command is \"SCAN cursor [MATCH pattern] [COUNT count]\"";
    public static final String SHUTDOWN = null;
    public static final String TIME = null;
    public static final String TTL =
        "The wrong number of arguments or syntax was provided, the format for the TTL command is \"TTL key\"";
    public static final String TYPE =
        "The wrong number of arguments or syntax was provided, the format for the TYPE command is \"TYPE key\"";
    public static final String UNKNOWN = null;


    /*
     * Hll
     */
    public static final String PFADD =
        "The wrong number of arguments or syntax was provided, the format for the PFADD command is \"PFADD key element [element ...]\"";
    public static final String PFCOUNT =
        "The wrong number of arguments or syntax was provided, the format for the PFCOUNT command is \"PFCOUNT key [key ...]\"";
    public static final String PFMERGE =
        "The wrong number of arguments or syntax was provided, the format for the PFMERGE command is \"PFMERGE destkey sourcekey [sourcekey ...]\"";

    /*
     * Geospatial
     */
    public static final String GEOADD =
        "The wrong number of arguments or syntax was provided, the format for the GEOADD command is \"GEOADD key longitude latitude member [longitude latitude member ...]\", or not every latitude/longitude pair matches to a member";
    public static final String GEOHASH =
        "The wrong number of arguments or syntax was provided, the format for the GEOHASH command is \"GEOHASH key member [member...]\"";
    public static final String GEOPOS =
        "The wrong number of arguments or syntax was provided, the format for the GEOPOS command is \"GEOPOS key member [member...]\"";
    public static final String GEODIST =
        "The wrong number of arguments or syntax was provided, the format for the GEODIST command is \"GEODIST key member member [unit]\"";
    public static final String GEORADIUS =
        "The wrong number of arguments or syntax was provided, the format for the GEORADIUS command is \"GEORADIUS key longitude latitude radius m|km|ft|mi [WITHCOORD] [WITHDIST] [WITHHASH] [COUNT count] [ASC|DESC]\"";
    public static final String GEORADIUSBYMEMBER =
        "The wrong number of arguments or syntax was provided, the format for the GEORADIUSBYMEMBER command is \"GEORADIUSBYMEMBER key member radius m|km|ft|mi [WITHCOORD] [WITHDIST] [WITHHASH] [COUNT count] [ASC|DESC]\"";

    /*
     * String
     */
    public static final String PUBLISH =
        "The wrong number of arguments or syntax was provided, the format for the PUBLISH command is \"PUBLISH channel message\"";

    /*
     * Transaction
     */
    public static final String DISCARD = null;
    public static final String EXEC = null;
    public static final String MULTI = null;
    public static final String UNWATCH = null;
    public static final String WATCH = null;
  }

}
