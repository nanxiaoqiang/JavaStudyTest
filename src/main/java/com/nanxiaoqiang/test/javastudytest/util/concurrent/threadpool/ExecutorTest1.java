package com.nanxiaoqiang.test.javastudytest.util.concurrent.threadpool;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.RandomUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.collect.Lists;

/**
 * ExecuteService的submit的获得Exception的测试，当一个线程返回异常的时候，其他的线程都可控的停止
 *
 * @author chengchao1
 *
 */
public class ExecutorTest1 {

     private static final Logger LOGGER = LogManager
               .getLogger(ExecutorTest1.class);

     public static void main(String[] args) {
          ExecutorService exe = Executors.newCachedThreadPool();
          List<Future<String>> list = Lists.newArrayList();
          for (int i = 0; i < 10; i++) {
               Future<String> future = exe.submit(new WorkTest(i));
               list.add(future);
          }
          exe.shutdown();
          for (Future<String> future : list) {
               try {
                    String str = future.get();
                    LOGGER.info(str);
               } catch (InterruptedException | ExecutionException e) {
                    LOGGER.error(e);
                    exe.shutdown();
                    // e.printStackTrace();
                    return;
               }
          }
     }
}

class WorkTest implements Callable<String> {
     private static final Logger LOGGER = LogManager.getLogger(WorkTest.class);

     private int id;

     public WorkTest(int id) {
          this.id = id;
     }

     @Override
     public String call() throws Exception {
          LOGGER.info(Thread.currentThread().getName() + ":" + id + " start!");
          int sleepTime = RandomUtils.nextInt(2, 5);// 随机一个2~4的随机数
          TimeUnit.SECONDS.sleep(sleepTime);
          if (RandomUtils.nextInt(0, 5) == 0) {
               throw new Exception("随机异常" + Thread.currentThread().getName() + ":"
                         + id);
          }
          LOGGER.info(new StringBuffer().append(Thread.currentThread().getName())
                    .append(':').append(id).append(" sleep ").append(sleepTime)
                    .append(" Over!").toString());
          return Thread.currentThread().getName() + ":" + id;
     }
}