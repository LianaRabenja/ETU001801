# FRAMEWORK
## 1. Presentation
Framework JAVA similaire à spring MVC <br>
Sur toute les plateformes Utilisant Tomcat

## 2. Pré-requis
- JDK version 17.0.6 au minimum
- Tomcat 10 au minimum
- Avoir le __fw.jar__ du projet

## 3. Configuration de l'environnement de travail
* Copier dans le repertoir <b>"Project/WEB-INF/lib/"</b> le <b>"fw.jar"</b>
* Dans le <b>"Project/WEB-INF/web.xml"</b> mettez : <br>
```xml
    <servlet>
        <servlet-name>FrontServlet</servlet-name>
        <servlet-class>etu1784.framework.servlet.FrontServlet</servlet-class>
    </servlet>

    <servlet-mapping>
        <servlet-name>FrontServlet</servlet-name>
        <url-pattern>*.do</url-pattern>
    </servlet-mapping>
```
## 4. Utilisation

* Toute les fonction d'action doit retourner la classe <b>"ModeView"</b>

* Toute les fonction d'action doit etre annoté par <b>"MethodAnnotation" </b><br>
        <b>url</b> = L'url pour l'appeler avec l'extension ".do"</br>
        <b>paramName</b> = les nons des parametres separer par virgule
```java
    @ActionMethod( url = "save.do", paramName = "nom,dateNaissance")
    public ModelView test(String nom, String dateNaissance) {
        ...
    }
```
* La classes ModelView contient l'attribut <b>"view"</b> qui est la view qui contient la view que l'on souhaite être dirigé
* La classe ModelView a une fonction addItem pour envoyer les donner dans va view que l'on recuperera ex:

```java
@ActionMethod( url = "getData.do")
public ModelView getData() {
    ModelView mv = new ModelView();

    mv.setView("/test.jsp");
    mv.addItem("data", Object data);

    return mv;
}
``` 

* Pour les recuperer dans la view il suffit juste de faire:

```jsp
    request.getAttribute("data")
```
* Pour l'envoie des données depuis la View vers la model <br>
Il faut que les nom des paramètres correspond au nom de l'attribut du classe ou le nom de parametre.

* Si on veut qu'une class soit traité comme un singleton il faut l'annoter par:
```java
    @Scope( type = ScopeType.SINGLETON)
    public class SingletonTest {
        // Les attribut sont initialiser avant chaque appel de la method
        ...
    }
```
<br>
<br>

* ### Pour ajouter des sessions dans HttpSession depuis le model il suffit d'appeller la fonction __addSession("key", value)__ dans la class __ModelView__  
***
```java
    @ActionMethod( url = "login.do", paramName="profil")
    public ModelView test(String profil) {
        ModelView mv = new ModelView();

        mv.setView("/index.jsp");
        mv.addSession("userProfil", profil);

        return mv;
    }
```

<br>
<br>

* ### Pour proteger une fonction ou ajouter une session pour l'appeler:<br>
***
il faut annoter la fonction par __@Auth( profil = "profil Autorise")__
et ajouter le nom de la variable de session pour contenir le profil dans web.xml
* <br> _Dans web.xml:_

```xml
    <servlet>
        <servlet-name>FrontServlet</servlet-name>
        <servlet-class>etu1784.framework.servlet.FrontServlet</servlet-class>
        <init-param>
            <param-name>session</param-name>
            <param-value>Variable session dans HttpSession</param-value>
        </init-param>
    </servlet>

    <servlet-mapping>
        <servlet-name>FrontServlet</servlet-name>
        <url-pattern>*.do</url-pattern>
    </servlet-mapping>
```

* <br> _La Fonction:_

```java
    @Auth( profil = "admin")
    @ActionMethod( url = "save.do")
    public ModelView save() {
        ...    
    }
```
* <br>Si la Fonciton peut etre appeler par plusieurs profil

```java
    @Auth( profil = "admin,simple")
    @ActionMethod( url = "setNewTest.do", paramName = "identifiant,name")
    public ModelView newTest(int identifiant, String name) {
        ...
    }
```

<br>
<br>

### Suppression des variable de session
*** 
Pour cela ajouter le nom de l'attribut du session dans __ModelView__
```java
    @ActionMethod( url = "disconnect.do")
    public ModelView deco() {
        ModelView mv = new ModelView();

        mv.setView("/index.jsp");
        mv.removeSession("userProfil");

        // Dans ce cas userProfil sera supprimer dans la variable session

        return mv;
    }
```
Pour supprimers toute les session, il faut setter __invalidateSession(true)__ de la class __ModelView__


<br>
<br>

### Pour les format JSON
***
1. Si la fonction retourne ModelView, faire __setJson(true)__. 
Cela transformera automatiquement les contenu de __Data__ dans ModelView au format JSON.
```java
    @Auth( profil = "admin")
    @ActionMethod( url = "save.do")
    public ModelView save() {
        ModelView mv = new ModelView();

        mv.addItem("obj", this);
        mv.setJson(true);

        // dans ce cas this sera transformer en JSON et afficher
        return mv;
    }
```


2. Si la fonction retourne autre que ModelView, il faut l'annoter par __@restAPI__

```java
    @restAPI
    @ActionMethod( url = "rest.do")
    public HashMap restAPI() {
        HashMap<String, Object> test = new HashMap<>();
        
        test.put("pers1", 123);
        test.put("pers2", 123);
        test.put("pers3", 123);

        //dans ce cas test sera affiher dans le navigatuer en format JSON

        return test;
    }
```

* Si la Dans la fonction on souhaite utiliser les session il faut avoir une attribut de type __HashMap<String, Object> session__ et une fonnction setter pour cela __public void setSession(HashMap<String, Object> session)__
Et annoter la fonction par __@UseSession__
```java
    @UseSession
    @restAPI
    @ActionMethod( url = "useSession.do")
    public List<Test> useSession() {
        List<Test> test = new ArrayList<>();

        for(String s : session.keySet()) {
            Test a = new Test();
            a.setId(0);
            a.setNom(session.get(s));
            test.add(a);
        }

        return test;
    }
```
 
## <b> Remarque </b>
* Toute les class ayant une method d'action doit avoir des setter qui prend des arguments de type __Primitives__ ou  __Date__ comme argument 
<br>ex:
```java
    public void setAge(int age) {
        this.age = age;
    }
    
    public void setDateNaissance(java.util.Date dtn) {
        this.dateNaissanve = dtn;
    }
```
* Toute les arguments des fonction d'action doit doit être de type __Primitives__ ou __Date__.
```java
    ...
    public ModelView findById(int id) {
        ...
    }
```

* Pour l'utilisation de fonctionnalité pour les format JSON il faut avoir
__gson-2.10.1.jar__ au minimum.
