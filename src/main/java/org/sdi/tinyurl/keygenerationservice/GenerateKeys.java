package org.sdi.tinyurl.keygenerationservice;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class GenerateKeys {

    private static Map<String, Boolean> listOfKeysSet;
    private static List<String> listOfKeys;

    @Async
    public void generateKeys(int number) {
        int i = 0;
        while(i < number) {
            String keys = RandomStringUtils.random(8, true, false);
            if (Objects.nonNull(listOfKeysSet) && listOfKeysSet.get(keys) == null) {
                i++;
                listOfKeysSet.put(keys, false);
                listOfKeys.add(keys);
            }
            else if(Objects.isNull(listOfKeysSet)) {
                listOfKeysSet = new HashMap<>();
                listOfKeys = new ArrayList<>();
                listOfKeys.add(keys);
                listOfKeysSet.put(keys, false);
                i++;
            }
            else System.out.println("Duplicate keys detected:"+listOfKeysSet.keySet()+": keys"+keys);
        }
    }
    public synchronized String getKey() {
        String key = listOfKeys.get(0);
        listOfKeysSet.put(key, true);
        listOfKeys.remove(0);
        if(listOfKeys.size() < 5)
            generateKeys(100);
        return key;
    }
}
