//
//  Constants.swift
//  FranceConnect POC
//
//  Created by Louis Davin on 04/11/2015.
//  Copyright © 2015 OCTO Technology. All rights reserved.
//

import Foundation

struct Constants {
    struct FranceConnect {
        static let baseUrl = "https://fcp.integ01.dev-franceconnect.fr";
        static let authorizePath = "/api/v1/authorize";
        static let logoutPath = "/api/v1/logout?force=true";
        static let tracesPath = "/traces";
        
        static let clientId = "YOUR CLIENT ID";
        static let scopes = "openid profile birth email address phone";
    }
    struct Backend {
        static let baseUrl = "YOUR BACKEND URL";
        static let callbackUrl = "YOUR BACKEND CALLBACK URL";
        static let userInfoPath = "/userinfo";
    }
}