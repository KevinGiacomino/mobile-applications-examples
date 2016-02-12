//
//  UserInfoService.swift
//  FranceConnect POC
//
//  Created by Louis Davin on 18/11/2015.
//  Copyright © 2015 OCTO Technology. All rights reserved.
//

import Foundation

class UserInfoService {
    
    func fetchUserInfo(code: String, nonce: String, successCallback: (userInfo: UserInfo) -> Void, errorCallback: () -> Void) {
        if let url = BackendURLBuilder.buildUserInfoUrlWith(code, nonce:nonce) {
            let request = NSURLRequest(URL: url)
            let config = NSURLSessionConfiguration.defaultSessionConfiguration()
            let session = NSURLSession(configuration: config)
            
            let task = session.dataTaskWithRequest(request, completionHandler: { (responseData, response, responseError) in
                let responseDictionary: NSDictionary
                let userInfo = UserInfo()
                
                do {
                    responseDictionary = try NSJSONSerialization.JSONObjectWithData(responseData!, options: []) as! NSDictionary
                } catch {
                    errorCallback()
                    return
                }
                
                self.fillDataInto(userInfo, dictionary:responseDictionary)
                successCallback(userInfo: userInfo)
            })
            task.resume()
            
        } else {
            errorCallback()
        }
    }
    
    func logoutUser(callback: () -> Void) {
        if let url = FranceConnectURLBuilder.buildLogoutUrl() {
            let request = NSURLRequest(URL: url)
            let config = NSURLSessionConfiguration.defaultSessionConfiguration()
            let session = NSURLSession(configuration: config)
            
            let task = session.dataTaskWithRequest(request, completionHandler: { (responseData, response, responseError) in
                callback()
            })
            task.resume()
            
        } else {
            callback()
        }
    }
    
    func fillDataInto(userInfo: UserInfo, dictionary: NSDictionary) {
        userInfo.address = Address()
        
        userInfo.givenName = dictionary["given_name"] as? String
        userInfo.familyName = dictionary["family_name"] as? String
        userInfo.gender = dictionary["gender"] as? String
        userInfo.email = dictionary["email"] as? String
        
        userInfo.birthPlace = dictionary["birthplace"] as? String
        userInfo.birthDate = dictionary["birthdate"] as? String
        userInfo.birthCountry = dictionary["birthcountry"] as? String
        
        if let address = dictionary["address"] as? NSDictionary {
            userInfo.address.formatted = address["formatted"] as? String
            userInfo.address.streetAddress = address["street_address"] as? String
            userInfo.address.locality = address["locality"] as? String
            userInfo.address.region = address["region"] as? String
            userInfo.address.postalCode = address["postal_code"] as? String
            userInfo.address.country = address["country"] as? String
        }
    }
}