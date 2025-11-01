package com.edigest.journalApp.repository;

import com.edigest.journalApp.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class UserRepositoryImpl {

     @Autowired
     private MongoTemplate mongoTemplate;

    public List<User> getUserForSA(){
        Query query = new Query();

        //  WE CAN ALSO ADD CRITERIA LIKE THIS
//        Criteria criteria = new Criteria();
//        query.addCriteria(criteria.orOperator(
//        Criteria.where("email").exists(true),
//        Criteria.where("sentimentAnalysis").is(true) ));


        query.addCriteria(Criteria.where("email").regex("^[A-Za-Z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,6}+$"));

//        query.addCriteria(Criteria.where("email").exists(true));
//        query.addCriteria(Criteria.where("email").ne(null).ne(""));
        query.addCriteria(Criteria.where("sentimentAnalysis").is(true));
        List<User> users = mongoTemplate.find(query,User.class);
        return users;
    }
}
