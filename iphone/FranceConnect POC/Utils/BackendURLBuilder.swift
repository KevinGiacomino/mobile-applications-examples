//
//  BackendUrlBuilder.swift
//  FranceConnect POC
//
//  Created by Louis Davin on 18/11/2015.
//  Copyright © 2015 OCTO Technology. All rights reserved.
//

import Foundation

class BackendURLBuilder {
    
    static func buildUserInfoUrlWith(code: String, nonce: String) -> NSURL? {
        let urlComponents = NSURLComponents(string: Constants.Backend.baseUrl)
        urlComponents?.path = Constants.Backend.userInfoPath
        
        var queryItems = [NSURLQueryItem]()
        queryItems.append(NSURLQueryItem(name: "code", value: code))
        queryItems.append(NSURLQueryItem(name: "nonce", value: nonce))
        
        urlComponents?.queryItems = queryItems
        
        return urlComponents?.URL
    }
}