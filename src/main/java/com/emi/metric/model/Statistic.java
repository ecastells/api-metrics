package com.emi.metric.model;

import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Emi on 24/07/2017.
 * Statistic object used to show the transactions statistic
 */
public class Statistic {

    private double sum;
    private double avg;
    private double max;
    private double min;
    private long count;

    public Statistic() {
        super();
    }

    public Statistic(double sum, double avg, double max, double min, long count) {
        this.sum = sum;
        this.avg = avg;
        this.max = max;
        this.min = min;
        this.count = count;
    }

    @ApiModelProperty(position = 0)
    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

    @ApiModelProperty(position = 1)
    public double getAvg() {
        return avg;
    }

    public void setAvg(double avg) {
        this.avg = avg;
    }

    @ApiModelProperty(position = 2)
    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    @ApiModelProperty(position = 3)
    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    @ApiModelProperty(position = 4)
    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }
}
