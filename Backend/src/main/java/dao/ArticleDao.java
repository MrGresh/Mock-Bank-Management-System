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
 * Copyright (C) 2012-2020 the original author or authors.
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

package dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import models.Article;
import models.ArticleDto;
import models.ArticlesDto;
import models.Users;
import ninja.jpa.UnitOfWork;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;

import ninja.params.Param;
import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

public class ArticleDao {
   
    @Inject
    Provider<EntityManager> entitiyManagerProvider;
    
    public static PolicyFactory sanitizer = Sanitizers.FORMATTING.and(Sanitizers.BLOCKS);
    
    @UnitOfWork
    public ArticlesDto getAllArticles() {
        
        EntityManager entityManager = entitiyManagerProvider.get();
        
        TypedQuery<Article> q = entityManager.createQuery("SELECT x FROM Article x", Article.class);
        List<Article> articles = q.getResultList();        

        ArticlesDto articlesDto = new ArticlesDto();

        articlesDto.articles = articles;
        return articlesDto;
        
    }
    
    @UnitOfWork
    public Article getFirstArticleForFrontPage() {
        
        EntityManager entityManager = entitiyManagerProvider.get();
        
        TypedQuery<Article> q = entityManager.createQuery("SELECT x FROM Article x ORDER BY x.postedAt DESC", Article.class);
        Article article = q.setMaxResults(1).getSingleResult();      
        
        return article;
        
        
    }
    
    @UnitOfWork
    public List<Article> getOlderArticlesForFrontPage() {
        
        EntityManager entityManager = entitiyManagerProvider.get();
        
        TypedQuery<Article> q = entityManager.createQuery("SELECT x FROM Article x ORDER BY x.postedAt DESC", Article.class);
        List<Article> articles = q.setFirstResult(1).setMaxResults(10).getResultList();            
        
        return articles;
        
        
    }
    
    @UnitOfWork
    public Article getArticle(Long id) {
        
        EntityManager entityManager = entitiyManagerProvider.get();
        
        TypedQuery<Article> q = entityManager.createQuery("SELECT x FROM Article x WHERE x.id = :idParam", Article.class);
        Article article = q.setParameter("idParam", id).getSingleResult();        
        
        return article;
        
        
    }
    
    /**
     * Returns false if user cannot be found in database.
     */
    @Transactional
    public boolean postArticle(String username, ArticleDto articleDto) {
        
        EntityManager entityManager = entitiyManagerProvider.get();
        
        TypedQuery<Users> q = entityManager.createQuery("SELECT x FROM Users x WHERE username = :usernameParam", Users.class);
        Users users = q.setParameter("usernameParam", username).getSingleResult();
        
        if (users == null) {
            return false;
        }
//        String title  = sanitizer.sanitize(articleDto.title);
//        String content  = sanitizer.sanitize(articleDto.content);
        Article article = new Article(users, articleDto.title, articleDto.content);
        entityManager.persist(article);
        
        return true;
        
    }

    @Transactional
    public Article getArticleById(Long id){
        EntityManager entityManager = entitiyManagerProvider.get();
        TypedQuery<Article> q = entityManager.createQuery("select x from Article x where x.id=:ArticleId", Article.class);
        List<Article> art = q.setParameter("ArticleId",id).getResultList();
        Article article1 = new Article();
        if(art.size()!=0)
        {
            article1 = art.get(0);
        }
        else {
            article1 = null;
        }
        return article1;


    }

    @Transactional
    public Article getArticleByTitle(String title){

            System.out.println(title);
            EntityManager entityManager = entitiyManagerProvider.get();
            TypedQuery<Article> q = entityManager.createQuery("select x from Article x where x.title=:ArticleTitle", Article.class);
            List<Article> art = q.setParameter("ArticleTitle",title).getResultList();
            Article article1 = new Article();
            if(art.size()!=0)
            {
                article1 = art.get(0);
            }
            else {
                article1 = null;
            }
            return article1;


    }
    @Transactional
    public Article getArticleBycontent(String content){
        EntityManager entityManager = entitiyManagerProvider.get();
        TypedQuery<Article> q = entityManager.createQuery("select x from Article x where x.content=:ArticleContent", Article.class);
        List<Article> art = q.setParameter("ArticleContent",content).getResultList();
        Article article1 = new Article();
        if(art.size()!=0)
        {
            article1 = art.get(0);
        }
        else {
            article1 = null;
        }
        return article1;
    }
    @Transactional
    public Article getArticleByTitlecontent(String content,String title){
        EntityManager entityManager = entitiyManagerProvider.get();
        TypedQuery<Article> q = entityManager.createQuery("select x from Article x where x.content=:ArticleContent and x.title=:ArticleTitle", Article.class);
        List<Article> art = q.setParameter("ArticleContent",content).setParameter("ArticleTitle",title).getResultList();
        Article article1 = new Article();
        if(art.size()!=0)
        {
            article1 = art.get(0);
        }
        else {
            article1 = null;
        }
        return article1;
    }
    @Transactional
    public Article getArticleByPostedAt(Long postedAt){
        EntityManager entityManager = entitiyManagerProvider.get();
        System.out.println(postedAt);
        TypedQuery<Article> q = entityManager.createQuery("select x from Article x where postedAt=:ArticlePostedAt", Article.class);
        Date x = new Date(postedAt);

        List<Article> art = q.setParameter("ArticlePostedAt",x).getResultList();
        Article article1 = new Article();
        if(art.size()!=0)
        {
            article1 = art.get(0);
        }
        else {
            article1 = null;
        }
        return article1;
    }
    @Transactional
    public Article putArticleById(Long id){
       EntityManager entityManager = entitiyManagerProvider.get();
       TypedQuery<Article> q = entityManager.createQuery("select a from Article a where a.id = :ArticleX",Article.class);
       List<Article> art = q.setParameter("ArticleX",id).getResultList();
        Article article1 = new Article();
      if(art!=null){
          article1 = art.get(0);
          article1.content="MyAnatomyIntegeration";

      }
      return article1;
    }
    /*update Article a set a.content= 'GreshSehrawat' where a.id = :x*/
    @Transactional
    public String deleteArticleById(Long id){
        EntityManager entityManager = entitiyManagerProvider.get();
        TypedQuery<Article> q = entityManager.createQuery("select a from Article a  where a.id = :ArticleX",Article.class);
        List<Article> art = q.setParameter("ArticleX",id).getResultList();

        if(art!=null){

            Article article1 = new Article();
            article1 = art.get(0);
//            entityManager.remove(art.get(0));
            entityManager.remove(article1);
        }
        return "success";
    }

    /*The @Transactional annotation is the metadata that specifies the semantics of the transactions on a method.
    We have two ways to rollback a transaction: declarative and programmatic.
    In the declarative approach, we annotate the methods with the @Transactional annotation.

    The unit of work class serves one purpose:
    to make sure that when you use multiple repositories, they share a single database context.
    Unit of Work merges many small database updates in a single batch to optimize the number of round-trips

    The @Inject annotation lets us define an injection point that is injected during bean instantiation.
    */

}
