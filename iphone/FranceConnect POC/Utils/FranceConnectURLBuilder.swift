//
//  FranceConnectURLBuilder.swift
//  FranceConnect POC
//
//  Created by Louis Davin on 18/11/2015.
//  Copyright © 2015 OCTO Technology. All rights reserved.
//

import Foundation

class FranceConnectURLBuilder {
    
    static func buildAuthorizeUrlWith(state: String, nonce: String) -> NSURL? {
        let urlComponents = NSURLComponents(string: Constants.FranceConnect.baseUrl)
        urlComponents?.path = Constants.FranceConnect.authorizePath
        
        var queryItems = [NSURLQueryItem]()
        queryItems.append(NSURLQueryItem(name: "response_type", value: "code"))
        queryItems.append(NSURLQueryItem(name: "client_id", value: Constants.FranceConnect.clientId))
        queryItems.append(NSURLQueryItem(name: "redirect_uri", value: Constants.Backend.callbackUrl))
        queryItems.append(NSURLQueryItem(name: "scope", value: Constants.FranceConnect.scopes))
        queryItems.append(NSURLQueryItem(name: "state", value: state))
        queryItems.append(NSURLQueryItem(name: "nonce", value: nonce))
        
        urlComponents?.queryItems = queryItems
        
        return urlComponents?.URL
    }
    
    static func buildHistoryUrl() -> NSURL? {
        let urlComponents = NSURLComponents(string: Constants.FranceConnect.baseUrl)
        urlComponents?.path = Constants.FranceConnect.tracesPath
        
        return urlComponents?.URL
    }
    
    static func buildLogoutUrl() -> NSURL? {
        let urlComponents = NSURLComponents(string: Constants.FranceConnect.baseUrl)
        urlComponents?.path = Constants.FranceConnect.logoutPath
        
        return urlComponents?.URL
    }
    
}