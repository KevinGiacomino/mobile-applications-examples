//
//  IdentityViewController.swift
//  FranceConnect POC
//
//  Created by Louis Davin on 18/11/2015.
//  Copyright © 2015 OCTO Technology. All rights reserved.
//

import UIKit

class IdentityViewController : UIViewController {
    
    @IBOutlet weak var heroNameLabel: UILabel!
    @IBOutlet weak var contentLabel: UILabel!
    
    override func viewDidLoad() {
        let tabBarController = self.tabBarController as! TabBarController
        let userInfo = tabBarController.userInfo!
        
        // Hero "Name LastName"
        var firstName = "?"
        if let unwrappedFirstName = userInfo.givenName {
            firstName = unwrappedFirstName
        }
        var lastName = "?"
        if let unwrappedLastName = userInfo.familyName {
            lastName = unwrappedLastName
        }
        let fullName = "\(firstName) \(lastName)"
        heroNameLabel.text = fullName
        
        
        // Prepare for content
        let attributes: [String:AnyObject] = [
            NSFontAttributeName : UIFont.boldSystemFontOfSize(18.0),
            NSForegroundColorAttributeName : UIColor(red:0.32, green:0.53, blue:0.71, alpha:1)
        ]
        let content = NSMutableAttributedString()
        let lineBreak = NSAttributedString(string: "\n")
        let coma = NSAttributedString(string: ",")
        
        // Monsieur XXX YYY
        if userInfo.gender == "male" {
            content.appendAttributedString(NSAttributedString(string: "Monsieur "))
        } else if userInfo.gender == "female" {
            content.appendAttributedString(NSAttributedString(string: "Madame "))
        }
        content.appendAttributedString(NSAttributedString(string: fullName, attributes: attributes))
        content.appendAttributedString(lineBreak)
        
        // de sexe XXX,
        content.appendAttributedString(NSAttributedString(string: "de sexe "))
        if userInfo.gender == "male" {
            content.appendAttributedString(NSAttributedString(string: "masculin", attributes: attributes))
        } else if userInfo.gender == "female" {
            content.appendAttributedString(NSAttributedString(string: "féminin", attributes: attributes))
        } else {
            content.appendAttributedString(NSAttributedString(string: "?", attributes: attributes))
        }
        content.appendAttributedString(coma)
        content.appendAttributedString(lineBreak)
        
        // né le XX/YY/ZZ, à BXBXBX
        var dateString = "?"
        
        //     date
        if let birthDate = userInfo.birthDate {
            let dateFormatter = NSDateFormatter()
            dateFormatter.dateFormat = "yyyy-MM-dd"
            let date = dateFormatter.dateFromString(birthDate)
            dateFormatter.dateFormat = "dd/MM/yyyy"
            if let unwrappedDate = date {
                dateString = dateFormatter.stringFromDate(unwrappedDate)
            }
        }
        
        //     city
        var city = "?"
        if let foundCity = CogCityService.fetchCity(userInfo.birthPlace) {
            city = foundCity
        }
        
        content.appendAttributedString(NSAttributedString(string: "né le "))
        content.appendAttributedString(NSAttributedString(string: dateString, attributes: attributes))
        content.appendAttributedString(NSAttributedString(string: ", à "))
        content.appendAttributedString(NSAttributedString(string: city, attributes: attributes))
        content.appendAttributedString(lineBreak)
        
        // en XXXXXX
        var country = "?"
        if let foundCountry = CogCountryService.fetchCountry(userInfo.birthCountry) {
            country = foundCountry
        }
        
        content.appendAttributedString(NSAttributedString(string: "en "))
        content.appendAttributedString(NSAttributedString(string: country, attributes: attributes))
        
        
        contentLabel.attributedText = content
    }
}