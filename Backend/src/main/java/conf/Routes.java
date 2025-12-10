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

package conf;

import ninja.AssetsController;
import ninja.Router;
import ninja.application.ApplicationRoutes;
import ninja.utils.NinjaProperties;

import com.google.inject.Inject;

import controllers.*;
public class Routes implements ApplicationRoutes {
    
    @Inject
    NinjaProperties ninjaProperties;

    /**
     * Using a (almost) nice DSL we can configure the router.
     * 
     * The second argument NinjaModuleDemoRouter contains all routes of a
     * submodule. By simply injecting it we activate the routes.
     * 
     * @param router
     *            The default router of this application
     */
    @Override
    public void init(Router router) {  
        
        // puts test data into db:
        if (!ninjaProperties.isProd()) {
            router.GET().route("/setup").with(ApplicationController::setup);
        }
        
        ///////////////////////////////////////////////////////////////////////
        // Login / Logout
        ///////////////////////////////////////////////////////////////////////
        router.GET().route("/login").with(LoginLogoutController::login);
        router.POST().route("/login").with(LoginLogoutController::loginPost);
        router.GET().route("/logout").with(LoginLogoutController::logout);
        
        ///////////////////////////////////////////////////////////////////////
        // Create new article
        ///////////////////////////////////////////////////////////////////////
        router.GET().route("/article/new").with(ArticleController::articleNew);

        
        ///////////////////////////////////////////////////////////////////////
        // Create new article
        ///////////////////////////////////////////////////////////////////////
        router.GET().route("/article/{id}").with(ArticleController::articleShow);

        ///////////////////////////////////////////////////////////////////////
        // Api for management of software
        ///////////////////////////////////////////////////////////////////////



        router.GET().route("/api/{username}/article/{id}.json").with(ApiController::getArticleJson);


        router.GET().route("/api/{username}/articles.xml").with(ApiController::getArticlesXml);
        router.POST().route("/api/{username}/article.json").with(ApiController::postArticleJson);
        router.POST().route("/api/{username}/article.xml").with(ApiController::postArticleXml);

        ///////////////////////////////////////////////////////////////////////
        // Assets (pictures / javascript)
        ///////////////////////////////////////////////////////////////////////
        router.GET().route("/assets/webjars/{fileName: .*}").with(AssetsController::serveWebJars);
        router.GET().route("/assets/{fileName: .*}").with(AssetsController::serveStatic);

        ///////////////////////////////////////////////////////////////////////
        // Index / Catchall shows index page
        ///////////////////////////////////////////////////////////////////////
//




        /*..............................Newly Created By Use..................................*/
        router.GET().route("/api/articles").with(ApiController::getArticlesJson);

        router.GET().route("/api/getArticleById").with(ApiController::getArticleById);

        router.GET().route("/api/getArticleByTitle/{title}").with(ApiController::getArticleByTitle);

        router.GET().route("/api/getArticleBycontent").with(ApiController::getArticleBycontent);

        router.GET().route("/api/getArticleByTitlecontent").with(ApiController::getArticleByTitlecontent);

        router.GET().route("/api/getArticleByPostedAt").with(ApiController::getArticleByPostedAt);

        router.PUT().route("/api/putArticleById").with(ApiController::putArticleById);

        router.POST().route("/api/article/new").with(ApiController::articleNewPost);


        router.DELETE().route("/api/deleteArticleById").with(ApiController::deleteArticleById);


//        router.GET().route("/api/getAllpersonalDetails").with(ApiController::getAllpersonalDetails);

        router.GET().route("/api/getAllBankDetail").with(BankController::getAllBankDetail);


        /*...........................................Project............................................*/

        /*Sign Up*/
        router.GET().route("/api/VerifySignUp").with(BankController::getpersonalDetailsByCard);
        router.POST().route("/api/postPersonalDetail").with(BankController::postPersonalDetail);

        /*Login*/
        router.GET().route("/api/Userlogin").with(BankController::Userlogin);

        router.GET().route("/api/getBalance").with(BankController::getBalance);
        router.GET().route("/api/getMiniStatement").with(BankController::getMiniStatement);

        router.POST().route("/api/postDeposit").with(BankController::postDeposit);
        router.POST().route("/api/postWithdraw").with(BankController::postWithdraw);
        router.PUT().route("/api/pinChange").with(BankController::pinChange);

        router.GET().route("/api/verifyUser").with(BankController::verifyUser); // At Money Send
        router.POST().route("/api/postDepositRecieved").with(BankController::postDepositRecieved);
        router.POST().route("/api/postWithdrawTransferred").with(BankController::postWithdrawTransferred);

        router.GET().route("/getpersonalDetailsByIdxc").with(BankController::getpersonalDetailsByIdxc);
        /*..............................Thank You............................................*/



//        router.GET().route("/.*").with(ApplicationController::index);
    }

}
