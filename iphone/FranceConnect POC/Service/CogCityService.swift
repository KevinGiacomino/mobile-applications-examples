//
//  CogCityService.swift
//  FranceConnect POC
//
//  Created by Louis Davin on 20/11/2015.
//  Copyright © 2015 OCTO Technology. All rights reserved.
//

import Foundation

class CogCityService {
    
    static func fetchCity(code: String?) -> String? {
        let path = NSBundle.mainBundle().pathForResource("cog-ville", ofType: "json")
        if let unwrappedPath = path, unwrappedCode = code {
            let data = NSData(contentsOfFile: unwrappedPath)
            if let unwrappedData = data {
                do {
                    let cityArray = try NSJSONSerialization.JSONObjectWithData(unwrappedData, options: []) as! [NSDictionary]
                    for cityDictionary:NSDictionary in cityArray {
                        if unwrappedCode == cityDictionary["CODE"] as! String {
                            return cityDictionary["NCC"] as? String
                        }
                    }
                } catch {
                    // Do nothing
                }
            }
        }

        return nil
    }
}