//
//  CogCountryFetcher.swift
//  FranceConnect POC
//
//  Created by Louis Davin on 20/11/2015.
//  Copyright © 2015 OCTO Technology. All rights reserved.
//

import Foundation

class CogCountryService {
    
    static func fetchCountry(stringCode: String?) -> String? {
        if let unwrappedStringCode = stringCode {
            if let code = Int(unwrappedStringCode) {
                let path = NSBundle.mainBundle().pathForResource("cog-pays", ofType: "json")
                if let unwrappedPath = path {
                    let data = NSData(contentsOfFile: unwrappedPath)
                    if let unwrappedData = data {
                        do {
                            let countryArray = try NSJSONSerialization.JSONObjectWithData(unwrappedData, options: []) as! [NSDictionary]
                            for countryDictionary:NSDictionary in countryArray {
                                if code == countryDictionary["code"] as! Int {
                                    return countryDictionary["libelle"] as? String
                                }
                            }
                        } catch {
                            // Do nothing
                        }
                    }
                }
            }
        }
        
        return nil
    }
}