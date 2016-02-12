Cette application Rails accompagne les démonstrateurs [Android](https://repo.android/) et [iPhone](https://repo.iphone/).

Les endpoints sont :

*  GET **/callback** : Affiche une page statique
*  GET **/userinfo**?*code*=xxx&*nonce*=yyy : Appelle le service "token" de FranceConnect pour récupérer un access token, puis appelle le service "userinfo". Si il n'y a pas d'erreur, le corps de la réponse du "userinfo" est retourné tel quel à l'application mobile.

Pour réutiliser ce serveur afin d'utiliser les démonstrateurs mobiles, après [vous être inscrit auprès de FranceConnect](https://developers.integ01.dev-franceconnect.fr/inscription), vous devez renseigner votre client id, client secret et callback url dans le fichier `app/controllers/userinfo_controller.rb`. Vous pourrez ensuite faire pointer les applications mobiles vers l'url de ce serveur.