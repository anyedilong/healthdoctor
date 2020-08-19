package com.java.until;

import static java.util.Objects.hash;

public class BloomFilter {

    private  int expectedInsertions=10000;
    private  double fpp=0.001;

    private int bitSize;
    private int numHashFunctions;

    public BloomFilter(){
        bitSize=optimalNumOfBits(expectedInsertions,fpp);
        numHashFunctions=optimalNumOfHashFunctions(expectedInsertions,bitSize);
    }
    /**
     * 计算bit数组长度
     */
    private int optimalNumOfBits(long n, double p) {
        if (p == 0) {
            p = Double.MIN_VALUE;
        }
        return (int) (-n * Math.log(p) / (Math.log(2) * Math.log(2)));
    }
    /**
     * 计算hash方法执行次数
     */
    private int optimalNumOfHashFunctions(long n, long m) {
        return Math.max(1, (int) Math.round((double) m / n * Math.log(2)));
    }

    /**
     * 返回key的下标
     * @param key
     * @return
     */
    public long[] murmurHashOffset(String key) {
        long  hash1=hash(key);
        long  hash2=hash1>>>16;
        long[] offset = new long[numHashFunctions];
        for (int i = 1; i <= numHashFunctions; i++) {
            long nextHash = hash1 + i * hash2;
            if (nextHash < 0) {
                nextHash = ~nextHash;
            }
            offset[i - 1] = nextHash % bitSize;
        }

        return offset;
    }


    public int getExpectedInsertions() {
        return expectedInsertions;
    }

    public void setExpectedInsertions(int expectedInsertions) {
        this.expectedInsertions = expectedInsertions;
    }

    public double getFpp() {
        return fpp;
    }

    public void setFpp(double fpp) {
        this.fpp = fpp;
    }

    public int getBitSize() {
        return bitSize;
    }

    public void setBitSize(int bitSize) {
        this.bitSize = bitSize;
    }

    public int getNumHashFunctions() {
        return numHashFunctions;
    }

    public void setNumHashFunctions(int numHashFunctions) {
        this.numHashFunctions = numHashFunctions;
    }
}
