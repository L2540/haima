package com.aliware.tianchi.strategy;

import com.aliware.tianchi.Constants;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.logger.Logger;
import org.apache.dubbo.common.logger.LoggerFactory;
import org.apache.dubbo.rpc.Invocation;

import java.util.Date;
import java.util.PriorityQueue;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2019-07-15 16:30:53
 */
public class AResStrategy extends AbstractStrategy {
    private static final Logger LOGGER = LoggerFactory.getLogger(AResStrategy.class);


    private static AResStrategy strategy = new AResStrategy();

    public static AResStrategy getInstance() {
        return strategy;
    }

    public static AResStrategy getInstance(String dataFrom) {
        strategy.dataFrom = dataFrom;
        return strategy;
    }

    @Override
    public int select(URL url, Invocation invocation) {
        long smallActiveCount;
        long mediumActiveCount;
        long largeActiveCount;
        long smallActiveTime;
        long mediumActiveTime;
        long largeActiveTime;
        smallActiveTime = Constants.lastSmall.longValue();
        mediumActiveTime = Constants.lastMedium.longValue();
        largeActiveTime = Constants.lastLarge.longValue();

        smallActiveCount = Constants.longAdderSmall.longValue();
        mediumActiveCount = Constants.longAdderMedium.longValue();
        largeActiveCount = Constants.longAdderLarge.longValue();


        double k1 = Math.log(rand.nextDouble()) * (smallActiveCount / (smallActiveTime + 35.0) * 1000);
        double k2 = Math.log(rand.nextDouble()) * (mediumActiveCount / (mediumActiveTime + 30.0) * 1000);
        double k3 = Math.log(rand.nextDouble()) * (largeActiveCount / (largeActiveTime + 25.0) * 1000);

        double result = Math.max(k1, Math.max(k1, k2));

        if (result == k1) {
            return 0;
        }
        if (result == k2) {
            return 1;
        }
        return 2;
    }
}
