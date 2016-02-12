//
//  ContactViewController.swift
//  FranceConnect POC
//
//  Created by Louis Davin on 20/11/2015.
//  Copyright © 2015 OCTO Technology. All rights reserved.
//

import UIKit

class ContactViewController : UIViewController {
    
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
        
        // Monsieur XXX YYY
        if userInfo.gender == "male" {
            content.appendAttributedString(NSAttributedString(string: "Monsieur "))
        } else if userInfo.gender == "female" {
            content.appendAttributedString(NSAttributedString(string: "Madame "))
        }
        content.appendAttributedString(NSAttributedString(string: fullName, attributes: attributes))
        content.appendAttributedString(lineBreak)
        
        // deumeurant au XXX à YYYY,
        content.appendAttributedString(NSAttributedString(string: "deumeurant au "))

        if let address = userInfo.address.streetAddress {
            content.appendAttributedString(NSAttributedString(string: address, attributes: attributes))
        } else {
            content.appendAttributedString(NSAttributedString(string: "?", attributes: attributes))
        }
        
        content.appendAttributedString(NSAttributedString(string: " à "))
        if let city = userInfo.address.locality {
            content.appendAttributedString(NSAttributedString(string: city, attributes: attributes))
        } else {
            content.appendAttributedString(NSAttributedString(string: "?", attributes: attributes))
        }
        
        content.appendAttributedString(NSAttributedString(string: " - ", attributes: attributes))
        if let postalCode = userInfo.address.postalCode {
            content.appendAttributedString(NSAttributedString(string: postalCode, attributes: attributes))
        } else {
            content.appendAttributedString(NSAttributedString(string: "?", attributes: attributes))
        }
        content.appendAttributedString(lineBreak)
        
        // joignable par téléphone au :
        content.appendAttributedString(NSAttributedString(string: "joignable par téléphone au : "))
        content.appendAttributedString(NSAttributedString(string: "?", attributes: attributes))
        content.appendAttributedString(lineBreak)
        
        // joignable par mail au :
        content.appendAttributedString(NSAttributedString(string: "joignable par mail au : "))
        if let email = userInfo.email {
            content.appendAttributedString(NSAttributedString(string: email, attributes: attributes))
        } else {
            content.appendAttributedString(NSAttributedString(string: "?", attributes: attributes))
        }
            
        contentLabel.attributedText = content
    }
}