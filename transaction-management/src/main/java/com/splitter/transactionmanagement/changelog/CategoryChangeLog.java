package com.splitter.transactionmanagement.changelog;

import org.bson.Document;

import com.github.mongobee.changeset.ChangeLog;
import com.github.mongobee.changeset.ChangeSet;
import com.mongodb.client.MongoDatabase;
import com.splitter.transactionmanagement.model.CategoryType;

@ChangeLog
public class CategoryChangeLog {
	
	  @ChangeSet(order = "001", id = "categoryMetaData", author = "guna")
	  public void importCategoryData(MongoDatabase mongoDatabase){
		  final Document category1 = new Document();
		  category1.put("type", CategoryType.FOOD.toString());
		  category1.put("icon", "food");
		  
		  final Document category2 = new Document();
		  category2.put("type",CategoryType.TRAVEL.toString());
		  category2.put("icon", "travel");
		  mongoDatabase.getCollection("category").insertOne(category1);
		  mongoDatabase.getCollection("category").insertOne(category2);
	  }

}
