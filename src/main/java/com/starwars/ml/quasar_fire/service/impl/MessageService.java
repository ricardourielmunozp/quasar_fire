package com.starwars.ml.quasar_fire.service.impl;

import com.starwars.ml.quasar_fire.exception.DarkSideException;
import com.starwars.ml.quasar_fire.service.IMessageService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class MessageService implements IMessageService {

    @Override
    public List<String> getMsgPhrases(List<List<String>> msgList){

        List<String> listWords = new ArrayList<String>();
        for( List<String> msg : msgList){
            listWords = Stream.concat(listWords.stream(), msg.stream())
                    .distinct()
                    .collect(Collectors.toList());
        }
        listWords.remove("");
        return listWords;
    }

    @Override
    public void removeGap(List<List<String>> msgList, int gapSize){

        int s = 0;
        for(int i = 0; i < msgList.size(); i++){
            s = msgList.get(i).size();
            msgList.set(i, msgList.get(i).subList(s-gapSize, s));
        }

        //msgList.stream().forEach(s -> s.subList(s.size()-lagSize, s.size()));
    }

    @Override
    public String completeMessage(List<List<String>> msgList){

        String phrase = "";
        for(List<String> m : msgList){

            if(m.size()>0 && !m.get(0).equals("")){
                phrase = (m.size() == 1) ? m.get(0) : m.get(0) + " ";
                msgList.stream().forEach( s -> s.remove(0));
                return  phrase + completeMessage(msgList);
            }
        }
        //if(messages.get(0).size()>0)
        return "";
    }

    @Override
    public String getMessage(List<List<String>> msgList) throws DarkSideException {

        List<String> msgPhrases = getMsgPhrases(msgList);
        if(!validateMessagesSize(msgList, msgPhrases.size()))
            throw new DarkSideException("Se necesita mas informacion");

        removeGap(msgList,msgPhrases.size());
        String message = completeMessage(msgList);
        if(!validateMessagePhrases(msgPhrases,message))
            throw new DarkSideException("No se puede conocer el mensaje");

        return message;
    }

    public boolean validateMessagesSize(List<List<String>> messages, int size){
        for(List<String> m : messages){
            if(m.size() < size){
                return false;
            }
        }
        return true;
    }

    public boolean validateMessagePhrases(List<String> phrases, String message){
        List<String> msg = Arrays.stream(message.split(" ")).collect(Collectors.toList());
        Collections.sort(phrases);
        Collections.sort(msg);
        return Arrays.equals(phrases.toArray(), msg.toArray());
    }
}
