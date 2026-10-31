package org.j2os.account.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Value("${app.name}")
    private String appName;

    public void save(){
        System.out.println("classNmae: PersonService" + "methodName: save " + appName);
    }
}
