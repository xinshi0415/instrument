package com.gjx.instrument.service;

public interface AirMathService {
    /**
     * 归集ecxel文件并生成新文件
     * @param inputUrl
     * @param outputUrl
     * @return
     */
    public void collectAirExcel(String inputUrl, String outputUrl) throws Exception;
}
