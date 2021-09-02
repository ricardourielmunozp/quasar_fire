package com.starwars.ml.quasar_fire.service;

import com.starwars.ml.quasar_fire.exception.DarkSideException;

import java.util.List;

public interface IMessageService {

    public List<String> getMsgPhrases(List<List<String>> msgList);

    public void removeGap(List<List<String>> msgList, int gapSize);

    public String completeMessage(List<List<String>> msgList);

    public String getMessage(List<List<String>> msgList) throws DarkSideException;
}
