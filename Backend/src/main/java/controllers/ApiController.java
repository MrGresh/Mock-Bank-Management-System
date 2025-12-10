/**
 * Copyright (C) the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * Copyright (C) 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package controllers;

import dao.PersonalDetailDao;
import models.*;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.SecureFilter;
import ninja.params.Param;
import ninja.params.PathParam;

import com.google.inject.Inject;

import dao.ArticleDao;
import etc.LoggedInUser;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ApiController {

    private static Logger log = LogManager.getLogger(ApiController.class);
    @Inject
    ArticleDao articleDao;

    @Inject
    PersonalDetailDao personalDetailDao;



    public Result getArticlesXml() {

        ArticlesDto articlesDto = articleDao.getAllArticles();

        return Results.xml().render(articlesDto);

    }
    
    public Result getArticleJson(@PathParam("id") Long id) {
    
        Article article = articleDao.getArticle(id);
        
        return Results.json().render(article);
    
    }

    public Result getArticleById(@Param("id") Long id){

        Article obj = new Article();
        obj = articleDao.getArticleById(id);
        return Results.json().render(obj);
    }

    public Result getArticleByTitle(@PathParam("title") String title){


            Article obj = new Article();
            obj = articleDao.getArticleByTitle(title);
            return Results.json().render(obj);


    }



    public Result getArticleBycontent(@Param("content") String content){

        Article obj = new Article();
        obj = articleDao.getArticleBycontent(content);
        return Results.json().render(obj);
    }
    public Result getArticleByTitlecontent(@Param("content") String content,@Param("title") String title){

        Article obj = new Article();
        obj = articleDao.getArticleByTitlecontent(content,title);
        return Results.json().render(obj);
    }
    public Result getArticleByPostedAt(@Param("postedAt") Long postedAt){

        Article obj = new Article();
        obj = articleDao.getArticleByPostedAt(postedAt);
        return Results.json().render(obj);
    }

    public Result putArticleById(@Param("id") Long id){

        Article obj = articleDao.putArticleById(id);

        return Results.json().render(obj);
    }

    public Result deleteArticleById(@Param("id") Long id){

        String str = articleDao.deleteArticleById(id);
        return Results.json().render(str);
    }


    @FilterWith(SecureFilter.class)
    public Result postArticleJson(@LoggedInUser String username,
                                  ArticleDto articleDto) {

        boolean succeeded = articleDao.postArticle(username, articleDto);

        if (!succeeded) {
            return Results.notFound();
        } else {
            return Results.json();
        }

    }

    @FilterWith(SecureFilter.class)
    public Result postArticleXml(@LoggedInUser String username,
                                 ArticleDto articleDto) {

        boolean succeeded = articleDao.postArticle(username, articleDto);

        if (!succeeded) {
            return Results.notFound();
        } else {
            return Results.xml();
        }

    }

    public Result articleNewPost(@Param("username")String username, ArticleDto articleDto) {


        articleDao.postArticle(username, articleDto);

        return Results.ok().json().render("success");
}

    public Result getArticlesJson() {


        ArticlesDto articlesDto = articleDao.getAllArticles();

        return Results.json().render(articlesDto);

    }
//    public Result getAllpersonalDetails(){
//
//        log.debug("getAllpersonalDetails isCalled");
//        PersonalDetailsDto object = personalDetailDao.getAllpersonalDetails();
//
//
//        return Results.ok().json().render(object);
//    }

    public Result postPersonalDetail(PersonalDetailDto personaldetailDto) throws Exception{
        System.out.println("We are in Controller");

        PersonalDetailDto personalDetailDto = personalDetailDao.postPersonalDetails(personaldetailDto);
        return Results.ok().json().render(personalDetailDto);
    }




    public Result verifyUser(@Param("id") Long id){

        System.out.println("We are in Controller of User Matching");
        PersonalDetail pd = new PersonalDetail();
        pd = personalDetailDao.getpersonalDetailsById(id);

        return Results.ok().json().render(pd);
    }

    public Result getpersonalDetailsByCard(@Param("card") Long card){

        PersonalDetail pd = new PersonalDetail();
        pd = personalDetailDao.getpersonalDetailsByCard(card);

        return Results.ok().json().render(pd);
    }

    public Result Userlogin(@Param("card") Long card,@Param("pin") Long pin){

        PersonalDetail pd = new PersonalDetail();
        pd = personalDetailDao.Userlogin(card,pin);

        return Results.ok().json().render(pd);
    }

    /*
    The @param tag provides the name, type, and description of a function parameter.
    @PathParam is a parameter annotation which allows you to map variable URI path fragments into your method call.

    Syntax. loggedInUser() · Returns. User · Notes.
    It is only applicable in situations where there is a user context, such as when rendering an interface,
    Returns the current user logged in to the application. Parameters. No parameters.
    */

}
