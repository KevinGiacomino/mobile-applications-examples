Cette application iPhone est un démonstrateur technique prouvant la faisabilité d'une authentification à FranceConnect sur mobile, ainsi que la récupération des informations de l'identité pivot de l'utilisateur.

# Fonctionnement global

![Schéma d'architecture application / backend / FranceConnect](https://raw.githubusercontent.com/france-connect/mobile-applications-examples/master/diagram/demo_diagram.png)

Au clic sur le bouton "Se connecter avec FranceConnect", l'application ouvre une webview pointant sur l'écran d'authentification FranceConnect (Étape 1 sur le schéma).

Le processus d'authentification OpenId Connect se déroule dans la webview. Les changements de page et les redirections sont suivies jusqu'à ce que le serveur FranceConnect provoque la redirection vers le "callback endpoint", c'est à dire l'URL de Callback que vous avez renseigné à votre inscription sur FranceConnect (étape 1 sur le schéma).

La redirection vers le "callback endpoint" est considérée comme un succès de l'authentification. L'application mobile coupe cette redirection, et récupère les paramètres GET "code" et "state".

Le code d'authorisation "code" est transmis au back-end par un appel webservice (étape 2 sur le schéma).

Le back-end peut alors appeler le service "token" des serveurs de FranceConnect (étape 3 du schéma), puis le service "userinfo" afin de récupérer l'identité pivot de l'utilisateur connecté (étape 4). La réponse au "userinfo" de FranceConnect est retransmise telle quelle au mobile par retour du webservice (étape 5).

L'application iPhone réalise alors le parsing du json récupéré afin de présenter les informations à l'écran.

# Détails
## Authentification
L'écran `AuthorizeViewController` affiche une webview dirigée vers la mire d'authenficiation de FranceConnect.

L'implémentation de `UIWebViewDelegate` scrute les changements de page au sein de la webview pour intercepter la demande de redirection vers l'URL de callback.

Lorsque l'URL de callback est intercepté, les paramètres GET sont extraits de l'URL et sont transmis au backend de l'application par un appel webservice.

Au retour du webservice, après parsing du json, un segue est effectué pour afficher l'écran porté par le `TabBarController`.

## Écrans après authentification
Le `TabBarController` porte quatre écrans :

Les controlleurs `IdentityViewController` et `ContactViewController` affichent les différents éléments de l'identité pivot.

L'écran `HistoryViewController` contient une webview qui affiche l'historique / les traces des connexions au service FranceConnect. Lors de l'authentification au service, FranceConnect dépose un cookie dans la webview ("sandboxé" à l'application mobile). Ce cookie sera automatiquement "chargé" par toutes les autres webviews de l'application, et est donc envoyé à FranceConnect lors de l'affichage de la page d'historique.

## Déconnexion
L'écran `MoreViewController` contient un simple bouton permettant la déconnexion. Après un tap, un appel au service "logout" de FranceConnect, est effectué, et le cookie est supprimé.

# Avertissement
Le schéma d'architecture utilisé permet de prouver qu'un utilisateur d'une application mobile peut s'authentifier grâce à FranceConnect, puis que le backend de cette application est capable de récupérer l'identité pivot de l'utilisateur.

Une application destinée à la mise en production devrait suivre un schéma d'architecture de ce type :

![Schéma d'architecture application / backend / FranceConnect](https://raw.githubusercontent.com/france-connect/mobile-applications-examples/master/diagram/target_diagram.png)

1. L'application affiche une webview. Cette webview pointe sur une URL du backend de l'application. Ce backend provoque la redirection de la webview vers la page d'authentification de FranceConnect en fournissant les bons paramètres (scope, clef publique de l'application etc).
2. L'utilisateur complète la cinématique d'authentification OpenId Connect.
3. Au succès de l'authentification, FranceConnect provoque la redirection de la webview vers la callback de succès du backend
4. Le backend traite les paramètres transmis dans l'appel à la callback de succès et appelle le webservice "token" de FranceConnect pour récupérer un access token et id token.
5. Le backend peut maintenant requêter FranceConnect (par exemple le service "userinfo"). En réponse à l'appel n°3, le backend transmet un cookie, token de connexion ou autre à l'application mobile.
6. Grâce au cookie, token ou autre transmis par le backend, l'application mobile peut désormais requêter les webservices de son backend de manière authentifiée, et peut donc par exemple demander la récupération des informations de l'identité pivot de l'utilisateur.

# Réutiliser ce démonstrateur
Pour réutiliser ce démonstrateur, vous aurez besoin d'un backend. Le plus simple est de [réutiliser le backend ayant servi pour ce démonstrateur](https://github.com/france-connect/mobile-applications-examples/tree/master/backend). Vous pourrez ensuite renseigner vos paramètres dans le fichier `Utils/Constants.swift`, notamment aux lignes 18, 22 et 23.